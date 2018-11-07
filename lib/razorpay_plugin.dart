import 'dart:async';

import 'package:flutter/services.dart';

class RazorpayPlugin {
  static const MethodChannel _channel =
      const MethodChannel('razorpay_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
