package com.blz.amapbus;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.blz.amapbus.bus.*;

import java.util.HashMap;
import java.util.Map;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * AmapbusPlugin
 */
public class AmapbusPlugin implements FlutterPlugin, ActivityAware, MethodCallHandler{
    private static final String PLUGIN_NAME = "Amap_bus_plugin";
    private Activity mActivity;
    private MethodChannel mMethodChannel;
    private MyBusStationSearch busStationSearch;
    private MyBusLineSearch busLineSearch;
    private MyBusTransferSearch busTransferSearch;

    //新版本注册插件
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        mMethodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "amapbus");
        mMethodChannel.setMethodCallHandler(this);
    }

    //旧版本的插件初始化
    public AmapbusPlugin initPlugin(MethodChannel methodChannel, Registrar registrar){
        mMethodChannel = methodChannel;
        mActivity = registrar.activity();
        busStationSearch = new MyBusStationSearch(mActivity,new BusStationQuery("河南城建学院","平顶山"));
        busStationSearch.initBusStationSearchListener();
        busLineSearch = new MyBusLineSearch(mActivity, new BusLineQuery("1路", BusLineQuery.SearchType.BY_LINE_NAME,"平顶山"));
        busLineSearch.initBusLineSearchListener();
        busTransferSearch = new MyBusTransferSearch(mActivity);
        busTransferSearch.initBusTransferSearchListener();
        return this;
    }
    // 旧版本注册插件
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "amapbus");
        AmapbusPlugin amapbusPlugin = new AmapbusPlugin();
        channel.setMethodCallHandler(amapbusPlugin.initPlugin(channel, registrar));
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull final Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("searchBusStation")) {
            String query = call.argument("query");
            String city = call.argument("city");
            Integer pageNumber = call.argument("pageNumber");
            Integer pageSize = call.argument("pageSize");
            BusStationQuery busStationQuery = new BusStationQuery(query,city);
            busStationQuery.setPageNumber(pageNumber);
            busStationQuery.setPageSize(pageSize);
            busStationSearch.setQuery(busStationQuery);
            busStationSearch.setResult(result);
            busStationSearch.beginSearch();
        } else if (call.method.equals("searchBusLine")) {
            String query = call.argument("query");
            Integer category = call.argument("category");
            String city = call.argument("city");
            Integer pageSize = call.argument("pageSize");
            Integer pageNumber = call.argument("pageNumber");
            BusLineQuery busLineQuery = new BusLineQuery(query,
                    category == 0 ? BusLineQuery.SearchType.BY_LINE_ID : BusLineQuery.SearchType.BY_LINE_NAME,
                    city);
            busLineQuery.setPageNumber(pageNumber);
            busLineQuery.setPageSize(pageSize);
            busLineSearch.setQuery(busLineQuery);
            busLineSearch.setResult(result);
            busLineSearch.beginSearch();
        } else if(call.method.equals("searchBusTransfer")){
            HashMap<String,HashMap<String,Double>> fromAndToMap = call.argument("fromAndTo");
            LatLonPoint from = new LatLonPoint(fromAndToMap.get("from").get("latitude"),fromAndToMap.get("from").get("longitude"));
            LatLonPoint to = new LatLonPoint(fromAndToMap.get("to").get("latitude"),fromAndToMap.get("to").get("longitude"));

            String city = call.argument("city");
            Integer model = call.argument("model");
            Integer nightFlag = call.argument("nightFlag");

            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from,to);
            RouteSearch.BusRouteQuery busRouteQuery = new RouteSearch.BusRouteQuery(fromAndTo,RouteSearch.BUS_LEASE_WALK,city,nightFlag);
            busTransferSearch.setBusRouteQuery(busRouteQuery);
            busTransferSearch.setResult(result);
            busTransferSearch.beginSearch();
        }else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        mMethodChannel.setMethodCallHandler(null);
        mMethodChannel = null;
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        mActivity = binding.getActivity();
        busStationSearch = new MyBusStationSearch(mActivity,new BusStationQuery("河南城建学院","平顶山"));
        busStationSearch.initBusStationSearchListener();
        busLineSearch = new MyBusLineSearch(mActivity, new BusLineQuery("1路", BusLineQuery.SearchType.BY_LINE_NAME,"平顶山"));
        busLineSearch.initBusLineSearchListener();
        busTransferSearch = new MyBusTransferSearch(mActivity);
        busTransferSearch.initBusTransferSearchListener();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {
        mActivity = null;
    }
}
