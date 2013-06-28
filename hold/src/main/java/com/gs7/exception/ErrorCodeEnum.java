package com.gs7.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yongbiaoli
 */
public enum ErrorCodeEnum {

    /** 系统错误定义 */
    SYSTEM_UNKNOW(10000, "未知异常", "未知异常"), //
    SYSTEM_ERROR(10001, "系统错误", "系统错误"), //
    SYSTEM_IP_LIMIT(10002, "IP限制不能请求该资源", "IP限制不能请求该资源"), //
    SYSTEM_APPID_NOT_SUPPORT(10003, "没有此appid", "没有此appid"), //
    SYSTEM_APPID_AUTHOR(10004, "appid权限错误", "appid权限错误"), //
    SYSTEM_API_NOT_FOUND(10005, "接口不存在", "接口不存在"), //
    SYSTEM_THIRD_NOT_SUPPORT(10006, "此第三方不支持", "此第三方不支持"), //
    SYSTEM_THIRD_NOT_ONNECTED(10007, "此用户没有授权,找不到token", "此用户没有授权,找不到token"), // userid,app,clientName//
    SYSTEM_PARAM_REQUIRED_MISS(10008, "缺少必要参数", "缺少必要参数"), // 返回必须的参数,请求的参数
    SYSTEM_PARAM_ERROR(10009, "参数错误", "参数错误"), // 返回错误的参数,需要的格式
    SYSTEM_GET_IMAGE_FAILED(10010, "获取图片失败", "获取图片失败"), //
    SYSTEM_IMAGE_SIZE_IS_OUT_OF_LIMIT(10011, "图片大小超限", "图片大小超限"), //
    SYSTEM_IMAGE_TYPE_IS_NOT_SUPPORTED(10012, "图片类型不支持", "图片类型不支持"), //
    SYSTEM_PROVIDER_ERROR(10013, "没有此provider", "没有此provider"), //

    /** 业务逻辑 **/
    //
    BINDING_FAIL(20000, "绑定错误", "绑定错误"), //
    ;

    /** 错误号 */
    public final int code;

    /** 错误名称内部 */
    public final String text;

    /** 错误描述外部 */
    public final String errMsg;

    ErrorCodeEnum(int code, String text, String errMsg) {
        this.code = code;
        this.text = text;
        this.errMsg = errMsg;
    }

    private static Map<Integer, ErrorCodeEnum> errorCodeEnumMap = new HashMap<Integer, ErrorCodeEnum>();

    static {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            errorCodeEnumMap.put(errorCodeEnum.code, errorCodeEnum);
        }
    }

    public static ErrorCodeEnum getErrorCodeEnumFromId(int code) {
        return errorCodeEnumMap.get(code);
    }

    @Override
    public String toString() {
        return "code:" + this.code + ",text:" + text + ",errMsg:" + errMsg;
    }

}
