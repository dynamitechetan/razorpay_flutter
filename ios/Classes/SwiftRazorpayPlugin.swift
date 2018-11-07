import Flutter
import UIKit
import Razorpay
public class SwiftRazorpayPlugin: NSObject, FlutterPlugin, RazorpayPaymentCompletionProtocol  {

  var razorpay: Razorpay!
  var _result: FlutterResult!

  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "razorpay_plugin", binaryMessenger: registrar.messenger())
    let instance = SwiftRazorpayPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    _result=result
    if(call.method.elementsEqual("RazorPayForm"))
        {
            let argue = call.arguments as? NSDictionary
            let product_name = argue!["name"] as? String
            let product_des = argue!["description"] as? String
            let product_image = argue!["image"] as? String
            let product_amount = argue!["amount"] as? String
            let prefill_email = argue!["email"] as? String
            let prefill_contact = argue!["contact"] as? String
            let product_theme = argue!["theme"] as? String
            let API_KEY = argue!["api_key"] as? String
            razorpay = Razorpay.initWithKey(API_KEY!, andDelegate: self )
            showPaymentForm(name: product_name!, des:product_des!, image:product_image!, amount:product_amount!,email:prefill_email!, contact:prefill_contact!, theme:product_theme!);
        }
    }
    public func showPaymentForm(name:String,des:String,image:String,amount:String,email:String,contact:String,theme:String)
    {
        let params: [String:Any] =
            [
                "name": name,
                "description": des,
                "image": image,
                "amount": amount,
                "prefill":[
                    "contact": contact,
                    "email": email,
                ],
                "theme":[
                    "color":theme
                ],
            ]
        razorpay.open(params)
    }
    public func onPaymentError(_ code: Int32, description str: String)
    {
        var response = [String : String]()
        response["message"] = str
        response["code"] = "0"
        _result(response)
    }
    
    public func onPaymentSuccess(_ payment_id: String)
    {
        var response = [String : String]()
        response["message"] = payment_id
        response["code"] = "1"
        _result(response)
    }
}
