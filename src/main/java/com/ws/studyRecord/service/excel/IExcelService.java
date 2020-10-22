package com.ws.studyRecord.service.excel;

import com.ws.studyRecord.utils.excel.export.exportBean.UserInfoExport;
import com.ws.studyRecord.utils.excel.importing.ImportBean.UserInfoImport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 16:59
 */
public interface IExcelService {

    void exportExcel(HttpServletResponse response)throws Exception;

    List<UserInfoImport> importExcel(MultipartFile file);
}
