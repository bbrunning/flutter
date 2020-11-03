package com.zwt.androidnative;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zwt.androidnative.router.RouterUtil;

import java.util.HashMap;
import java.util.Map;

@Route(path = RouterUtil.NATIVE_TEST_PAGE)
public class NativeTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_test);
        Map<String, Object> params = RouterUtil.getParams(getIntent());
        TextView params_view = (TextView) findViewById(R.id.params_view);
        if (params != null) {
            params_view.setText(params.toString());
        }
    }

    @Override
    public void finish() {
        Map<String, Object> data = new HashMap<>();
        data.put("params", "back data");
        RouterUtil.setResult(this, data);
        super.finish();
    }
}