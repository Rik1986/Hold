package com.gs7.android.hold;

import android.os.Bundle;

import com.gs7.android.R;
import com.gs7.android.core.BaseActivity;

public class HoldActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hold);
        finish();
        this.openActivity(LoginActivity.class);
    }
}
