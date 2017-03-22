package com.tradeitsignals.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.logging.TILogger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Kostyantin on 8/7/2015.
 */
public class CommonUtils {

    public static String createInstallationId(Context context) {
        String installationId = android.provider.Settings.System.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID
        );
        if (CommonUtils.isEmpty(installationId)) {
            installationId = UUID.randomUUID().toString();
        }
        return installationId.replace("-", "");
    }

    public static String getVersionName() {
        String versionName;
        try {
            Context context = SignalsApplication.getAppContext();
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            TILogger.getLog().e("Couldn't get version name from package manager");
            versionName = "";
        }
        return versionName;
    }

    public static boolean isTrialPeriodExpired() {
        // TODO: change back to a day.
        Date dayAfterFirstTimeAppOpened = new Date(PrefUtils.getFirstAppOpenTimestamp() + (24 * 60 * 60 * 1000));
        Date now = new Date();

        TILogger.getLog().d("Checking Trial... now: " + now + ", dayAfterFirstTimeAppOpened: " + dayAfterFirstTimeAppOpened);

        if(now.after(dayAfterFirstTimeAppOpened)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) SignalsApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni != null && ni.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String toMD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes(Charset.forName("UTF-8")));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static AlertDialog getConnectToNetworkDialog(Context context) {
        return getConnectToNetworkDialog(context, null);
    }

    public static AlertDialog getConnectToNetworkDialog(final Context context, DialogInterface.OnCancelListener cancelListener) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);

        builder.setCancelable(true)
                .setTitle(R.string.no_internet_conn)
                .setMessage(R.string.no_internet_msg)
                .setPositiveButton(R.string.use_data_package, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                })
                .setNeutralButton(R.string.use_wifi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        if(cancelListener != null) {
            builder.setOnCancelListener(cancelListener);
        }

        return builder.create();
    }

    public static String getUserPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                SignalsApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        String number = mTelephonyMgr.getLine1Number();
        if(number == null) {
            number = "";
        }
        return number;
    }

    public static String getMy10DigitPhoneNumber(){
        String s = getUserPhoneNumber();
        return s != null && s.length() > 2 ? s.substring(2) : "";
    }

    /**
     * Caution!! can return null.
     * @return String of a 2 letter country iso code of the user.
     */
    public static String getUserCountryCode() {
        try {
            final TelephonyManager tm = (TelephonyManager) SignalsApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toUpperCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toUpperCase(Locale.US);
                }
            }
        } catch (Exception e) {
            TILogger.getLog().e("CommonUtils", "Error getting users country code from CommonUtils.getUserCountryCode()", e);
        }
        return null;
    }

    /**
     * Caution!! can return null.
     * @return String of users country name.
     */
    public static String getUserCountryName() {
        String countryCode = getUserCountryCode();
        if(countryCode != null) {
            return StringUtils.firstLetterToUpper(new Locale("", countryCode).getDisplayCountry());
        }
        return "";
    }

    public static String encodeBase64(String text) {
        return encodeBase64(text, false);
    }

    public static String encodeBase64(String text, boolean includeLineBreak) {
        byte[] data = null;
        try {
            data = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            TILogger.getLog().e("CommonUtils", "Failure encoding " + text + " to base64 in CommonUtils.encodeToBase64()", e);
        }
        int flag = includeLineBreak ? Base64.DEFAULT : Base64.NO_WRAP;
        return Base64.encodeToString(data, flag);
    }

    public static String decodeBase64(String base64) {
        return decodeBase64(base64, false);
    }

    public static String decodeBase64(String base64, boolean includeLineBreak) {
        int flag = includeLineBreak ? Base64.DEFAULT : Base64.NO_WRAP;
        byte[] data = Base64.decode(base64, flag);
        String text = null;
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            TILogger.getLog().e("CommonUtils", "Failure decoding base64 from " + base64 + " in CommonUtils.decodeBase64()", e);
        }
        return text;
    }

    public static boolean doesFileExist(String fileName) {
        File file = SignalsApplication.getAppContext().getFileStreamPath(fileName);
        return file.exists();
    }

    public final static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static String getIPAddress() {
        try {
            //Enumerate all the network interfaces
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                // Make a loop on the number of IP addresses related to each Network Interface
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //Check if the IP address is not a loopback address, in that case it is
                    //the IP address of your mobile device
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipAddress = inetAddress.getHostAddress();
                        if(ipAddress.contains("%")) {
                            return ipAddress.split("%")[0];
                        }
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean notEmpty(String string) {
        return string != null && !string.trim().equals("");
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().equals("");
    }

    public static boolean isValidEmail(String email) {
        return notEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
