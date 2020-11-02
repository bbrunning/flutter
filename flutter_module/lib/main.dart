import 'package:flutter/material.dart';
import 'package:flutter_module/home/FlutterMainPage.dart';
import 'package:flutter_module/router/RouterUtil.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _InnerState();
  }
}

class _InnerState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    RouterUtil.init();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "app混编 flutter_boost",
      builder: RouterUtil.initPageListener(),
      home: FlutterMainPage(),
    );
  }
}
