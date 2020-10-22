package com.ws.studyRecord.service.excel.impl;

import com.google.common.collect.Lists;
import com.ws.studyRecord.service.excel.IExcelService;
import com.ws.studyRecord.utils.excel.export.DownLoadUtil;
import com.ws.studyRecord.utils.excel.export.ExcelParam;
import com.ws.studyRecord.utils.excel.export.exportBean.UserInfoExport;
import com.ws.studyRecord.utils.excel.importing.ImportBean.UserInfoImport;
import com.ws.studyRecord.utils.excel.importing.ImportUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 16:59
 */
@Service
public class ExcelServiceImpl implements IExcelService{

    @Override
    public void exportExcel(HttpServletResponse response)throws Exception {

        String fileName = "测试.xls";
        //String headStr = "attachment; filename=\"" + fileName + "\"";
        String headStr = "attachment;filename=\"" + new String(fileName.getBytes(),"ISO8859-1") + "\"";//postman访问有乱码
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", headStr);
        OutputStream out = response.getOutputStream();

        List<UserInfoExport> exports = new ArrayList<>();
        UserInfoExport export = UserInfoExport.builder().name("小明").Age(18).address("青岛").build();
        exports.add(export);

        List<Class> classList = Lists.newArrayList(UserInfoExport.class);
        List<String> sheetNames = Lists.newArrayList("导出");
        List<Collection> dataSets = new ArrayList<>();
        dataSets.add(exports);

        ExcelParam excelParam = new ExcelParam();
        excelParam.setClassList(classList);
        excelParam.setSheetNames(sheetNames);
        excelParam.setOut(out);
        excelParam.setDataSets(dataSets);
        DownLoadUtil.exportExcel(excelParam);
        out.close();

    }

    @Override
    public List<UserInfoImport> importExcel(MultipartFile file) {

        List<UserInfoImport> infoList = ImportUtil.readExcel("",UserInfoImport.class,file);
        return infoList;
    }
}
