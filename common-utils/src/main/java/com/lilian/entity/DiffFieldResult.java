// Copyright (C) 2016 Meituan
// All rights reserved
package com.lilian.entity;

import com.lilian.utils.ArrayUtils;

/**
 * @author wangliyue
 * @version 1.0
 * @created 16/8/24 上午11:12
 **/
public class DiffFieldResult {
    private String fieldName;
    private Object fromValue;
    private Object toValue;

    public DiffFieldResult() {
    }

    public DiffFieldResult(String fieldName, Object fromValue, Object toValue) {
        this.fieldName = fieldName;
        this.fromValue = fromValue;
        this.toValue = toValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFromValue() {
        return fromValue;
    }

    public void setFromValue(Object fromValue) {
        this.fromValue = fromValue;
    }

    public Object getToValue() {
        return toValue;
    }

    public void setToValue(Object toValue) {
        this.toValue = toValue;
    }

    @Override
    public String toString() {
        Object fromValueStr = fromValue == null ? "" : fromValue.getClass().isArray() ? ArrayUtils.toString((Object[]) fromValue) : fromValue;
        Object toValueStr = toValue == null ? "" : toValue.getClass().isArray() ? ArrayUtils.toString((Object[]) toValue) : toValue;
        final StringBuilder sb = new StringBuilder("{");
        sb.append("fieldName='").append(fieldName).append('\'');
        sb.append(", fromValue=").append(fromValueStr);
        sb.append(", toValue=").append(toValueStr);
        sb.append('}');
        return sb.toString();
    }
}