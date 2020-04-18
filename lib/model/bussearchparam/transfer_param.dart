import '../core/latlonpoint.dart';

///公交换乘请求参数model
class BusTransferQueryParam{
  static const int MODEL_BUS_DEFAULT = 0;
  static const int MODEL_BUS_SAVE_MONEY = 1;
  static const int MODEL_BUS_LEASE_CHANGE = 2;
  static const int MODEL_BUS_LEASE_WALK = 3;
  static const int MODEL_BUS_COMFORTABLE = 4;
  static const int MODEL_BUS_NO_SUBWAY = 5;
  FromAndTo fromAndTo;
  int model;
  String city;
  int nightFlag;

  BusTransferQueryParam(this.fromAndTo,this.city,[this.model = MODEL_BUS_LEASE_WALK,this.nightFlag = 0]);

  Map<String, dynamic> toMap(){
    Map<String, dynamic> map = Map();
    map['fromAndTo'] = this.fromAndTo.toMap();
    map['model'] = this.model;
    map['city'] = this.city;
    map['nightFlag'] = this.nightFlag;
    return map;
  }
}
///从出发地的到目的地的坐标数据封装实体类
class FromAndTo{
  LatLonPoint from;
  LatLonPoint to;
  FromAndTo(this.from,this.to);
  FromAndTo.fromMap(Map<String,dynamic> map){
    this.from = LatLonPoint.fromMap(map["from"]);
    this.to = LatLonPoint.fromMap(map["to"]);
  }

  Map<String, dynamic> toMap(){
    Map<String, dynamic> map = Map();
    map['from'] = this.from.toMap();
    map['to'] = this.to.toMap();
    return map;
  }
}