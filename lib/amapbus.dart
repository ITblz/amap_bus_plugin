import 'dart:async';

import 'package:flutter/services.dart';

import 'model/bussearchparam/line_param.dart';
import 'model/bussearchparam/station_param.dart';
import 'model/bussearchparam/transfer_param.dart';

class Amapbus {
  static const MethodChannel _channel =
      const MethodChannel('amapbus');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> searchBusStation(BusStationQueryParam bsq) async {
    final String s = await _channel.invokeMethod("searchBusStation",bsq.toMap());
    return s;
  }

  static Future<String> searchBusLine(BusLineQueryParam blq) async {
    final String s = await _channel.invokeMethod("searchBusLine",blq.toMap());
    return s;
  }

  static Future<String> searchBusTransfer(BusTransferQueryParam btq) async {
    final String s = await _channel.invokeMethod("searchBusTransfer",btq.toMap());
    return s;
  }
}
