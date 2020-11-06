package com.zwt.androidnative;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zwt.androidnative.router.RouterUtil;

import java.util.HashMap;
import java.util.Map;

@Route(path = RouterUtil.NATIVE_MAIN_PAGE)
public class MainActivity extends Activity {
    public static final int REQUEST_CODE_KEY = 1002;

    private Context mContext;
    private TextView mBackDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        Button open_flutter_main = (Button) findViewById(R.id.open_flutter_main);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        mBackDataView = (TextView) findViewById(R.id.back_data);


        open_flutter_main.setOnClickListener(v -> RouterUtil.openPageByUrl(mContext, RouterUtil.FLUTTER_MAIN_PAGE, null));

        btn1.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("params", "我是来自于native的数据");
            RouterUtil.openPageByUrl(mContext, RouterUtil.FLUTTER_TEST_PAGE, map);
        });

        btn2.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("params", "我是来自于native的数据 ，并设置了requestCode");
            RouterUtil.openPageByUrl(mContext, RouterUtil.FLUTTER_TEST_PAGE, map, REQUEST_CODE_KEY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_KEY) {
            Map<String, Object> params = RouterUtil.getResultParams(data);
            if (params != null && params.size() > 0) {
                Log.e("MainActivity", "params [" + params.toString() + "]");
                if (mBackDataView != null) {
                    mBackDataView.setText("onActivityResult [" + params.toString() + "]");
                }
            }
        }
    }
}