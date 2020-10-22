package com.ws.studyRecord.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 16:40
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "Hello World!";
    }
}
