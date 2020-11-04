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
                child: Text("关闭并回传值"),
                onPressed: () {
                  RouterUtil.close(context,
                      result: <String, dynamic>{'params': 'back data'});
                },
              )
            ],
          ),
          color: Colors.blue,
        ));
  }
}
