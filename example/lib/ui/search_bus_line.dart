import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:amapbus/amapbus.dart';
import 'package:city_pickers/city_pickers.dart';
import 'package:amapbus/model/bussearchparam/line_param.dart';

class SearchBusLine extends StatefulWidget {
  @override
  _SearchBusLineState createState() => _SearchBusLineState();
}

class _SearchBusLineState extends State<SearchBusLine> {
  String _busLine = "暂无数据";
  final BusLineQueryParam _blq = BusLineQueryParam("",BusLineQueryParam.CATEGORY_SearchType_BY_LINE_NAME,"");
  String _city = "";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("公交线路查询"),
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
              textInputAction: TextInputAction.search,
              decoration: InputDecoration(
                hintText: "请输入公交线路",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  )
              ),
              onChanged: (val) async{
                if(val == null || val.length == 0 || val == '')
                  return;
                _blq.query = val;
                _blq.city = _city;
                final String s = await Amapbus.searchBusLine(_blq);
                setState(() {
                  _busLine = s;
                });
              },
              onSubmitted: (val){},
            ),
            SizedBox(height: 10,),
            Expanded(
              child: ListView(
                children: <Widget>[
                  Text("查询结果:",style: TextStyle(fontSize: 14),),
                  Text("$_busLine")
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
