package com.lilian.utils;
import com.lilian.exception.APIRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抽取工具
 */
public class ExtractUtils {

    public static final Logger logger = LoggerFactory.getLogger(ExtractUtils.class);

    /**
     * 从复杂结构列表中抽取出指定字段的列表
     *
     * @param list      源数据列表
     * @param fieldName 欲抽取字段名
     * @param clz       抽取字段类型
     * @return 去重结果
     */
    public static <T, R> List<R> extractField(List<T> list, String fieldName, Class<R> clz) {
        if (CollectionUtils.isEmpty(list) || StringUtils.isBlank(fieldName)) {
            return Collections.emptyList();
        }

        Function<T, R> func = (item) -> {
            Object obj = null;
            try {
                obj = ReflectUtils.invokeGetter(item, fieldName);
            } catch (Exception e) {
                logger.info("extractField list:{}, fieldName:{}, type:{} error->", list, fieldName, clz, e);
                throw new APIRuntimeException(e);
            }
            return (R) obj;
        };

        return list.stream().map(func).filter(item -> item != null).distinct().collect(Collectors.toList());
    }

}