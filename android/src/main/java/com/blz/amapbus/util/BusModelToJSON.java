package com.blz.amapbus.util;

import android.util.Log;

import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteBusWalkItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 高德地图SDK提供的Bus相关实体类转为JSON对象
 */
public class BusModelToJSON {

    /**
     * 公交站点查询结果集BusStationResult中相关数据的提取
     * @param busStationResult
     * @return
     * @throws JSONException
     */
    public static JSONObject fromBusStationResultToJSON(BusStationResult busStationResult) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("pageCount",busStationResult.getPageCount());
        //公交车站信息列表提取
        List<BusStationItem> busStations = busStationResult.getBusStations();
        if(busStations != null){
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < busStations.size(); i++) {
                jsonArray.put(fromBusStationItemToJSON(busStations.get(i)));
            }
            result.put("busStations",jsonArray);
        }else {
            result.put("busStations",null);
        }
        //搜索建议关键字
        final List<String> keywords = busStationResult.getSearchSuggestionKeywords();
        result.put("searchSuggestionKeywords",keywords);

        //搜索城市建议
        final List<SuggestionCity> searchSuggestionCities = busStationResult.getSearchSuggestionCities();
        if (searchSuggestionCities == null){
            result.put("searchSuggestionCities",null);
        }else {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < searchSuggestionCities.size(); i++) {
                jsonArray.put(fromSuggestionCityToJSON(searchSuggestionCities.get(i)));
            }
            result.put("searchSuggestionCities",jsonArray);
        }

        return result;
    }

    /**
     * 公交线路查询结果集BusStationResult中相关数据的提取
     * @param busLineResult
     * @return
     * @throws JSONException
     */
    public static JSONObject fromBusLineResultToJSON(BusLineResult busLineResult) throws JSONException{
        JSONObject result = new JSONObject();
        result.put("pageCount",busLineResult.getPageCount());

        List<BusLineItem> busLines = busLineResult.getBusLines();
        if (busLines != null){
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < busLines.size(); i++) {
                jsonArray.put(fromBusLineItemToJSON(busLines.get(i)));
            }
            result.put("busLines",jsonArray);
        }else {
            result.put("busLines",null);
        }
        //搜索建议关键字
        final List<String> keywords = busLineResult.getSearchSuggestionKeywords();
        result.put("searchSuggestionKeywords",keywords);

        //搜索城市建议
        final List<SuggestionCity> searchSuggestionCities = busLineResult.getSearchSuggestionCities();
        if (searchSuggestionCities == null){
            result.put("searchSuggestionCities",null);
        }else {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < searchSuggestionCities.size(); i++) {
                jsonArray.put(fromSuggestionCityToJSON(searchSuggestionCities.get(i)));
            }
            result.put("searchSuggestionCities",jsonArray);
        }
        return result;
    }

    /**
     * 公交换乘规划
     * BusRouteResult实体类转JSON对象
     * @param busRouteResult
     * @return
     * @throws JSONException
     */
    public static JSONObject fromBusRouteResultToJSON(BusRouteResult busRouteResult) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taxiCost",busRouteResult.getTaxiCost());
        jsonObject.put("startPos",fromLatLonPointToJSON(busRouteResult.getStartPos()));
        jsonObject.put("targetPos",fromLatLonPointToJSON(busRouteResult.getTargetPos()));
        List<BusPath> paths = busRouteResult.getPaths();
        if(paths == null){
            jsonObject.put("paths",null);
        }else {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < paths.size(); i++) {
                jsonArray.put(fromBusPathToJSON(paths.get(i)));
            }
            jsonObject.put("paths", jsonArray);
        }

        return jsonObject;
    }
    /**
     * 将BusStationItem实体类转为JSON字符串
     * @param busStationItem
     * @return
     * @throws JSONException
     */
    public static JSONObject fromBusStationItemToJSON(BusStationItem busStationItem) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("busStationId",busStationItem.getBusStationId());
        jsonObject.put("busStationName",busStationItem.getBusStationName());
        jsonObject.put("cityCode",busStationItem.getCityCode());
        jsonObject.put("adCode",busStationItem.getAdCode());
        LatLonPoint latLonPoint = busStationItem.getLatLonPoint();
        jsonObject.put("latLonPoint",fromLatLonPointToJSON(latLonPoint));

        List<BusLineItem> busLineItems = busStationItem.getBusLineItems();
        if(busLineItems == null){
            jsonObject.put("busLineItems",null);
        }else {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < busLineItems.size(); i++) {
                jsonArray.put(fromBusLineItemToJSON(busLineItems.get(i)));
            }
            jsonObject.put("busLineItems",jsonArray);
        }
        return jsonObject;
    }

    /**
     * BusLineItem实体类转JSON对象
     * @param busLineItem
     * @return
     */
    public static JSONObject fromBusLineItemToJSON(BusLineItem busLineItem) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("busLineId",busLineItem.getBusLineId());
        jsonObject.put("busLineName",busLineItem.getBusLineName());
        jsonObject.put("busLineType",busLineItem.getBusLineType());
        jsonObject.put("describeContents",busLineItem.describeContents());
        jsonObject.put("basicPrice",busLineItem.getBasicPrice());
        JSONArray jsonArray = new JSONArray();
        List<LatLonPoint> bounds = busLineItem.getBounds();
        if (bounds == null){
            jsonObject.put("bounds", null);
        }else {
            for (int i = 0; i < bounds.size(); i++) {
                jsonArray.put(fromLatLonPointToJSON(bounds.get(i)));
            }
            jsonObject.put("bounds", jsonArray);
        }

        List<LatLonPoint> directionsCoordinates = busLineItem.getDirectionsCoordinates();
        if(directionsCoordinates == null){
            jsonObject.put("directionsCoordinates", null);
        }else {
            jsonArray = null;
            jsonArray = new JSONArray();
            for (int i = 0; i < directionsCoordinates.size(); i++) {
                jsonArray.put(fromLatLonPointToJSON(directionsCoordinates.get(i)));
            }
            jsonObject.put("directionsCoordinates", jsonArray);
        }

        jsonObject.put("busCompany",busLineItem.getBusCompany());
        jsonObject.put("cityCode",busLineItem.getCityCode());
        jsonObject.put("distance",busLineItem.getDistance());
        jsonObject.put("firstBusTime",busLineItem.getFirstBusTime());
        jsonObject.put("lastBusTime",busLineItem.getLastBusTime());
        jsonObject.put("originatingStation",busLineItem.getOriginatingStation());
        jsonObject.put("terminalStation",busLineItem.getTerminalStation());
        jsonObject.put("totalPrice",busLineItem.getTotalPrice());

        List<BusStationItem> busStations = busLineItem.getBusStations();
        if(busStations == null){
            jsonObject.put("busStations", null);
        }else {
            jsonArray = null;
            jsonArray = new JSONArray();
            for (int i = 0; i < busStations.size(); i++) {
                jsonArray.put(fromBusStationItemToJSON(busStations.get(i)));
            }
            jsonObject.put("busStations", jsonArray);
        }
        return jsonObject;
    }

    /**
     * LatLonPoint实体类转为JSON对象
     * @param latLonPoint
     * @return
     * @throws JSONException
     */
    public static JSONObject fromLatLonPointToJSON(LatLonPoint latLonPoint) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        double latitude = latLonPoint.getLatitude();
        double longitude = latLonPoint.getLongitude();
        jsonObject.put("latitude",latitude);
        jsonObject.put("longitude", longitude);
        return jsonObject;
    }

    /**
     * SuggestionCity实体类转JSON对象
     * @param suggestionCity
     * @return
     * @throws JSONException
     */
    public static JSONObject fromSuggestionCityToJSON(SuggestionCity suggestionCity) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cityName",suggestionCity.getCityName());
        jsonObject.put("cityCode",suggestionCity.getCityCode());
        jsonObject.put("suggestionNum",suggestionCity.getSuggestionNum());
        return jsonObject;
    }

    /**
     * BusPath转JSON对象
     * @param busPath 定义了公交换乘路径规划的一个方案。
     * @return
     * @throws JSONException
     */
    public static JSONObject fromBusPathToJSON(BusPath busPath) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("busDistance",busPath.getBusDistance());
        jsonObject.put("cost",busPath.getCost());
        jsonObject.put("walkDistance",busPath.getWalkDistance());
        jsonObject.put("distance",busPath.getDistance());
        jsonObject.put("duration",busPath.getDuration());
        List<BusStep> steps = busPath.getSteps();
        if(steps == null){
            jsonObject.put("steps",null);
        }else {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < steps.size(); i++) {
                jsonArray.put(fromBusStepToJSON(steps.get(i)));
            }
            jsonObject.put("steps",jsonArray);
        }
        return jsonObject;
    }

    /**
     * BusStep实体类转JSON对象
     * 定义了公交路径规划的一个路段。 路段最多包含一段步行信息和公交导航信息。可能出现路段中没有步行信息的情况。
     * @param busStep
     * @return
     * @throws JSONException
     */
    public static JSONObject fromBusStepToJSON(BusStep busStep) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        RouteBusWalkItem walk = busStep.getWalk();
        jsonObject.put("walk", walk != null ? fromRouteBusWalkItemToJSON(walk) : null);
        List<RouteBusLineItem> busLines = busStep.getBusLines();
        if(busLines == null) {
            jsonObject.put("busLines", null);
        }else {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i <busLines.size() ; i++) {
                jsonArray.put(fromBusLineItemToJSON(busLines.get(i)));
            }
            jsonObject.put("busLines", jsonArray);
        }
        return jsonObject;
    }

    /**
     * RouteBusWalkItem实体类转json对象
     * @param routeBusWalkItem 定义了公交换乘路径规划的一个换乘段的步行信息。
     * @return
     */
    public static JSONObject fromRouteBusWalkItemToJSON(RouteBusWalkItem routeBusWalkItem) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("origin",fromLatLonPointToJSON(routeBusWalkItem.getOrigin()));
        jsonObject.put("destination",fromLatLonPointToJSON(routeBusWalkItem.getDestination()));
        return jsonObject;
    }
}
