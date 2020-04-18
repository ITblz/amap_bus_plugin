package com.blz.amapbus.bus;

import android.content.Context;

import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.blz.amapbus.util.BusModelToJSON;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

/**
 * 公交线路查询
 */
public class MyBusLineSearch {

    private MethodChannel.Result result;
    private BusLineSearch busLineSearch;

    public MyBusLineSearch(Context context, BusLineQuery query){
        busLineSearch = new BusLineSearch(context,query);
    }

    public void initBusLineSearchListener(){
        busLineSearch.setOnBusLineSearchListener(new BusLineSearch.OnBusLineSearchListener() {
            @Override
            public void onBusLineSearched(BusLineResult busLineResult, int i) {
                Map<String,Object> map = new HashMap<>();
                map.put("code",i);
                try {
                    if(i == 1000){
                        map.put("result", BusModelToJSON.fromBusLineResultToJSON(busLineResult));
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
        });
    }

    public void setResult(MethodChannel.Result result){
        this.result = result;
    }

    public void  setQuery(BusLineQuery query){
        busLineSearch.setQuery(query);
    }

    public void beginSearch(){
        busLineSearch.searchBusLineAsyn();
    }

}
