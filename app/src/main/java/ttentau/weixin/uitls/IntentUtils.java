package ttentau.weixin.uitls;

import android.app.Activity;
import android.content.Intent;

import ttentau.weixin.R;

/**
 * Created by ttent on 2017/3/14.
 */

public class IntentUtils {

    public static void startActivity(Activity activity, Class<?> tClass){
        Intent intent = new Intent();
        intent.setClass(activity,tClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
    }
    public static Intent getIntent(Activity activity, Class<?> tClass){
        Intent intent = new Intent(activity,tClass);
        return intent;
    }
    public static void startActivity(Activity activity,Intent intent){
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
    }
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
    }
    public static void startActivityForResult(Activity activity, Class<?> tClass, int requestCode){
        Intent intent = new Intent();
        intent.setClass(activity,tClass);
        activity.startActivityForResult(intent,requestCode);
        activity.overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
    }
}
