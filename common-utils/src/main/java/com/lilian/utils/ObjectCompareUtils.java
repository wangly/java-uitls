// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.utils;

import com.lilian.annotation.IgnoreCompare;
import com.lilian.entity.DiffFieldResult;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/2/16 上午12:04
 **/
public class ObjectCompareUtils {

    public static List<DiffFieldResult> compare(Object fromObject, Object toObject) throws Exception {
        List<DiffFieldResult> diffFieldResults = new ArrayList<DiffFieldResult>();
        if (fromObject == null && toObject == null) {
            return Collections.emptyList();
        } else if (fromObject == null || toObject == null) {
            diffFieldResults.add(new DiffFieldResult("object", fromObject, toObject));
            return diffFieldResults;
        }
        if (fromObject.getClass() != toObject.getClass()) {
            throw new Exception("object class cannot match");
        }
        Class<? extends Object> clazz = fromObject.getClass();
        if (isJavaClass(clazz)) {
            if (!fromObject.equals(toObject)) {
                diffFieldResults.add(new DiffFieldResult("object", fromObject, toObject));
            }
            return diffFieldResults;
        }

        Field[] fields = clazz.getDeclaredFields();//不包含父类的字段

        for (Field field : fields) {
            String fieldName = field.getName();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            if (field.isAnnotationPresent(IgnoreCompare.class)) {//忽略字段
                continue;
            }
            try {
                Method method = clazz.getMethod(methodName, new Class[] {});
                Object fromFieldValue = method.invoke(fromObject, new Object[] {});
                Object toFieldValue = method.invoke(toObject, new Object[] {});

                if (fromFieldValue == null && toFieldValue == null) {//为空字段跳过
                    continue;
                }
                if (fromFieldValue != null && toFieldValue != null) {//均不为null
                    if (fromFieldValue instanceof Collection && toFieldValue instanceof Collection
                            && isSameCollection((Collection) fromFieldValue,
                            (Collection) toFieldValue)) {//集合类型
                        continue;
                    } else if (fromFieldValue.getClass().isArray() && toFieldValue.getClass().isArray()
                            && isSameCollection(Arrays.asList((Object[]) fromFieldValue), Arrays.asList((Object[])toFieldValue))) {
                        continue;
                    } else if (!isJavaClass(fromFieldValue.getClass()) && CollectionUtils
                            .isEmpty(compare(fromFieldValue, toFieldValue))) {//自定义类型
                        continue;
                    } else if (fromFieldValue.equals(toFieldValue)) {
                        continue;
                    }
                }
                DiffFieldResult diffFieldResult = new DiffFieldResult(fieldName, fromFieldValue, toFieldValue);
                diffFieldResults.add(diffFieldResult);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return diffFieldResults;
    }


    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    /**
     * 集合类型对比,不考虑顺序
     * @param coll1
     * @param coll2
     * @return
     */
    public static boolean isSameCollection(Collection coll1, Collection coll2) throws Exception {
        if ((coll1 == null || coll2 == null) ? !(coll1 == null && coll2 == null) : coll1.size() != coll2.size()) {
            //不为全空,且大小不同,则集合不同
            return false;
        }
        Set<Integer> matchIndex = new HashSet();//保存coll2中被匹配过的元素的下标

        for (Iterator it1 = coll1.iterator(); it1.hasNext(); ) {
            boolean flag = false; //是否存在相同对象
            Object item1 = it1.next();
            int index = 0;
            for (Iterator it2 = coll2.iterator(); it2.hasNext(); index++) {
                Object item2 = it2.next();
                if (item1 == null ? item2 == null : CollectionUtils.isEmpty(compare(item1, item2))) {
                    if (matchIndex.contains(index)) {//已被匹配过
                        continue;
                    }
                    flag = true;
                    matchIndex.add(index);
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        if (matchIndex.size() != coll2.size()) {
            return false;
        }
        return true;
    }

    /**
     * 检查某个字段是否为差异字段
     * @param diffFieldResults
     * @param fieldName
     * @return
     */
    public static boolean isContained(List<DiffFieldResult> diffFieldResults, String fieldName) {
        if (CollectionUtils.isEmpty(diffFieldResults)) {
            return false;
        }
        for (DiffFieldResult diffFieldResult : diffFieldResults) {
            if (diffFieldResult.getFieldName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }


}