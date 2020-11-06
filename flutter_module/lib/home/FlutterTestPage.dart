import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/router/RouterUtil.dart';

class FlutterTestPage extends StatefulWidget {
  final Map<dynamic, dynamic> params;

  FlutterTestPage({this.params});

  @override
  State<StatefulWidget> createState() {
    return _InnerState();
  }
}

class _InnerState extends State<FlutterTestPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('FlutterTestPage'),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              Text("接收到参数： ${widget.params.toString()}"),
              RaisedButton(
                child: Text(widget.params.toString().contains("requestCode") ? "返回native并回传值" : "返回native"),
                onPressed: () {
                  RouterUtil.close(context,
                      result: <String, dynamic>{'params': '我是来自于flutter的回传数据'});
                },
              )
            ],
          ),
          color: Colors.blue,
        ));
  }
}
