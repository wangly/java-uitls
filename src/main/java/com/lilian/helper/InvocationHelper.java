// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.helper;

import com.lilian.entity.EntityHelper;
import com.lilian.entity.EntityTable;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author cyy
 * @version 1.0
 * @created 2017/3/30 下午6:27
 **/
public class InvocationHelper {

    private static Logger logger = LoggerFactory.getLogger(InvocationHelper.class);

    public static Collection getParamList(Invocation invocation) {
        Object parameter = invocation.getArgs()[1];
        Collection paramList = new ArrayList<>();
        if (DefaultSqlSession.StrictMap.class.isInstance(parameter)) { //ia-mapper 参数
            DefaultSqlSession.StrictMap paramMap = (DefaultSqlSession.StrictMap) parameter;
            if (paramMap.get("collection") != null) {
                paramList = new ArrayList((Collection) paramMap.get("collection"));
            } else if (paramMap.get("array") != null) {
                paramList = Arrays.asList(paramMap.get("array"));
            }
        } else if (MapperMethod.ParamMap.class.isInstance(parameter)) {  //自定义xml 参数
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameter;
            if (paramMap.get("param1") != null) {
                paramList = new ArrayList((Collection) paramMap.get("param1"));
            }
        } else {
            paramList.add(parameter);
        }
        return paramList;
    }

    public static EntityTable getEntityTable(Class entityClass) {
        try {
            return EntityHelper.getEntityTable(entityClass);
        } catch (Exception e) {
            logger.warn("getEntityTable error->", e);
            return null;
        }
    }

}
