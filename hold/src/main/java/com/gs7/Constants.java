package com.gs7;

public class Constants {

    public final static String DEFAULT_IMAGE_DATA = "{\"w\":128,\"h\":128,\"ext\":{\"s\":{\"w\":48,\"h\":48}},\"key\":\"agth29wu\",\"extension\":\"png\"}";

    /** service的默认配置文件 */
    public final static String DEFAULT_SERVICE_PROPERTIES = "service.properties";

    /** control的默认配置文件 */
    public final static String DEFAULT_CONTROL_PROPERTIES = "web.properties";

    public final static String DEFAULT_COMMON_PROPERTIES = "common.properties";

    /** 官方appstore */
    public final static String PROMOTION_DEFAULT = "0000";

    /** 官方破解 */
    public final static String PROMOTION_POJIE = "0010";

    /** 91助手 */
    public final static String PROMOTION_91 = "0001";

    /** 快装商店 */
    public final static String PROMOTION_KUAIZHUANG = "0002";

    /** pp助手 */
    public final static String PROMOTION_PP = "0003";

    /** 贝壳助手 */
    public final static String PROMOTION_BEIKE = "0004";

    /** 同步推付费 */
    public final static String PROMOTION_TONGBUTUI = "0005";

    /** 搜狐应用中心 */
    public final static String PROMOTION_SOHUAPP = "0006";

    /** 微信 */
    public final static String PROMOTION_WEIXIN = "0007";

    /** 新闻客户端 */
    public final static String PROMOTION_SOHUNEWS = "0008";

    // 要加到配置文件里去，不要用常量
    public final static String thirdCloudUrl = "http://router.chinaren.com/connect";

    public final static String thirdCloudApiUrl = "http://router.chinaren.com/api";

    public final static String thirdCloudAppid = "1002";

    public final static String cookie_uid = "TV_u";

    public final static String cookie_token = "TV_t";

    public final static int cookie_time = 60 * 60 * 24 * 14;

    public final static String NAME = "野火视频";

    /** 野火用户的积分图片 */
    public final static String fireIconsFormat = "http://image0.cv.itc.cn/w/fire/fire_%s.png";

    /** 在线用户失效时间 (毫秒30分钟) */

    public final static int onlineUserExpireTime = 1000 * 60 * 30;

    /** 在线用户的key */
    public final static String onlineUserCacheKey = "onlineUserCacheKey";

}
