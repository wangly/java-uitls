// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author chenhan
 * @version 1.0
 * @created 1/16/17 8:08 PM
 **/
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collect) {
        return collect == null || collect.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collect) {
        return !isEmpty(collect);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <T> T[] toArray(Collection<T> collect, Class<T[]> clazz) {
        if (collect == null) {
            throw new RuntimeException("参数不能为null!");
        }
        if (clazz == null) {
            return null;
        }
        Object[] data = collect.toArray();
        return Arrays.copyOf(data, data.length, clazz);
    }

    public static <T> boolean containsElement(Collection<T> collect, T elem) {
        if (elem == null || isEmpty(collect)) {
            return false;
        }
        return collect.contains(elem);
    }

    public static <E> List<E> concatList(List<E>... array) {
        if (array == null || array.length < 1) {
            return Collections.emptyList();
        }
        List<E> newList = new ArrayList<E>();
        for (List<E> e : array) {
            if (isNotEmpty(e)) {
                newList.addAll(e);
            }
        }
        return newList;
    }
}