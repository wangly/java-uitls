// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.entity;

/**
 * @author cyy
 * @version 1.0
 * @created 2017/4/11 下午2:42
 **/
public class UpdateEntity<T> {
    private T oldEntity;  //如果为空，则beforeMap为空
    private T newEntity;

    public UpdateEntity(T oldEntity, T newEntity) {
        this.oldEntity = oldEntity;
        this.newEntity = newEntity;
    }

    public T getOldEntity() {
        return oldEntity;
    }

    public void setOldEntity(T oldEntity) {
        this.oldEntity = oldEntity;
    }

    public T getNewEntity() {
        return newEntity;
    }

    public void setNewEntity(T newEntity) {
        this.newEntity = newEntity;
    }
}
