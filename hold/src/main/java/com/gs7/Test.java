package com.gs7;

import java.awt.Desktop;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinaren.framework.common.http.MultiHttpPool;
import com.chinaren.framework.common.util.MD5Utils;

public class Test {

    public static String word = "客户端用token加上这句话再加个随机数做md5";

    //public static String MOBILEURI = "http://10.16.12.16:8002";

    public static String MOBILEURI = "http://127.0.0.1:8002";

    private static final String uid = "7";

    private static final String salt = "66932";

    public static void query(String url, Map<String, Object> paramsMap, String methodType) throws Exception {
        MultiHttpPool httpPool = MultiHttpPool.getMultiHttpUtils(1000000);
        Map<String, String> headMap = new HashMap<String, String>();
        String mobileRandom = "111";
        String mobileSecretKey = MD5Utils.getMD5Str(MD5Utils.getMD5Str("" + uid + salt) + word + mobileRandom);
        headMap.put("mobileUid", uid);
        headMap.put("mobileSecretKey", mobileSecretKey);
        headMap.put("mobileRandom", mobileRandom);
        System.out.println(httpPool.httpQuery(url, paramsMap, methodType, "utf-8", headMap));
    }

    /**
     * 客户端授权测试
     * 
     * @throws Exception
     */
    public static void testMobileClientLogin() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("provider", "1");
        paramsMap.put("identity", "");
        paramsMap.put("expiresIn", "");
        paramsMap.put("accessToken", "");
        paramsMap.put("refreshToken", "");
        query(MOBILEURI + "/m/accountClient/afterLogin", paramsMap, "get");
    }

    /**
     * 客户端测试创建账号
     * 
     * @throws Exception
     */
    public static void testCreate(String provider) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("provider", provider);
        Desktop.getDesktop().browse(new URI(MOBILEURI + "/m/account/connect?provider=sina"));

    }

    /**
     * 客户端测试绑定
     * 
     * @throws Exception
     */
    public static void testBinding(String provider) throws Exception {
        URI uri = new URI(MOBILEURI + "/m/account/connect?provider=" + provider);
        Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
        String mobileRandom = "111";
        String mobileSecretKey = MD5Utils.getMD5Str(MD5Utils.getMD5Str("" + uid + salt) + word + mobileRandom);
        responseHeaders.put("mobileUid", Arrays.asList(uid));
        responseHeaders.put("mobileSecretKey", Arrays.asList(mobileSecretKey));
        responseHeaders.put("mobileRandom", Arrays.asList(mobileRandom));
        CookieHandler.getDefault().put(uri, responseHeaders);
        Desktop.getDesktop().browse(uri);
    }

    /**
     * 我的信息
     * 
     * @throws Exception
     */
    public static void testUserInfo() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        
        query(MOBILEURI + "/m/user/userInfo", paramsMap, "get");
    }
    
    /**
     * 我的任务list
     * 
     * @throws Exception
     */
    public static void testPlanList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("uid", uid);
        query(MOBILEURI + "/m/plan/list", paramsMap, "get");
    }

    /**
     * 别人的任务list
     * 
     * @throws Exception
     */
    public static void testOtherPlanList(String uid) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("uid", uid);
        query(MOBILEURI + "/m/plan/list", paramsMap, "get");
    }

    /**
     * 测试创建任务
     * 
     * @throws Exception
     */
    public static void testCreatePlan(String cid, String title, String desc) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("cid", cid);
        paramsMap.put("title", title);
        paramsMap.put("desc", desc);
        query(MOBILEURI + "/m/plan/createPlan", paramsMap, "get");
    }

    /**
     * 测试更新任务
     * 
     * @throws Exception
     */
    public static void testUpdatePlan(String status, String pid) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("status", status);
        paramsMap.put("pid", pid);
        query(MOBILEURI + "/m/plan/update", paramsMap, "get");
    }

    /**
     * 测试评价别人的计划<br>
     * type==1 good<br>
     * type=2 bad
     * 
     * @throws Exception
     */
    public static void testcommentPlan(String type, String pid) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("type", type);
        paramsMap.put("pid", pid);
        query(MOBILEURI + "/m/plan/comment", paramsMap, "get");
    }

    /**
     * 测试记录列表
     * 
     * @throws Exception
     */
    public static void testLogList(String pid,String cursor,String size) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("pid", pid);
        paramsMap.put("cursor", cursor);
        paramsMap.put("size", size);
        query(MOBILEURI + "/m/log/list", paramsMap, "get");
    }

    /**
     * 测试创建记录
     * 
     * @throws Exception
     */
    public static void testcreatelog(String[] providers, String pid, String date, String success, String content)
            throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        for (String provider : providers) {
            paramsMap.put("provider", provider);
        }
        paramsMap.put("pid", pid);
        paramsMap.put("date", date);
        paramsMap.put("success", success);
        paramsMap.put("content", content);
        query(MOBILEURI + "/m/log/create", paramsMap, "get");
    }

    public static void main(String[] sdg) throws Exception {
        // testMobileClientLogin();
        //testCreate("sina");
        // testBinding("tqq");
        // testPlanList();
    	//testUserInfo();
        // testOtherPlanList("uid");
        // testCreatePlan(cid, title, desc);
        // testUpdatePlan(status, pid);
        // testcommentPlan(type, pid);
         testLogList("1","0","20");
        // testcreatelog(new String[]{"sina","tqq"}, pid, date, success,
        // content);
    }
}
