package com.gs7.service;

import java.util.HashMap;
import java.util.Map;

import com.chinaren.framework.common.http.MultiHttpPool;
import com.chinaren.framework.common.json.ObjectMapperHelper;
import com.gs7.Constants;
import com.sohu.zh.model.ThirdUser;
import com.sohu.zh.request.PublishMsgRequest;
import com.sohu.zh.result.AccessTokenResult;
import com.sohu.zh.result.PublishMsgResult;

public class ThirdCloudServer {

    public static ThirdUser createOrUpdateUser(String ip, String clientName, AccessTokenResult accessTokenResult)
            throws Exception {
        MultiHttpPool httpPool = MultiHttpPool.getMultiHttpUtils();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("ip", ip);
        paramsMap.put("clientName", clientName);
        paramsMap.put("accessToken", accessTokenResult.getAccessToken());
        paramsMap.put("expireIn", accessTokenResult.getExpireIn());
        paramsMap.put("refreshToken", accessTokenResult.getRefreshToken());
        paramsMap.put("uid", accessTokenResult.getUid());
        String result = httpPool.httpQuery(Constants.thirdCloudApiUrl + "/createOrUpdateUser", paramsMap, "post");
        return ObjectMapperHelper.objectMapper.readValue(result, ThirdUser.class);
    }

    // publishMsg
    public static PublishMsgResult publishMsg(PublishMsgRequest publishMsgRequest) throws Exception {
        MultiHttpPool httpPool = MultiHttpPool.getMultiHttpUtils();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("appId", publishMsgRequest.getAppId());
        paramsMap.put("identity", publishMsgRequest.getIdentity());
        paramsMap.put("imageUrl", publishMsgRequest.getImageUrl());
        paramsMap.put("msg", publishMsgRequest.getMsg());
        paramsMap.put("provider", publishMsgRequest.getProvider());
        paramsMap.put("thirdCloudUserId", publishMsgRequest.getThirdCloudUserId());
        paramsMap.put("videoUrl", publishMsgRequest.getVideoUrl());
        String result = httpPool.httpQuery(Constants.thirdCloudApiUrl + "/createOrUpdateUser", paramsMap, "post");
        return ObjectMapperHelper.objectMapper.readValue(result, PublishMsgResult.class);
    }

    // getThirdUser
    public static ThirdUser getThirdUser(long thirdId) throws Exception {
        MultiHttpPool httpPool = MultiHttpPool.getMultiHttpUtils();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", thirdId);
        String result = httpPool.httpQuery(Constants.thirdCloudApiUrl + "/getThirdUser", paramsMap, "post");
        return ObjectMapperHelper.objectMapper.readValue(result, ThirdUser.class);
    }

}
