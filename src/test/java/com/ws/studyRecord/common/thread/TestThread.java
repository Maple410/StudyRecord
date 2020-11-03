package com.ws.studyRecord.common.thread;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.ws.studyRecord.common.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Author: wangshuo
 * @Date: 2020/11/3 11:28
 */
public class TestThread {

    //获取CPU数量
    private static final int nThreads = Runtime.getRuntime().availableProcessors();


    public static void main(String[] args) {


    }


    public void test(){
        List<Callable<List<UserInfo>>> tasks =new ArrayList<>();

        Callable<List<UserInfo>> afe = new ThreadHandle("测试",1d);
        tasks.add(afe);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            List<Future<List<UserInfo>>> futures = executorService.invokeAll(tasks);

            if(null !=futures && futures.size()>0){
                for(Future<List<UserInfo>> future : futures){
                    if(CollectionUtils.isNotEmpty(future.get())){
                        //TODO 处理
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    class ThreadHandle implements Callable<List<UserInfo>> {

        private String content;

        private Double standardRate;

        public ThreadHandle(String content, Double standardRate) {
            this.content = content;
            this.standardRate = standardRate;
        }

        @Override
        public List<UserInfo> call() throws Exception {


            List<UserInfo> list = new ArrayList<>();
            return list;
        }
    }
}
