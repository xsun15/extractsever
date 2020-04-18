package com.cjbdi.core.developcenter.utils;

import java.util.ArrayList;
import java.util.List;

public class Combination {
    public static List combine(Double[] a, int m) {
        int n = a.length;
        if (m > n) {
            System.out.println("错误");
        }

        List<List<Double>> result = new ArrayList();

        Double[] bs = new Double[n];
        for (int i = 0; i < n; i++) {
            bs[i] = 0.0;
        }
        // 初始化
        for (int i = 0; i < m; i++) {
            bs[i] = 1.0;
        }
        boolean flag = true;
        boolean tempFlag = false;
        int pos = 0;
        int sum = 0;
        // 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
        int count = 0;
        do {
            sum = 0;
            pos = 0;
            tempFlag = true;
            result.add(print(bs, a, m));

            for (int i = 0; i < n - 1; i++) {
                if (bs[i] == 1 && bs[i + 1] == 0) {
                    bs[i] = 0.0;
                    bs[i + 1] = 1.0;
                    pos = i;
                    break;
                }
            }
            // 将左边的1全部移动到数组的最左边

            for (int i = 0; i < pos; i++) {
                if (bs[i] == 1) {
                    sum++;
                }
            }
            for (int i = 0; i < pos; i++) {
                if (i < sum) {
                    bs[i] = 1.0;
                } else {
                    bs[i] = 0.0;
                }
            }

            // 检查是否所有的1都移动到了最右边
            for (int i = n - m; i < n; i++) {
                if (bs[i] == 0) {
                    tempFlag = false;
                    break;
                }
            }
            if (tempFlag == false) {
                flag = true;
            } else {
                flag = false;
            }
            count++;
        } while (flag && count<=1000);
        result.add(print(bs, a, m));

        return result;
    }
    private static List<Double> print(Double[] bs, Double[] a, int m) {
        List<Double> result = new ArrayList<>();
        int pos = 0;
        for (int i = 0; i < bs.length; i++) {
            if (bs[i] == 1) {
                result.add(a[i]);
            }
        }
        return result;
    }

    private static void print(List l) {
        for (int i = 0; i < l.size(); i++) {
            int[] a = (int[]) l.get(i);
            for (int j = 0; j < a.length; j++) {
                System.out.print(a[j] + "\t");
            }
            System.out.println();
        }
    }
}
