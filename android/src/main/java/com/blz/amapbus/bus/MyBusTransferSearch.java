package com.blz.amapbus.bus;

import android.content.Context;

import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.blz.amapbus.util.BusModelToJSON;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MethodChannel;

/**
 * 公交换乘查询
 */
public class MyBusTransferSearch {
    private MethodChannel.Result result;
    private RouteSearch routeSearch;
    private RouteSearch.BusRouteQuery busRouteQuery;
    public MyBusTransferSearch(Context context) {
        routeSearch = new RouteSearch(context);
    }

    public void initBusTransferSearchListener(){
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                Map<String,Object> map = new HashMap<>();
                map.put("code",i);
                try {
                    if(i == 1000){
                        map.put("result", BusModelToJSON.fromBusRouteResultToJSON(busRouteResult));
                    }
                }catch (org.json.JSONException e){
                    map.put("result",null);
                    map.put("error",e.getMessage());
                }finally {
                    if(result != null)
                        result.success(new JSONObject(map).toString());
                    result = null;
                }
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
    }

    public void setResult(MethodChannel.Result result) {
        this.result = result;
    }

    public void setBusRouteQuery(RouteSearch.BusRouteQuery query){
        this.busRouteQuery = query;
    }

    public void beginSearch() {
        routeSearch.calculateBusRouteAsyn(busRouteQuery);
    }
}
