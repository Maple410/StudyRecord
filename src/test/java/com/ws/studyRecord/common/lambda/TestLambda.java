package com.ws.studyRecord.common.lambda;

import com.google.common.collect.Lists;
import com.ws.studyRecord.common.entity.UserInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: wangshuo
 * @Date: 2020/10/29 10:11
 */
public class TestLambda {


    public static void main(String[] args) {

        UserInfo info1 = UserInfo.builder().id(1).name("张三").address("南京").build();
        UserInfo info2 = UserInfo.builder().id(2).name("李四").address("烟台").build();
        UserInfo info3 = UserInfo.builder().id(3).name("王五").address("济南").build();
        UserInfo info4 = UserInfo.builder().id(4).name("赵六").address("烟台").build();

        List<UserInfo> infoList = new ArrayList<>();
        infoList.add(info1);
        infoList.add(info2);
        infoList.add(info3);
        infoList.add(info4);

        //过滤
        System.out.println("-------------过滤-----------------");
        List<UserInfo> filterList = infoList.stream().filter(item ->item.getId()==1).collect(Collectors.toList());
        System.out.println(filterList.size());

        //排序
        System.out.println("-------------排序-----------------");
        List<UserInfo> sortList = infoList.stream().sorted(Comparator.comparing(UserInfo::getId).reversed()).collect(Collectors.toList());
        System.out.println(sortList.toString());


        //1.按照对象的某个属性分组，并取其中某个属性的集合：按照id 分组 取address的list
        System.out.println("-------------分组-----------------");
        Map<Integer, List<String>> map = infoList.stream().collect(Collectors.groupingBy(UserInfo::getId, Collectors.mapping(UserInfo::getAddress, Collectors.toList())));
        map.forEach((k,v)->{
            System.out.println(k +":" + v);
        });

        //2.保持分组后有序
        System.out.println("-------------分组后有序-----------------");
        Map<Integer,List<UserInfo>> sortMap =  infoList.stream().collect(Collectors.groupingBy(UserInfo::getId, LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
        sortMap.forEach((k,v) ->{
            System.out.println(k + ": "+ v);
        });

        //3.分隔
        System.out.println("-------------分隔-----------------");
        List<List<UserInfo>> splitLlist = Lists.partition(infoList,2);
        splitLlist.forEach(item ->{
            System.out.println(item.toString());
        });

    }
}
