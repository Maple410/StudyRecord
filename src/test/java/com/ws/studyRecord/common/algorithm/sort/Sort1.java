package com.ws.studyRecord.common.algorithm.sort;

/**
 * @Author: wangshuo
 * @Date: 2020/11/3 10:40
 */
public class Sort1 {



    public static int[] sort(int[] arr){

        int temp = 0;
        for(int i =1;i<arr.length;i++){
            int j = i-1;
            temp = arr[i];//要插入的数

            for (; j >= 0 && temp < arr[j]; j--) {
                arr[j + 1] = arr[j];// 将大于temp的值整体后移一个单位
            }
            arr[j + 1] = temp;
        }

        return arr;

    }


    public static void main(String[] args) {


        int[] arr = {15,8,56,247,5,45};
        int num[] = sort(arr);
        for(int i=0;i<num.length;i++){
            System.out.print(num[i] + " ");
        }
    }


}
