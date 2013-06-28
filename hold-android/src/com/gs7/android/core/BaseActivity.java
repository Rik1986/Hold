package com.gs7.android.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

    public void openActivity(Class<?> cls) {
        openActivity(cls, null, 0);
    }

    public void openActivity(Class<?> cls, Bundle b) {
        openActivity(cls, b, 0);
    }

    public void openActivity(Class<?> cls, Bundle b, int requestCode) {
        Intent i = new Intent(this, cls);
        if (b != null)
            i.putExtras(b);
        if (requestCode > 0)
            startActivityForResult(i, requestCode);
        else
            startActivity(i);
    }
}
