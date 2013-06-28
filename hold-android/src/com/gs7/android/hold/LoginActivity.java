package com.gs7.android.hold;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gs7.android.core.BaseActivity;
import com.gs7.android.core.utils.StringUtils;
import com.gs7.android.hold.activity.MainActivity;
import com.gs7.android.hold.common.Constant;

public class LoginActivity extends BaseActivity {

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(Constant.PROPERTIES_FILE_NAME, MODE_PRIVATE);

        String uid = sp.getString("uid", "");
        String token = sp.getString("token", "");

        if (!StringUtils.isNullOrEmpty(uid) && !StringUtils.isNullOrEmpty(token)) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!");
            Constant.uid = uid;
            Constant.token = token;
            Editor editor = sp.edit();
            editor.clear();
            editor.commit();
            finish();
            this.openActivity(MainActivity.class);
        } else {
            webView = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    System.out.println(url + "============");
                    if (url.contains("afterLogin?jsonValue=")) {
                        //                    if (url.contains("success?uid")) {
                        //http://10.16.12.16:8002/success?uid=32&token=6e6a7d49d9c8603663262871f6a27e80
                        //                        String uid = url.substring(url.indexOf("uid=") + "uid=".length(), url.lastIndexOf("&"));
                        //                        String token = url.substring(url.indexOf("token=") + "token=".length(), url.length());

                        String uid = "32";
                        String token = "6e6a7d49d9c8603663262871f6a27e80";
                        Constant.uid = uid;
                        Constant.token = token;

                        SharedPreferences sp = getSharedPreferences(Constant.PROPERTIES_FILE_NAME, MODE_PRIVATE);
                        Editor editor = sp.edit();
                        editor.putString("uid", uid);
                        editor.putString("token", token);
                        editor.commit();
                        finish();
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        Intent i = new Intent(view.getContext(), MainActivity.class);
                        startActivity(i);

                    }
                    super.onPageStarted(view, url, favicon);
                }

            });
            webView.loadUrl(Constant.HOST + "/m/account/connect?provider=sina");
            setContentView(webView);
        }
    }

}
