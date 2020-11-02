import 'package:flutter/cupertino.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_module/home/FlutterMainPage.dart';
import 'package:flutter_module/home/FlutterTestPage.dart';

const String MAIN_PAGE = "flutter://flutterMainPage";
const String TEST_PAGE = "flutter://flutterTestPage";

const String NATIVE_MAIN_PAGE = "native://main/nativemainpage";
const String NATIVE_TEST_PAGE = "native://test/nativetestpage";

class RouterUtil {
  //初始化
  static void init() {
    registerRouters();
    FlutterBoost.singleton.addBoostNavigatorObserver(BoostNavigatorObserver());
  }

  //注册页面
  static void registerRouters() {
    FlutterBoost.singleton.registerPageBuilders(<String, PageBuilder>{
      MAIN_PAGE: (String pageName, Map<dynamic, dynamic> params, String _) =>
          FlutterMainPage(),
      TEST_PAGE: (String pageName, Map<dynamic, dynamic> params, String _) =>
          FlutterTestPage(params: params),
    });
  }

  //打开页面
  static Future<Map> open(
      String url, {Map<dynamic, dynamic> urlParams, Map<dynamic, dynamic> exts}) {
    return FlutterBoost.singleton.open(url, urlParams: urlParams, exts: exts);
  }

  //关闭页面
  static Future<bool> close(BuildContext context,
      {Map<dynamic, dynamic> result, Map<dynamic, dynamic> exts}) {
    String uniqueId = BoostContainer.of(context).uniqueId;
    return FlutterBoost.singleton.close(uniqueId, result: result, exts: exts);
  }

  static TransitionBuilder initPageListener() {
    return FlutterBoost.init(postPush: RouterUtil.onRoutePushed);
  }

  //监听页面跳转情况
  static void onRoutePushed(
    String pageName,
    String uniqueId,
    Map<dynamic, dynamic> params,
    Route<dynamic> route,
    Future<dynamic> _,
  ) {}
}

class BoostNavigatorObserver extends NavigatorObserver {
  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    print("RouterUtil didPush");
  }

  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
    print("RouterUtil didPop");
  }

  @override
  void didRemove(Route route, Route previousRoute) {
    super.didRemove(route, previousRoute);
    print("RouterUtil didRemove");
  }

  @override
  void didReplace({Route newRoute, Route oldRoute}) {
    super.didReplace(newRoute: newRoute, oldRoute: oldRoute);
    print("RouterUtil didReplace");
  }
}
