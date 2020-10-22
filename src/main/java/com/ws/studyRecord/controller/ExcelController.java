package com.ws.studyRecord.controller;

import com.ws.studyRecord.service.excel.IExcelService;
import com.ws.studyRecord.utils.excel.importing.ImportBean.UserInfoImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 16:54
 */
@RestController
@RequestMapping("/record/excel")
@Slf4j
public class ExcelController {

    @Autowired
    private IExcelService excelService;

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response){
        try {
            excelService.exportExcel(response);
        } catch (Exception e) {
            log.error("导出Excel失败");
        }
    }

    @PostMapping("/import")
    public List<UserInfoImport> importExcel(@RequestParam("file") MultipartFile file){
       return excelService.importExcel(file);
    }
}
