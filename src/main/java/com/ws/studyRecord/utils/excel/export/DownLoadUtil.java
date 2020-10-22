package com.ws.studyRecord.utils.excel.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 16:45
 */
@Slf4j
public class DownLoadUtil {
    /**
     * 导出 Excel
     * @param excelParam
     */
    public static void exportExcel(ExcelParam excelParam){

        try {
            // 声明一个工作薄
            Workbook workbook = new HSSFWorkbook();

            List<String> sheetNames = excelParam.getSheetNames();
            List<Class> classList = excelParam.getClassList();
            List<Collection> dataSets = excelParam.getDataSets();
            OutputStream out = excelParam.getOut();

            for(int sheetNo = 0; sheetNo<sheetNames.size();sheetNo++){
                String title = sheetNames.get(sheetNo);
                Class<?> pojoClass = classList.get(sheetNo);
                Collection<?> dataSet = dataSets.get(sheetNo);
                // 首先检查数据看是否是正确的
                if (dataSet == null || dataSet.size() == 0) {
                    continue;
                }
                if (title == null || out == null || pojoClass == null) {
                    continue;
                }

                // 生成一个表格
                Sheet sheet = workbook.createSheet(title);

                // 标题
                List<String> exportFieldTitle = new ArrayList<>();
                List<Integer> exportFieldWidth = new ArrayList<>();
                // 拿到所有列名，以及导出的字段的get方法
                List<Method> methodObj = new ArrayList<>();
                Map<String, Method> convertMethod = new HashMap<>();
                // 得到所有字段
                Field[] fields = pojoClass.getDeclaredFields();

                //是否求和配置
                boolean isSum=false;
                List<BigDecimal> sumList = new ArrayList<>();
                List<Boolean> isSumList = new ArrayList<>();
                List<Integer> scaleList = new ArrayList<>();
                List<Boolean> isMergeList = new ArrayList<>();
                List<Method> mergeFlagList = new ArrayList<>();

                // 遍历整个filed
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
                    // 如果设置了annotation
                    if (excelConfig != null) {
                        // 添加到标题
                        exportFieldTitle.add(excelConfig.exportName());
                        // 添加标题的列宽
                        exportFieldWidth.add(excelConfig.exportFieldWidth());
                        // 添加到需要导出的字段的方法
                        String fieldName = field.getName();
                        //LOG.debug(i + excelConfig.exportName() + " " + "列宽" + excelConfig.exportFieldWidth());
                        StringBuffer getMethodName = new StringBuffer("get");
                        getMethodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                        Method getMethod = pojoClass.getMethod(getMethodName.toString(), new Class[]{});
                        methodObj.add(getMethod);

                        if (excelConfig.exportConvertSign()) {
                            StringBuilder getConvertMethodName = new StringBuilder("get");
                            getConvertMethodName.append(fieldName.substring(0, 1).toUpperCase())
                                    .append(fieldName.substring(1))
                                    .append("Convert");
                            //LOG.debug("convert: " + getConvertMethodName.toString());
                            Method getConvertMethod = pojoClass.getMethod(getConvertMethodName.toString(), new Class[]{});
                            convertMethod.put(getMethodName.toString(), getConvertMethod);
                        }
                        //记录是否求和配置
                        if (i != 0) {
                            if (excelConfig.isSum()) {
                                isSum = true;
                                //LOG.debug(field.getName() + "需要合计");
                                isSumList.add(true);
                                sumList.add(new BigDecimal(0));
                                scaleList.add(excelConfig.scale());
                            } else {
                                isSumList.add(false);
                                sumList.add(null);
                                scaleList.add(null);
                            }
                        } else {
                            isSumList.add(false);
                            sumList.add(null);
                            scaleList.add(null);
                        }

                        // 是否合并
                        isMergeList.add(excelConfig.isMerge());
                        if (excelConfig.isMerge()) {
                            StringBuilder getMergeFlagName = new StringBuilder("get");
                            String mergeFlag;
                            if (StringUtils.isBlank(excelConfig.mergeFlag())) {
                                mergeFlag = getMethodName.toString();
                            } else {
                                getMergeFlagName.append(excelConfig.mergeFlag().substring(0, 1).toUpperCase()).append(excelConfig.mergeFlag().substring(1));
                                mergeFlag = getMergeFlagName.toString();
                            }
                            Method getMergeFlag = pojoClass.getMethod(mergeFlag, new Class[]{});
                            mergeFlagList.add(getMergeFlag);
                        } else {
                            mergeFlagList.add(null);
                        }
                    }
                }
                int index = 0;
                // 产生表格标题行
                Row row = sheet.createRow(index);
                for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
                    Cell cell = row.createCell(i);
                    RichTextString text = new HSSFRichTextString(exportFieldTitle.get(i));
                    cell.setCellValue(text);
                }

                // 设置每行的列宽
                for (int i = 0; i < exportFieldWidth.size(); i++) {
                    // 256=65280/255
                    sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
                }
                Iterator its = dataSet.iterator();
                HashMap<String, PoiModel> poiModelMap = new HashMap<>();
                // 设置单元格样色
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                // 循环插入剩下的集合
                while (its.hasNext()) {
                    // 从第二行开始写，第一行是标题
                    index++;
                    row = sheet.createRow(index);
                    Object t = its.next();
                    for (int k = 0; k < methodObj.size(); k++) {
                        Cell cell = row.createCell(k);
                        Method getMethod = methodObj.get(k);
                        Object value;
                        if (convertMethod.containsKey(getMethod.getName())) {
                            Method cm = convertMethod.get(getMethod.getName());
                            value = cm.invoke(t, new Object[]{});
                        } else {
                            value = getMethod.invoke(t, new Object[]{});
                        }
                        cell.setCellValue(value == null ? "" : value.toString());

                        //合计计算操作
                        if(isSumList.get(k)){
                            BigDecimal tempNum = sumList.get(k);
                            if(value instanceof Number){
                                sumList.set(k,tempNum.add(new BigDecimal(value.toString())));
                            }else if(value instanceof String){
                                sumList.set(k,tempNum.add(new BigDecimal(1)));
                            }else{
                                //LOG.warn("未知合计类型"+value.toString());
                                sumList.set(k,tempNum.add(new BigDecimal(1)));
                            }
                        }

                        // 合并列
                        if (isMergeList.get(k)) {
                            String mergeValue;
                            Method cm = mergeFlagList.get(k);
                            mergeValue = cm.invoke(t, new Object[]{}).toString();
                            PoiModel poiModel = poiModelMap.get(getMethod.getName());
                            if (poiModel == null) {
                                poiModel = new PoiModel();
                                poiModel.setRowIndex(index);
                                poiModel.setContent(mergeValue);
                                poiModelMap.put(getMethod.getName(), poiModel);
                            } else {
                                // 判断值是否相等，不相等则合并
                                if (!poiModel.getContent().equals(mergeValue)) {
                                    // 合并单元格必须是2个或以上
                                    if (poiModel.getRowIndex() != (index - 1)) {
                                        CellRangeAddress cra=new CellRangeAddress(poiModel.getRowIndex(), index - 1, k, k);
                                        sheet.addMergedRegion(cra);
                                        sheet.getRow(poiModel.getRowIndex()).getCell(k).setCellStyle(cellStyle);
                                    }
                                    poiModel.setContent(mergeValue);
                                    poiModel.setRowIndex(index);
                                    poiModelMap.put(getMethod.getName(), poiModel);
                                } else {
                                    // 最后一行无法在进行比较，直接合并
                                    if (index == dataSet.size()) {
                                        if (poiModel.getRowIndex() != index) {
                                            CellRangeAddress cra=new CellRangeAddress(poiModel.getRowIndex(), index, k, k);
                                            sheet.addMergedRegion(cra);
                                            sheet.getRow(poiModel.getRowIndex()).getCell(k).setCellStyle(cellStyle);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //合计行显示操作
                if(isSum){
                    row = sheet.createRow(++index);
                    row.createCell(0).setCellValue("合计");
                    for (int k = 0; k < isSumList.size(); k++) {
                        if(isSumList.get(k)){
                            Cell cell = row.createCell(k);
                            cell.setCellValue((sumList.get(k).setScale(scaleList.get(k), RoundingMode.HALF_UP)).toString());
                        }
                    }
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            log.error("Excel导出失败：",e);
            // LOG.error("Excel导出失败：", e);
        }

    }
}
