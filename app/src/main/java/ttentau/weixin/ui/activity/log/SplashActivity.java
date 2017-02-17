package ttentau.weixin.ui.activity.log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;

/**
 * Created by ttent on 2017/2/9.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private android.widget.Button btnregister;
    private android.widget.Button btnlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        this.btnlog = (Button) findViewById(R.id.btn_log);
        this.btnregister = (Button) findViewById(R.id.btn_register);
        btnlog.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent =null;
        switch (view.getId()){
            case R.id.btn_log:
                intent= new Intent(SplashActivity.this, LogActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.btn_register:
                intent= new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
        }
    }
}
