package ttentau.weixin.ui.activity.found;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;

/**
 * Created by ttent on 2017/2/13.
 */
public class PiaoLiuPingActivity extends BaseActivity{

    private Handler hand=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piaoliuping);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("漂流瓶");
        ab.hide();
    }

    private void setFullScreen(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    private void quitFullScreen(){
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
            case 789:
                startActivity(new Intent(PiaoLiuPingActivity.this,YaoYiYaoSetttingActivity.class));
                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,789,0,"设置").setEnabled(true).setIcon(R.drawable.icon_setting).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode) {
            case KeyEvent.KEYCODE_BACK :
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
        }
        return true;
    }
}
