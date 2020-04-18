///公交请求查询参数封装的基类
class QueryParam{
  String query;
  String city;
  int pageSize;
  int pageNumber;

  QueryParam(String query,String city, [int pageSize = 20, int pageNumber = 0]){
    this.query = query;
    this.city = city;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
  }

  Map<String,dynamic> toMap(){
    Map<String,dynamic> map = new Map<String,dynamic>();
    map["query"] = this.query;
    map["city"] = this.city;
    map["pageSize"] = this.pageSize;
    map["pageNumber"] = this.pageNumber;
    return map;
  }
}