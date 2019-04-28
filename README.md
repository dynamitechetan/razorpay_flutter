# Flutter Razorpay Plugin
![image](https://user-images.githubusercontent.com/14369357/48184454-17c1bc80-e358-11e8-8821-269a30935a68.png)

A flutter plugin for razorpay integration for both android and ios.

#### If you use this library in your app, please let me know and I'll add it to the list.

### Installing
Add this in pubspec.yaml
```
  razorpay_plugin: ^0.2.9
```
### Using
```
import 'package:razorpay_plugin/razorpay_plugin.dart';
```

```
  startPayment() async {
    Map<String, dynamic> options = new Map();
    options.putIfAbsent("name", () => "Razorpay T-Shirt");
    options.putIfAbsent("image", () => "https://www.73lines.com/web/image/12427");
    options.putIfAbsent("description", () => "This is a real transaction");
    options.putIfAbsent("amount", () => "100");
    options.putIfAbsent("email", () => "test@testing.com");
    options.putIfAbsent("contact", () => "9988776655");
    //Must be a valid HTML color.
    options.putIfAbsent("theme", () => "#FF0000");
    //Notes -- OPTIONAL
    Map<String, String> notes = new Map();
    notes.putIfAbsent('key', () => "value");
    notes.putIfAbsent('randomInfo', () => "haha");
    options.putIfAbsent("notes", () => notes);
    options.putIfAbsent("api_key", () => "API_KEY_HERE");
    Map<dynamic,dynamic> paymentResponse = new Map();
    paymentResponse = await Razorpay.showPaymentForm(options);
    print("response $paymentResponse");

}
```  
Response : 
```
 {"code": 0, "message": "payment cancelled by user"}
```
or 
```
 {"code": 1, "message": "rpz_asdw23axd223s"}
```
If payment is sucessfull message will contain the payment_id from razorpay.

### Demo app
<table>
  <tr>
     <td>
       <img src = "https://user-images.githubusercontent.com/14369357/48185114-109bae00-e35a-11e8-9df8-2c8ccfcdbfc7.png" height="350">
    </td>
    <td>
      <img src = "https://user-images.githubusercontent.com/14369357/48185687-d3d0b680-e35b-11e8-849b-0899364df2f2.png" height="350">
      </td>
    </tr>
  </table>
  
  ## Apps using this library
- Worth It - Best Deals, Offers & Discounts [Android](https://play.google.com/store/apps/details?id=com.worthitproductions) |          [iOS](https://itunes.apple.com/us/app/worthit-deals-discounts/id1450975646?ls=1&mt=8)
