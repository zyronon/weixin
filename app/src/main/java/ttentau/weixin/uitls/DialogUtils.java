package ttentau.weixin.uitls;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import ttentau.weixin.R;


/**
 * Created by ttent on 2017/3/24.
 */

public class DialogUtils {
    public Dialog showFromBottom(Activity activity,View view, int width, int height){
        Dialog dialog = new Dialog(activity, R.style.dialog_formbottom);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        // 设置位置
        dialogWindow.setGravity(Gravity.BOTTOM);
        // 设置dialog的宽高属性
        dialogWindow.setLayout(width==0?LinearLayout.LayoutParams.MATCH_PARENT:height,height==0?LinearLayout.LayoutParams.MATCH_PARENT:height );
        dialogWindow.setWindowAnimations(R.style.alert_anim);
        dialog.show();
        return dialog;
    }
}
