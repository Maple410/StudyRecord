package com.ws.studyRecord.common.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wangshuo
 * @Date: 2020/10/22 14:03
 */
@Data
@Builder
public class UserInfo {

    private Integer id;

    private String name;

    private String address;
}
