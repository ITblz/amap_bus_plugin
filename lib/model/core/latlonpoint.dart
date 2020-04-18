///坐标实体类 包含经纬度
class LatLonPoint {
  double lat;
  double lon;
  LatLonPoint(this.lat,this.lon);
  LatLonPoint.fromMap(Map<String,dynamic> map){
    this.lat = map['latitude'];
    this.lon = map['longitude'];
  }

  Map<String, dynamic> toMap(){
    Map<String, dynamic> map = Map();
    map['longitude'] = this.lon;
    map['latitude'] = this.lat;
    return map;
  }
}