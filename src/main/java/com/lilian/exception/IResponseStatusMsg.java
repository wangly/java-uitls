// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.exception;

/**
 * 状态和信息
 **/
public interface IResponseStatusMsg {

    //通用的枚举类型
    enum APIEnum implements IResponseStatusMsg {
        // 通用码
        SUCCESS(0, "成功"),

        /**
         * 1000~1999 通用的服务器异常, 影响API成功率统计
         */
        FAILED(1001, "失败"),
        SERVER_ERROR(1002, "服务器错误"),
        METHOD_WITHOUT_IMPLEMENT(1003, "该方法暂未实现"),;

        private Integer code;
        private String message;

        APIEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public static boolean isSuccess(Integer code) {
            return SUCCESS.code.equals(code);
        }
    }

    Integer getCode();

    String getMessage();
}
