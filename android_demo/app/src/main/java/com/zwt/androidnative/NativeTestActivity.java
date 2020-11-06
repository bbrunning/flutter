package com.zwt.androidnative;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button finish_btn = (Button) findViewById(R.id.finish_btn);
        if (params != null) {
            params_view.setText(params.toString());
        }
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("params", "我是native 回传到 flutter的数据");
                RouterUtil.setResult(NativeTestActivity.this, data);
                finish();
            }
        });
    }
}