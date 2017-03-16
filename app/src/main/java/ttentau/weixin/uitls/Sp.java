package ttentau.weixin.uitls;

import android.content.SharedPreferences;

/**
 * Created by ttent on 2017/2/10.
 */

public class Sp {
    public static void putNearPeople(String key ,boolean value){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("nearPeople", 0);
        sp.edit().putBoolean(key,value).commit();
    }
    public static boolean getNearPeople(String key ,boolean defvalue){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("nearPeople", 0);
        boolean result = sp.getBoolean(key, defvalue);
        return result;
    }
    public static void putUserInfo(String key,String value){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("userInfo", 0);
        sp.edit().putString(key,value).commit();
    }
    public static String getUserInfo(String key,String defvalue){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("userInfo", 0);
        String result = sp.getString(key, defvalue);
        return result;
    }
}
