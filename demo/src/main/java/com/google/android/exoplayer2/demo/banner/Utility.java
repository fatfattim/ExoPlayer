package com.google.android.exoplayer2.demo.banner;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utility {

    public static final String TAG = Utility.class.getSimpleName();

    public static Point getScreenSize(Activity activity) {
        Point displaySize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
        return displaySize;
    }

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    public static boolean isEmpty(TextView textView) {
        return textView.getText().toString().isEmpty();
    }

    public static float getSp(Context context, int res ) {
        return (context.getResources().getDimension(res) / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int getPixel(Context context, int res ) {
        return context.getResources().getDimensionPixelSize(res);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String appendSeeMoreMark(String text) {
        return text + " >";
    }

    public static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }

    public static String getServiceProviderName(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getSimOperatorName();
    }

    public static void setButtonMargin(View view, int size, int rightMargin, int padding) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
        lp.setMargins(0, 0, rightMargin, 0);
        view.setPadding(padding, padding, padding, padding);
        view.setLayoutParams(lp);
    }

    public static String getMiddleUnicode() {
        return " \u00B7 ";
    }

    public static void makeMarquee(TextView textView) {
        textView.setSelected(true);
    }

    public static String convertToDate(long seconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(seconds * 1000);
        return formatter.format(calendar.getTime());
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {

        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void listTelephonyManagerInfo(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        Log.d(TAG, "deviceId: " + telephonyManager.getDeviceId());
        Log.d(TAG, "deviceSoftwareVersion: " + telephonyManager.getDeviceSoftwareVersion());
        Log.d(TAG, "line1Number: " + telephonyManager.getLine1Number());
        Log.d(TAG, "networkCountryIso: " + telephonyManager.getNetworkCountryIso());
        Log.d(TAG, "simOperator: " + telephonyManager.getSimOperator());
        Log.d(TAG, "simOperatorName: " + telephonyManager.getSimOperatorName());
        Log.d(TAG, "simSerialNumber: " + telephonyManager.getSimSerialNumber());
        Log.d(TAG, "subscriberId: " + telephonyManager.getSubscriberId());
        Log.d(TAG, "getCellLocation: " + telephonyManager.getCellLocation().toString());
    }

    public static float getFloat(Context context, int res) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(res, typedValue, true);
        return typedValue.getFloat();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) ((float) px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToSp(int px) {
        return (int) ((float) px / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

}