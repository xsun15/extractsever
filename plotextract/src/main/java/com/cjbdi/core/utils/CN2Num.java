package com.cjbdi.core.utils;

public class CN2Num {
	static char[] cnArr = new char [] {'一','二','三','四','五','六','七','八','九'};
	static char[] chArr = new char [] {'十','百','千','万','亿'};
	static String allChineseNum = "零一二三四五六七八九十百千万亿";

	public static int cn2Num(String chineseNum) {
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        for (int i = 0; i < chineseNum.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNum.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                        case 0:
                            temp *= 10;
                            break;
                        case 1:
                            temp *= 100;
                            break;
                        case 2:
                            temp *= 1000;
                            break;
                        case 3:
                            temp *= 10000;
                            break;
                        case 4:
                            temp *= 100000000;
                            break;
                        default:
                            break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNum.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
	}

	public static boolean isChineseNum(String chineseStr) {
		char [] ch = chineseStr.toCharArray();
		for (char c : ch) {
			if (!allChineseNum.contains(String.valueOf(c))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNum(String str) {
		String reg = "[0-9]+";
		return str.matches(reg);
	}
	
	public static void main(String[] args) {
		System.out.println(cn2Num("五"));
	}
}