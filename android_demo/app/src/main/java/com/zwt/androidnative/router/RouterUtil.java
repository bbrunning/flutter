package com.zwt.androidnative.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;
import com.idlefish.flutterboost.interfaces.IFlutterViewContainer;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: ZWT
 * Date: 2020/10/28
 * Time: 16:15
 */
public class RouterUtil {

    //参数key
    public static final String PAGE_PARAMS = "params";
    //协议
    public static final String PROTOCOL_NATIVE = "native";

    //flutter 路由
    public static final String FLUTTER_MAIN_PAGE = "flutter://flutterMainPage";
    public static final String FLUTTER_TEST_PAGE = "flutter://flutterTestPage";

    //native 路由
    public static final String NATIVE_MAIN_PAGE = "/main/nativemainpage";
    public static final String NATIVE_TEST_PAGE = "/test/nativetestpage";

    private static boolean isDebug() {
        return false;
    }

    public static void init(Application application) {
        if (isDebug()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    public static Postcard getRouterBuilder(String path) {
        return ARouter.getInstance().build(path);
    }

    //打开原生页面
    public static void open(Context context, String path, Map params, int requestCode) {
        Bundle bundle = new Bundle();
        if (params != null && params.size() > 0) {
            bundle.putSerializable(PAGE_PARAMS, (Serializable) params);
        }
        if (context instanceof Activity && requestCode != -1) {
            getRouterBuilder(path).with(bundle).navigation((Activity) context, requestCode);
        } else {
            getRouterBuilder(path).with(bundle).navigation();
        }

    }

    // Native和Flutter的映射处理
    public static void openPageByUrl(Context context, String url, Map params) {
        openPageByUrl(context, url, params, -1);
    }

    //Native和Flutter的映射处理
    public static void openPageByUrl(Context context, String url, Map params, int requestCode) {
        Log.i("RouterUtil", "url [" + url + "] params [" + (params != null ? params.toString() : "is null") + "] requestCode [" + requestCode + "]");
        //获取路由path
        String path = url.split("\\?")[0];
        // 根据协议判断url是native 还是 flutter
        boolean isNative = isNative(url);
        Log.i("RouterUtil", "isNative [" + isNative + "]");
        if (isNative) {
            String nativePath = getBoostToRouter(path);
            Log.i("RouterUtil", "nativePath [" + nativePath + "]");
            if (TextUtils.isEmpty(nativePath)) {
                return;
            }
            open(context, nativePath, params, requestCode);
        } else {
            Intent intent = BoostFlutterActivity
                    .withNewEngine()
                    .url(url)
                    .params(params)
                    .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque)
                    .build(context);
            if (context instanceof Activity && requestCode != -1) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
        }
    }

    //是否是native路由
    private static boolean isNative(String url) {
        return TextUtils.equals(url.split("://")[0], PROTOCOL_NATIVE);
    }

    //将传递的path转换成native路由
    public static String getBoostToRouter(String path) {
        String nativePath = null;
        if (!TextUtils.isEmpty(path)) {
            String[] aar = path.split("://");
            if (aar.length > 1) {
                nativePath = aar[1];
                nativePath = "/" + nativePath;
            }
        }
        return nativePath;
    }

    //获取Flutter传递数据
    public static Map<String, Object> getParams(Intent intent) {
        Map<String, Object> params = null;
        if (intent != null) {
            Serializable result = intent.getSerializableExtra(PAGE_PARAMS);
            if (result instanceof Map) {
                params = (Map<String, Object>) result;
            }
        }
        return params;
    }

   //获取Flutter回传数据
    public static Map<String, Object> getResultParams(Intent intent) {
        Map<String, Object> params = null;
        if (intent != null) {
            Serializable result = intent.getSerializableExtra(IFlutterViewContainer.RESULT_KEY);
            if (result instanceof Map) {
                params = (Map<String, Object>) result;
            }
        }
        return params;
    }

    //设置回传给Flutter数据
    public static void setResult(Activity activity, Map<String, Object> map) {
        Intent intent = new Intent();
        intent.putExtra(IFlutterViewContainer.RESULT_KEY, (Serializable) map);
        activity.setResult(Activity.RESULT_OK, intent);
    }
}
