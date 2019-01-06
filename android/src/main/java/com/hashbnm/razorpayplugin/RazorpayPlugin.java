package com.hashbnm.razorpayplugin;

import android.app.Activity;
import android.content.Intent;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
/**
 * RazorpayPlugin
 */
public class RazorpayPlugin implements MethodCallHandler, PluginRegistry.ActivityResultListener {
    private final MethodChannel channel;
    private Activity activity;
    private Result pendingResult;
    private Map<String, Object> arguments;

    public RazorpayPlugin(Activity activity, MethodChannel channel) {
        this.activity = activity;
        this.channel = channel;
        this.channel.setMethodCallHandler(this);
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "razorpay_plugin");
        RazorpayPlugin instance = new RazorpayPlugin(registrar.activity(), channel);
        channel.setMethodCallHandler(instance);
        registrar.addActivityResultListener(instance);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        pendingResult = result;
        if (call.method.equals("RazorPayForm")) {
            arguments = (Map<String, Object>) call.arguments;
            Intent razorpayIntent = new Intent(activity, RazorpayActivity.class);
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_PRODUCT_NAME, (String) arguments.get("name"));
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_PRODUCT_IMAGE, (String) arguments.get("image"));
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_PRODUCT_DESCRIPTION, (String) arguments.get("description"));
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_PRODUCT_AMOUNT, (String) arguments.get("amount"));
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_PREFILL_EMAIL, (String) arguments.get("email"));
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_THEME, (String) arguments.get("theme"));
            razorpayIntent.putExtra(RazorpayActivity.EXTRA_NOTES, (Serializable) arguments.get("notes"));

            razorpayIntent.putExtra(RazorpayActivity.EXTRA_PREFILL_CONTACT, (String) arguments.get("contact"));
            razorpayIntent.putExtra(RazorpayActivity.RAZORPAY_KEY, (String) arguments.get("api_key"));
            activity.startActivityForResult(razorpayIntent, 8888);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 8888) {
            if (resultCode == Activity.RESULT_OK) {
                HashMap<String, String> data = new HashMap<>();
                String response = intent.getStringExtra("payment_id");
                data.put("code", "1");
                data.put("message", response);
                pendingResult.success(data);
            } else {
                HashMap<String, String> data = new HashMap<>();
                String response = intent.getStringExtra("payment_id");
                data.put("code", "0");
                data.put("message", response);
                pendingResult.success(data);
            }
            pendingResult = null;
            arguments = null;
            return true;
        }
        return false;
    }
}
