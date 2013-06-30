package com.gs7.android.hold.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.gs7.android.R;
import com.gs7.android.hold.activity.task.TaskActivity;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addTab();
    }

    protected void addTab() {
        TabHost tabHost = getTabHost();
        //设置背景颜色
        tabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
        //        tabHost.addTab(tabHost.newTabSpec("tab_test1").setIndicator(getString(R.string.main))
        //                .setContent(new Intent(this, LoginActivity.class)));
        LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);
        Intent taskIntent = new Intent(this, TaskActivity.class);

        TabSpec tabSpec1 = tabHost.newTabSpec("tabSpec_1");
        TabSpec tabSpec2 = tabHost.newTabSpec("tabSpec_2");
        TabSpec tabSpec3 = tabHost.newTabSpec("tabSpec_3");

        tabHost.addTab(tabSpec1.setIndicator(getString(R.string.main)).setContent(taskIntent));
        tabHost.addTab(tabSpec2.setIndicator(getString(R.string.plaza)).setContent(R.id.txt2));
        tabHost.addTab(tabSpec3.setIndicator(getString(R.string.setting)).setContent(R.id.txt3));

        //        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
        //
        //            @Override
        //            public void onTabChanged(String tagString) {
        //                System.out.println(tagString + "=========");
        //                Intent intent = new Intent(,ListViewActivity.class); 
        //            }
        //        });

        //设置显示当前标签(设置第一个)
        tabHost.setCurrentTab(0);
    }

    //
    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        menu.clear();
    //        MenuInflater inflater = getMenuInflater();
    //        
    //        inflater.inflate(menuRes, menu)
    //
    //        return super.onCreateOptionsMenu(menu);
    //    }
}
