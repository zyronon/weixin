package ttentau.weixin.uitls;

import android.content.SharedPreferences;

/**
 * Created by ttent on 2017/2/10.
 */

public class Sp {
    public static void putMessageInfo(String key ,String content){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("message_info", 0);
        sp.edit().putString(key,content).commit();
    }
    public static String getMessageInfo(String key ,String content){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("message_info", 0);
        String result = sp.getString(key, content);
        return result;
    }
    public static void putMessageList(String key ,String content){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("message_list", 0);
        sp.edit().putString(key,content).commit();
    }
    public static String getMessageList(String key ,String content){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("message_list", 0);
        String result = sp.getString(key, content);
        return result;
    }
    public static void putNearPeople(String key ,boolean value){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("nearPeople", 0);
        sp.edit().putBoolean(key,value).commit();
    }
    public static boolean getNearPeople(String key ,boolean defvalue){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("nearPeople", 0);
        boolean result = sp.getBoolean(key, defvalue);
        return result;
    }
}
