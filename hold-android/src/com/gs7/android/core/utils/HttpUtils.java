package com.gs7.android.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.gs7.android.hold.common.Constant;

public class HttpUtils {

    private DefaultHttpClient httpClient = null;

    private static HttpUtils util;

    public static HttpUtils getHttpUtils() {
        if (util == null)
            util = new HttpUtils();
        return util;
    }

    private HttpUtils() {

        //        SchemeRegistry sr = new SchemeRegistry();
        //        sr.register(new Scheme("https", new MySecureSocketFactory(), 443));
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpProtocolParams.setHttpElementCharset(params, "utf-8");
        //        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, sr);
        httpClient = new DefaultHttpClient(params);
    }

    public String httpQuery(String url, Map<String, Object> paramsMap, MethodType methodType) throws Exception {
        return httpQuery(url, paramsMap, methodType, "utf-8", getHeadMap());
    }

    public String httpQuery(String url, Map<String, Object> paramsMap, MethodType methodType, String charSet,
            Map<String, String> headMap) throws Exception {
        HttpRequestBase method = null;
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                Object objValue = paramsMap.get(key);
                if (objValue == null) {
                    continue;
                }
                if (objValue instanceof List) {
                    List values = (List) objValue;
                    for (Object value : values) {
                        formparams.add(new BasicNameValuePair(key, value.toString()));
                    }
                } else {
                    formparams.add(new BasicNameValuePair(key, objValue.toString()));

                }
            }
        }

        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if ("post".equals(methodType)) {
            method = new HttpPost(url);
            ((HttpPost) method).setEntity(entity);
        } else if ("put".equals(methodType)) {
            method = new HttpPut(url);
            ((HttpPut) method).setEntity(entity);
        } else if ("delete".equals(methodType)) {
            if (url.contains("?")) {
                url = url + (formparams.size() == 0 ? "" : "&") + URLEncodedUtils.format(formparams, "UTF-8");
            } else {
                url = url + (formparams.size() == 0 ? "" : "?") + URLEncodedUtils.format(formparams, "UTF-8");
            }
            method = new HttpDelete(url);
        } else {
            if (url.contains("?")) {
                url = url + (formparams.size() == 0 ? "" : "&") + URLEncodedUtils.format(formparams, "UTF-8");
            } else {
                url = url + (formparams.size() == 0 ? "" : "?") + URLEncodedUtils.format(formparams, "UTF-8");
            }
            method = new HttpGet(url);
        }
        if (headMap != null) {
            for (String key : headMap.keySet()) {
                method.setHeader(key, headMap.get(key));
            }
        } else {
            // default http head
            method.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.22 (KHTML, like Gecko) Chrome/19.0.1049.3 Safari/535.22");
            method.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
            method.setHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.7");
            method.setHeader("Connection", "keep-alive");
        }
        HttpResponse response = httpClient.execute(method);
        System.out.println("######" + response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == 298) {
            return EntityUtils.toString(response.getEntity());
        }
        return null;
    }

    private Map<String, String> getHeadMap() {
        Map<String, String> headMap = new HashMap<String, String>();
        int random = new Random().nextInt();
        random = Math.abs(random);
        String key = Constant.token + Constant.WORD + random;
        key = MD5Utils.getMD5Str(key);
        headMap.put("mobileUid", Constant.uid);
        headMap.put("mobileSecretKey", key);
        headMap.put("mobileRandom", String.valueOf(random));
        System.out.println("%%%%%%% random:" + random + " mobileUid:" + Constant.uid + "  token:" + Constant.token);
        return headMap;
    }

    public enum MethodType {
        POST("post"), GET("get"), DELETE("delete"), PUT("put");

        private String name;

        private MethodType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
