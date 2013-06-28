package com.gs7.android.hold.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gs7.android.R;
import com.gs7.android.core.BaseActivity;
import com.gs7.android.core.utils.HttpUtils;
import com.gs7.android.core.utils.HttpUtils.MethodType;
import com.gs7.android.core.utils.JsonUtils;
import com.gs7.android.hold.common.Constant;

public class TaskActivity extends BaseActivity {

    private ListView listView;

    private final static String url = Constant.HOST + "/m/plan/list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        listView = (ListView) findViewById(R.id.taskListView);
        try {
            Map map = new HashMap();
            map.put("uid", Constant.uid);
            String json = HttpUtils.getHttpUtils().httpQuery(url, map, MethodType.GET);
            System.out.println("@@@@@" + json);
            Map<String, Object> jsonMap = JsonUtils.objectMapper.readValue(json, Map.class);
            listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, getData(jsonMap)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List getData(Map<String, Object> jsonMap) {
        List data = new ArrayList();
        List tasks = (List) jsonMap.get("result");

        if (tasks != null && tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                data.add(tasks.get(i));
            }
        }

        return data;
    }
}
