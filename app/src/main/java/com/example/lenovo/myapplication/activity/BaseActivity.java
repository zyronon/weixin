package com.example.lenovo.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.lenovo.myapplication.utils.ActivityCollector;


/**
 * Created by ttent on 2017/2/4.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    public void initAllMembersView(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
//            overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
        }
        return super.onKeyDown(keyCode, event);
    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
        return super.onOptionsItemSelected(item);
    }*/
}
