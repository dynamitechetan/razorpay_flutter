package com.hashbnm.razorpayplugin;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RazorpayActivity extends Activity implements PaymentResultListener {
    private static final String TAG = RazorpayActivity.class.getSimpleName();
    public static String EXTRA_PRODUCT_NAME = "name";
    public static String EXTRA_PRODUCT_IMAGE = "image";
    public static String EXTRA_PRODUCT_DESCRIPTION = "description";
    public static String EXTRA_PRODUCT_AMOUNT = "amount";
    public static String EXTRA_PREFILL_EMAIL = "email";
    public static String EXTRA_NOTES = "notes";
    public static String EXTRA_THEME = "theme";
    public static String EXTRA_PREFILL_CONTACT = "contact";
    public static String PAYMENT_ID = "payment_id";
    public static String RAZORPAY_KEY = "api_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        Intent intent = getIntent();
        Checkout.preload(getApplicationContext());
        startPayment(intent);
    }

    public void startPayment(Intent intent) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID(intent.getStringExtra(RAZORPAY_KEY));

        try {
            JSONObject options = new JSONObject();
            options.put(EXTRA_PRODUCT_NAME, intent.getStringExtra(EXTRA_PRODUCT_NAME));
            options.put(EXTRA_PRODUCT_DESCRIPTION, intent.getStringExtra(EXTRA_PRODUCT_DESCRIPTION));
            //You can omit the image option to fetch the image from dashboard
            options.put(EXTRA_PRODUCT_IMAGE, intent.getStringExtra(EXTRA_PRODUCT_IMAGE));
            options.put("currency", "INR");
            options.put(EXTRA_PRODUCT_AMOUNT, intent.getStringExtra(EXTRA_PRODUCT_AMOUNT));
            JSONObject color = new JSONObject();
            color.put("color", intent.getStringExtra(EXTRA_THEME));
            options.put("theme",color);
            JSONObject preFill = new JSONObject();
            preFill.put(EXTRA_PREFILL_EMAIL, intent.getStringExtra(EXTRA_PREFILL_EMAIL));
            preFill.put(EXTRA_PREFILL_CONTACT, intent.getStringExtra(EXTRA_PREFILL_CONTACT));
            options.put("prefill", preFill);

            if (intent.getSerializableExtra(EXTRA_NOTES) != null) {
                JSONObject notes = new JSONObject();
                Serializable noteObj = intent.getSerializableExtra(EXTRA_NOTES);
                HashMap<String, String> noteMap= (HashMap<String, String>) noteObj;
                for (Map.Entry<String, String> entry : noteMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    notes.put(key, value);
                }
                options.put(EXTRA_NOTES, notes);
            }

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Intent data = new Intent();
            data.putExtra(PAYMENT_ID, razorpayPaymentID);
            setResult(Activity.RESULT_OK, data);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Intent data = new Intent();
            data.putExtra(PAYMENT_ID, response);
            setResult(Activity.RESULT_CANCELED, data);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
