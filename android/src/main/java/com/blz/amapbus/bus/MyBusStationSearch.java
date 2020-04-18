package com.blz.amapbus.bus;

import android.content.Context;

import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch;
import com.blz.amapbus.util.BusModelToJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

/**
 * 公交站点查询
 */
public class MyBusStationSearch {
    private MethodChannel.Result result;
    private BusStationSearch busStationSearch;
    public MyBusStationSearch(Context context, BusStationQuery query) {
        busStationSearch = new BusStationSearch(context,query);
    }

    /**
     * 初始化异步监听事件
     */
    public void initBusStationSearchListener(){
        busStationSearch.setOnBusStationSearchListener(new BusStationSearch.OnBusStationSearchListener() {
            @Override
            public void onBusStationSearched(BusStationResult busStationResult, int i) {
                {
                    Map<String,Object> map = new HashMap<>();
                    map.put("code",i);
                    try {
                        if(i == 1000){
                            map.put("result", BusModelToJSON.fromBusStationResultToJSON(busStationResult));
                        }
                    }catch (JSONException e){
                        map.put("result",null);
                        map.put("error",e.getMessage());
                    }finally {
                        if(result != null)
                            result.success(new JSONObject(map).toString());
                        result = null;
                    }
                }
            }
        });
    }

    /**
     * 设置MethodChannel.Result，以便于在异步监听中发送消息到flutter
     * @param result
     */
    public void setResult(MethodChannel.Result result) {
        this.result = result;
    }

    /**
     * 设置查询参数
     * @param query
     */
    public void setQuery(BusStationQuery query){
        busStationSearch.setQuery(query);
    }

    /**
     * 开启查询
     */
    public void beginSearch(){
        busStationSearch.searchBusStationAsyn();
    }
}
