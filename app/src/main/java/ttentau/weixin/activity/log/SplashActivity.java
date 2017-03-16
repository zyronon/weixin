package ttentau.weixin.activity.log;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.uitls.IntentUtils;

/**
 * Created by ttent on 2017/2/9.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private android.widget.Button btnregister;
    private android.widget.Button btnlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        this.btnlog = (Button) findViewById(R.id.btn_log);
        this.btnregister = (Button) findViewById(R.id.btn_register);
        btnlog.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_log:
                IntentUtils.startActivity(this,LogActivity.class);
                break;
            case R.id.btn_register:
                IntentUtils.startActivity(this,RegisterActivity.class);
                break;
        }
    }
}
