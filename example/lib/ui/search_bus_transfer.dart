import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:amapbus/amapbus.dart';

import 'package:amapbus/model/core/latlonpoint.dart';
import 'package:amapbus/model/bussearchparam/station_param.dart';
import 'package:amapbus/model/bussearchparam/transfer_param.dart';
import 'package:city_pickers/city_pickers.dart';
class SearchBusTransfer extends StatefulWidget {
  @override
  _SearchBusTransferState createState() => _SearchBusTransferState();
}

class _SearchBusTransferState extends State<SearchBusTransfer> {
  String _busTransfer = "暂无数据";
  final TextEditingController _controller1 = TextEditingController();
  final TextEditingController _controller2 = TextEditingController();
  final BusStationQueryParam _bsq = BusStationQueryParam("","");
  String city = "";
  FromAndTo fromAndTo = FromAndTo(LatLonPoint(0,0),LatLonPoint(10,10));
  BusTransferQueryParam _btq = BusTransferQueryParam(null,"");
  final FocusNode _focusNode1 = FocusNode();
  final FocusNode _focusNode2 = FocusNode();

  List _items = List(0);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("公交换乘查询"),
        actions: <Widget>[
          GestureDetector(
            child: Icon(Icons.location_city),
            onTap: ()async{
              final Result result = await CityPickers.showCityPicker(
                showType: ShowType.pc,
                context: context,
                locationCode: "310100",
              );
              if(result == null || result.cityName == ''){
                return;
              }else{
                setState(() {
                  city = result.cityName;
                });
              }

            },
          )
        ],
      ),
      body: Center(
        child: Column(
          children: <Widget>[

            SizedBox(height: 3,),
            TextField(
              controller: _controller1,
              keyboardType: TextInputType.text,
              enableSuggestions: true,
              decoration: InputDecoration(
                  hintText: "起点",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  )
              ),
              inputFormatters: [
                WhitelistingTextInputFormatter(RegExp(r"[a-zA-Z]|[\u4e00-\u9fa5]|[0-9]"))
              ],
              onChanged: (val) async{
                RegExp r = RegExp("[\u4e00-\u9fa5]");
                if( !r.hasMatch(val) || val == null || val.length == 0 || val == '')
                  return;
                _bsq.query = val;
                _bsq.city = city;
                final String s = await Amapbus.searchBusStation(_bsq);
                Map map = Map<String,dynamic>.from(json.decode(s));
                setState(() {
                  map = Map<String,dynamic>.from(map['result']);
                  _items = map['busStations'];
                });
              },
              focusNode: _focusNode1,
              onSubmitted: (val){},
            ),
            SizedBox(height: 3,),
            TextField(
              controller: _controller2,
              keyboardType: TextInputType.text,
              focusNode: _focusNode2,
              decoration: InputDecoration(
                  hintText: "终点",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  )
              ),
              inputFormatters: [
                WhitelistingTextInputFormatter(RegExp(r"[a-zA-Z]|[\u4e00-\u9fa5]|[0-9]"))
              ],
              onChanged: (val) async{
                RegExp r = RegExp("[\u4e00-\u9fa5]");
                if( !r.hasMatch(val) || val == null || val.length == 0 || val == '')
                  return;
                _bsq.query = val;
                _bsq.city = city;
                final String s = await Amapbus.searchBusStation(_bsq);
                Map map = Map<String,dynamic>.from(json.decode(s));
                setState(() {
                  map = Map<String,dynamic>.from(map['result']);
                  _items = map['busStations'];
                });
              },
              onSubmitted: (val){},
            ),
            SizedBox(height: 2,),
            SizedBox(child: Text("搜索建议"),),
            Expanded(
              child: ListView.builder(
                itemCount: _items.length,
                itemExtent: 50,
                itemBuilder: (BuildContext context, int index){
                  String s = _items[index]['busStationName'];
                return GestureDetector(
                  child: Text(s),
                  onTap: (){
                    Map map = Map<String,dynamic>.from(_items[index]["latLonPoint"]);
                    if(_focusNode1.hasFocus){
                      _controller1.text = s;
                      _controller1.selection = TextSelection.fromPosition(TextPosition(
                        //affinity: TextAffinity.downstream,
                          offset: s.length
                      )
                      );
                      fromAndTo.from = LatLonPoint.fromMap(map);
                    }else{
                      _controller2.text = s;
                      _controller2.selection = TextSelection.fromPosition(TextPosition(
                        //affinity: TextAffinity.downstream,
                          offset: s.length
                      )
                      );
                    }
                    fromAndTo.to.lon = map['longitude'];
                    fromAndTo.to.lat = map['latitude'];
                  },
                );
              }),
            ),
            SizedBox(height: 10,),
            RaisedButton(
              child: Text("换乘查询"),
              color: Colors.blue,
              onPressed: () async{
                _btq.fromAndTo = fromAndTo;
                _btq.city = city;
                final s = await Amapbus.searchBusTransfer(_btq);
                setState(() {
                  _busTransfer = s;
                });
              },
            ),
            SizedBox(height: 10,),
            Expanded(
              child: ListView(
                children: <Widget>[
                  Text("查询结果:",style: TextStyle(fontSize: 14,color: Colors.blue),),
                  Text("$_busTransfer"),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  @override
  void initState() {
    super.initState();
  }
}

