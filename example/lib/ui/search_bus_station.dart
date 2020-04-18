import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:amapbus/amapbus.dart';
import 'package:amapbus/model/bussearchparam/station_param.dart';
import 'package:flutter/services.dart';
import 'package:city_pickers/city_pickers.dart';
class SearchBusStation extends StatefulWidget {
  @override
  _SearchBusStaionState createState() => _SearchBusStaionState();
}

class _SearchBusStaionState extends State<SearchBusStation> {
  String _busStation = "暂无数据";
  final BusStationQueryParam _bsq = BusStationQueryParam("","");
  String _city = "";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("公交站点查询"),
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
                  _city = result.cityName;
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
              keyboardType: TextInputType.text,
              decoration: InputDecoration(
                  hintText: "请输入公交站点",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  )
              ),
              inputFormatters: [
                WhitelistingTextInputFormatter(RegExp(r"[a-zA-Z]|[\u4e00-\u9fa5]"))
              ],
              onChanged: (val) async{
                RegExp r = RegExp("[\u4e00-\u9fa5]");
                if( !r.hasMatch(val) || val == null || val.length == 0 || val == '')
                  return;
                _bsq.query = val;
                _bsq.city = _city;
                final String s = await Amapbus.searchBusStation(_bsq);
                setState(() {
                  _busStation = s;
                });
              },
              onSubmitted: (val){},
            ),
            SizedBox(height: 10,),
            Expanded(
              child: ListView(
                children: <Widget>[
                  Text("查询结果:",style: TextStyle(fontSize: 14),),
                  Text("$_busStation")
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}

