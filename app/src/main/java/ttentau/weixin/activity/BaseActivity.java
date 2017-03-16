package ttentau.weixin.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import ttentau.weixin.uitls.ActivityCollector;

/**
 * Created by ttent on 2017/2/4.
 */
public class BaseActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
