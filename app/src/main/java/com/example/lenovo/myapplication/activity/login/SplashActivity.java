package com.example.lenovo.myapplication.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.utils.IntentUtils;


/**
 * Created by ttent on 2017/2/9.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private Button btnregister;
    private Button btnlog;

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
