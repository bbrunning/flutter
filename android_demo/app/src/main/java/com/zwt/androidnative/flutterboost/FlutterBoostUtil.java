package com.zwt.androidnative.flutterboost;

import android.app.Application;
import android.util.Log;

import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.Platform;
import com.idlefish.flutterboost.Utils;
import com.idlefish.flutterboost.interfaces.INativeRouter;
import com.zwt.androidnative.router.RouterUtil;

import io.flutter.embedding.android.FlutterView;

/**
 * Created by Android Studio.
 * User: ZWT
 * Date: 2020/10/28
 * Time: 15:20
 */
public class FlutterBoostUtil {
   private static final String TAG = FlutterBoostUtil.class.getSimpleName();
    public static void init(Application context) {

        INativeRouter router = (context1, url, urlParams, requestCode, exts) -> {
            String assembleUrl = Utils.assembleUrl(url, urlParams);
            RouterUtil.openPageByUrl(context1, assembleUrl, urlParams, requestCode);
        };

        // 生命周期监听
        FlutterBoost.BoostLifecycleListener boostLifecycleListener = new FlutterBoost.BoostLifecycleListener() {

            @Override
            public void beforeCreateEngine() {
                Log.i(TAG, "inApplicationOnCreate beforeCreateEngine");
            }

            @Override
            public void onEngineCreated() {
                // 引擎创建后的操作，比如自定义MethodChannel，PlatformView等
                Log.i(TAG, "inApplicationOnCreate onEngineCreated");
            }

            @Override
            public void onPluginsRegistered() {
                Log.i(TAG, "inApplicationOnCreate onPluginsRegistered");
            }

            @Override
            public void onEngineDestroy() {
                Log.i(TAG, "inApplicationOnCreate onEngineDestroy");
            }

        };

        // 生成Platform配置
        Platform platform = new FlutterBoost
                .ConfigBuilder((Application) context, router)
                .isDebug(true)
                //.dartEntrypoint() //dart入口，默认为main函数，这里可以根据native的环境自动选择Flutter的入口函数来统一Native和Flutter的执行环境，（比如debugMode == true ? "mainDev" : "mainProd"，Flutter的main.dart里也要有这两个对应的入口函数）
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build();
        // 初始化
        FlutterBoost.instance().init(platform);
    }
}
