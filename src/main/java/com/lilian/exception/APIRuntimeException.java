// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.exception;

/**
 * 业务异常
 **/
public class APIRuntimeException extends RuntimeException implements IBizException {
    private Integer code;
    private String message;

    public APIRuntimeException(IResponseStatusMsg responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }

    public APIRuntimeException(IResponseStatusMsg responseStatus, String message) {
        this.code = responseStatus.getCode();
        this.message = message == null?responseStatus.getMessage():message;
    }

    public APIRuntimeException(Integer error, String message) {
        this.code = error;
        this.message = message;
    }

    public APIRuntimeException(Throwable cause) {
        super(cause);
        this.code = IResponseStatusMsg.APIEnum.SERVER_ERROR.getCode();
        this.message = cause.getMessage()==null?
                IResponseStatusMsg.APIEnum.SERVER_ERROR.getMessage():cause.getMessage();
    }

    public APIRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.code = IResponseStatusMsg.APIEnum.SERVER_ERROR.getCode();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
