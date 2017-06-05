// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.exception;

/**
 * @author laisongxian
 * @version 1.0
 * @created 17/1/16 10:51
 **/
public interface IBizException {
    public Integer getCode();
    public String getMessage();
}
