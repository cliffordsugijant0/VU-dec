package node8.valetuncle.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;

import node8.valetuncle.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicholas on 8/1/15.
 */
public class UserData {

    private static HashMap<String,String> settings_map;
    private static String device_id;
    private static String FBAccessToken;

    private static Context _ctx;

    public static void setContext(Context ctx)
    {
        _ctx = ctx;
        FBAccessToken = "";
        settings_map = new HashMap<String,String>();
        try{
            SharedPreferences sharedPref = ctx.getSharedPreferences(
                    ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            Map<String,?> allentries = sharedPref.getAll();
            for (Map.Entry<String, ?> entry : allentries.entrySet()) {
                settings_map.put(entry.getKey(), entry.getValue().toString());
            }
        }
        catch (NullPointerException e)
        {

        }
    }

    public static String getFBAccessToken()
    {
        return FBAccessToken;
    }

    public static void setFBAccessToken(String accessToken)
    {
        FBAccessToken = accessToken;
    }

    public static void setUserInfo(String object_id,String display_name,String user_name)
    {
        settings_map.put(_ctx.getString(R.string.user_id), object_id);
        settings_map.put(_ctx.getString(R.string.username), user_name);
        settings_map.put(_ctx.getString(R.string.displayName), display_name);
    }

    public static void setTransInfo(String promo_code,String remark,String pickup,String pickup_address,String actual_location)
    {
        settings_map.put(_ctx.getString(R.string.promo_code), promo_code);
        settings_map.put(_ctx.getString(R.string.remark), remark);
        settings_map.put(_ctx.getString(R.string.pickup), pickup);
        settings_map.put(_ctx.getString(R.string.pickup_address), pickup_address);
        settings_map.put(_ctx.getString(R.string.actual_location), actual_location);
    }


    public static void setPromoCode(String promo_code)
    {
        settings_map.put(_ctx.getString(R.string.promo_code), promo_code);
    }

    public static void setTransId(String transId)
    {
        settings_map.put(_ctx.getString(R.string.transId), transId);
    }
    public static void setAuthToken (String auth_token)
    {
        settings_map.put(_ctx.getString(R.string.auth_token),auth_token);
    }
    public static void setPassword (String password)
    {
        settings_map.put(_ctx.getString(R.string.password),password);
    }
    public static void setConfirmationCode(String confirmationCode)
    {
        settings_map.put(_ctx.getString(R.string.confirmation_code),confirmationCode);
    }
    public static void setSocialID(String social_id)
    {
        settings_map.put(_ctx.getString(R.string.social_id),social_id);
    }
    public static void setUserName(String user_name)
    {
        settings_map.put(_ctx.getString(R.string.username),user_name);
    }
    public static void setDisplayName(String display_name)
    {
        settings_map.put(_ctx.getString(R.string.displayName),display_name);
    }
    public static void setImgUrl(String img_url)
    {
        settings_map.put(_ctx.getString(R.string.displayName),img_url);
    }


    public static void setUserId(String userId)
    {
        settings_map.put(_ctx.getString(R.string.user_id),userId);
    }

    public static void setLoggedIn(String loggedIn)
    {
        settings_map.put(_ctx.getString(R.string.logged_in),loggedIn);
    }


    public static void setFeeMsg(String fee_msg)
    {
        settings_map.put(_ctx.getString(R.string.fee_msg),fee_msg);
    }

    public static void setFee (String fee)
    {
        settings_map.put(_ctx.getString(R.string.fee),fee);
    }

    public static String getTransId() {return settings_map.get(_ctx.getString(R.string.transId));}
    public static String getPromoCode() {return settings_map.get(_ctx.getString(R.string.promo_code));}
    public static String getRemark() {return settings_map.get(_ctx.getString(R.string.remark));}
    public static String getPickup() {return settings_map.get(_ctx.getString(R.string.pickup));}
    public static String getPickupAddress() {return settings_map.get(_ctx.getString(R.string.pickup_address));}
    public static String getActualLocation() {return settings_map.get(_ctx.getString(R.string.actual_location));}
    public static String getFeeMsg() {return settings_map.get(_ctx.getString(R.string.fee_msg));}
    public static String getFee() {return settings_map.get(_ctx.getString(R.string.fee));}
    public static String getLoggedIn() {return settings_map.get(_ctx.getString(R.string.logged_in));}
    public static String getPassword() {return settings_map.get(_ctx.getString(R.string.password));}
    public static String getObjectId() {return settings_map.get(_ctx.getString(R.string.user_id));}
    public static String getUsername() { return settings_map.get(_ctx.getString(R.string.username));}
    public static String getConfirmationCode() { return settings_map.get(_ctx.getString(R.string.confirmation_code));}
    public static String getSessionID() {return settings_map.get(_ctx.getString(R.string.session_id));}
    public static String getDisplayName(){return settings_map.get(_ctx.getString(R.string.displayName));}
    public static String getAuthToken(){return settings_map.get(_ctx.getString(R.string.auth_token));}
    public static String getSocialID(){return settings_map.get(_ctx.getString(R.string.social_id));}
    public static String getImgUrl(){return settings_map.get(_ctx.getString(R.string.imgurl));}

    public static String getDeviceID()
    {
        final TelephonyManager tm = (TelephonyManager) _ctx.getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        return  device_id;
    }
    public static String getDeviceName(){

        return getDisplayName()+"'s " + Build.BRAND+ " " +Build.MODEL;
    }

    public static void setOption(String key,String value)
    {
        settings_map.put(key,value);
    }

    public static String getOption(String key)
    {
        return settings_map.get(key);
    }

    public static Map<String,String> getSettingsMap(){
        return settings_map;
    }

    public static void flushSettings(){
        try{
            SharedPreferences sharedPref = _ctx.getSharedPreferences(
                    _ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPref.edit();

            for(Map.Entry<String, String> entry : settings_map.entrySet()) {
                edit.putString(entry.getKey(), entry.getValue());
            }

            edit.apply();
        }
        catch (ClassCastException e)
        {
        }
    }

    public static void ClearAllSettings(){
        settings_map.clear();
        SharedPreferences sharedPref = _ctx.getSharedPreferences(
                _ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();
        edit.apply();
    }
}
