import 'query_param.dart';
///公交线路查询请求参数封装实体类
class BusLineQueryParam extends QueryParam{
  //搜索类型
  static const int CATEGORY_SearchType_BY_LINE_ID = 0;
  static const int CATEGORY_SearchType_BY_LINE_NAME = 1;
  int category;
  BusLineQueryParam(String query,this.category, String city, [int pageSize = 20, int pageNumber = 0])
      : super(query,city,pageSize,pageNumber);

  @override
  Map<String, dynamic> toMap() {
    Map<String, dynamic> map = super.toMap();
    map["category"] = this.category;
    return map;
  }
}