package com.zwt.androidnative;

import android.app.Application;

import com.zwt.androidnative.flutterboost.FlutterBoostUtil;
import com.zwt.androidnative.router.RouterUtil;

/**
 * Created by Android Studio.
 * User: ZWT
 * Date: 2020/10/28
 * Time: 15:19
 */
public class ZWTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterUtil.init(this);
        FlutterBoostUtil.init(this);
    }
}
