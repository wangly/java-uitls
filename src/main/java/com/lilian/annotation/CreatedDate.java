// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/2/22 上午10:58
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD, METHOD, ANNOTATION_TYPE })
public @interface CreatedDate {
}