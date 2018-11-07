#import "RazorpayPlugin.h"
#import <razorpay_plugin/razorpay_plugin-Swift.h>

@implementation RazorpayPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftRazorpayPlugin registerWithRegistrar:registrar];
}
@end
