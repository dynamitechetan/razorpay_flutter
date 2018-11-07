import 'dart:async';

import 'package:flutter/services.dart';

class Razorpay {
  static const MethodChannel _channel =
      const MethodChannel('razorpay_plugin');

  static Future<Map<dynamic,dynamic>> showPaymentForm(Map<dynamic,dynamic> map) async {
    Map<dynamic,dynamic> data=new Map();
    data=await _channel.invokeMethod('RazorPayForm',map);
    return data;
  }
}
