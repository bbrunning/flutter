import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/router/RouterUtil.dart';

class FlutterMainPage extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return _InnerState();
  }
}

class _InnerState extends State<FlutterMainPage>{
  Map<dynamic, dynamic> _value;
  
  @override
  Widget build(BuildContext context) {
   return Scaffold(
     appBar: AppBar(title: Text('MainPage'),),
     body: Column(
       children: <Widget>[
         Text("接收回传数据${_value.toString()}"),
         RaisedButton(
           child: Text('flutter 跳转 native'),
           onPressed: () {
             RouterUtil.open(NATIVE_TEST_PAGE, urlParams: <String, dynamic> {'params':"request data"}).then((Map<dynamic, dynamic> value) {
               setState(() {
                 _value = value;
               });
             });
           },
         )
       ],
     ),
   );
  }

}