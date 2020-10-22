package com.ws.studyRecord.utils.excel.export;

import lombok.Data;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 16:45
 */
@Data
public class ExcelParam {

    private List<String> sheetNames;
    private List<Class> classList;
    private List<Collection> dataSets;
    private OutputStream out;
}
