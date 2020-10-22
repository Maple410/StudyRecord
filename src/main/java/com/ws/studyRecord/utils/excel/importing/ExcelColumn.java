package com.ws.studyRecord.utils.excel.importing;

import java.lang.annotation.*;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 18:09
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    String value() default "";

    int col() default 0;
}
