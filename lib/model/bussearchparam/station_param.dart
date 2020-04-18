import 'query_param.dart';
///公交站点查询请求参数封装实体类
class BusStationQueryParam extends QueryParam{
  BusStationQueryParam(String query, String city, [int pageSize = 20, int pageNumber = 0])
      : super(query,city,pageSize,pageNumber);
}