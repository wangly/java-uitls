// Copyright (C) 2015 Meituan
// All rights reserved
package com.lilian.utils;

import java.lang.reflect.Array;

/**
 * 数组的一些公共方法
 * @author laisongxian
 * @version 1.0
 * @created 15/11/11 10:08
 **/
public class ArrayUtils {
    /**
     * 是否为空或长度为0
     * @param array
     * @return
     */
    public static boolean isArrayEmpty(Object [] array)
    {
        return length(array) == 0;
    }

    /**
     * 是否为空或长度为0
     * @param array
     * @return
     */
    public static boolean isArrayEmpty(int [] array)
    {
        return length(array) == 0;
    }

    /**
     * 是否为空或长度为0
     * @param array
     * @return
     */
    public static boolean isArrayEmpty(long [] array)
    {
        return length(array) == 0;
    }

    /**
     * 数组长度，如果为空，则为0
     * @param array
     * @return
     */
    public static int length(Object [] array){
        if(array == null) return 0;
        return array.length;
    }

    /**
     * 数组长度，如果为空，则为0
     * @param array
     * @return
     */
    public static int length(int [] array){
        if(array == null) return 0;
        return array.length;
    }

    /**
     * 数组长度，如果为空，则为0
     * @param array
     * @return
     */
    public static int length(long [] array){
        if(array == null) return 0;
        return array.length;
    }

    /**
     * 两个相同类型的数组合并，得到新的数组
     * @param a1
     * @param a2
     * @param <T>
     * @return
     */
    public static <T> T[] combineTwoArrays(T[] a1, T... a2)
    {
        if(a1 == null && a2 == null){
            return null;
        }
        int length1 = length(a1);
        int length2 = length(a2);

        int newLength = length1 + length2;
        Class componentType = a1 == null? a2.getClass().getComponentType(): a1.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(componentType, newLength);
        if(length1>0) {
            System.arraycopy(a1, 0, result, 0, length1);
        }
        if(length2>0) {
            System.arraycopy(a2, 0, result, length1, length2);
        }

        return result;
    }

    /**
     * 数组转化成字符串输出: 格式[a,b,c]
     * @param array
     * @return
     */
    @SuppressWarnings("squid:S1994")
    public static String toString(Object [] array){
        if(array == null || array.length == 0){
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (int i=0;;i++) {
            sb.append(array[i]);
            if (i == array.length-1)
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
