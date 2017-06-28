// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.interceptor;

import com.lilian.annotation.CreatedDate;
import com.lilian.annotation.LastModifiedDate;
import com.lilian.entity.EntityHelper;
import com.lilian.entity.EntityTable;
import com.lilian.entity.UpdateEntity;
import com.lilian.helper.InvocationHelper;
import com.lilian.utils.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/3/9 下午5:26
 **/
@Intercepts(@Signature(method = "update", type = Executor.class, args = { MappedStatement.class, Object.class }))
public class AutoGmtPlugin implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(AutoGmtPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (!SqlCommandType.INSERT.equals(sqlCommandType) && !SqlCommandType.UPDATE.equals(sqlCommandType)) {
            return invocation.proceed();
        }
        Object param = invocation.getArgs()[1];
        Class paramClass = param.getClass();
        Collection paramList = new ArrayList<>();
        EntityTable entityTable;
        try {
            paramList = InvocationHelper.getParamList(invocation);
            if (CollectionUtils.isEmpty(paramList)) {
                return invocation.proceed();
            }
            if (UpdateEntity.class.equals(paramClass)) {
                Object parameter = invocation.getArgs()[1];
                UpdateEntity updateEntity = (UpdateEntity) parameter;
                paramList = Arrays.asList(updateEntity.getNewEntity());
            }
            paramClass = paramList.toArray()[0].getClass();
            if (!EntityHelper.isEntityClass(paramClass)) {
                return invocation.proceed();
            }
            entityTable = EntityHelper.getEntityTable(paramClass);
        } catch (Exception ex) { //找不到参数类对应的对应entityTable
            logger.warn("AutoGmtPlugin param:{} error->", ex);
            return invocation.proceed();
        }
        List<String> needAutoGmtFields = entityTable.getNeedAutoGmtFields();
        if (CollectionUtils.isEmpty(needAutoGmtFields) || CollectionUtils.isEmpty(paramList)) {//不包含注解字段不处理
            return invocation.proceed();
        }
        //遍历处理@LastModifiedDate或@CreatedDate
        for (Object item : paramList) {
            for (String fieldName : needAutoGmtFields) {
                Field field = paramClass.getDeclaredField(fieldName);
                if (field.isAnnotationPresent(LastModifiedDate.class) || (SqlCommandType.INSERT.equals(sqlCommandType)
                        && field.isAnnotationPresent(CreatedDate.class))) {
                    String methodName = new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase())
                            .append(fieldName.substring(1)).toString();
                    try {
                        Method method = paramClass.getMethod(methodName, Date.class);
                        method.invoke(item, new Date());
                    } catch (NoSuchMethodException e) {
                        logger.warn("NoSuchMethodException methodName:{}, error->", methodName, e);
                        continue;
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}