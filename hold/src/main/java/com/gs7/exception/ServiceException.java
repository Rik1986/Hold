package com.gs7.exception;

/**
 * 
 * @author yongbiaoli
 * 
 */
public class ServiceException extends Exception {

    public static ServiceException systemServiceException = new ServiceException(ErrorCodeEnum.SYSTEM_ERROR);

    /**
     * 
     */
    private static final long serialVersionUID = 8648011056274697788L;

    private ErrorCodeEnum errorCodeEnum;

    public ServiceException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.toString(), cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum, Throwable cause, String msg) {
        super(errorCodeEnum.toString() + "  " + msg, cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(errorCodeEnum.toString() + "  " + msg);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.toString());
        this.errorCodeEnum = errorCodeEnum;

    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public int getErrorCode() {
        return errorCodeEnum.code;
    }

    public String getErrorMsg() {
        return getMessage();
    }

}
