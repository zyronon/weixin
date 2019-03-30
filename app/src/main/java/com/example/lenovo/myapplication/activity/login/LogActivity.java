package com.example.lenovo.myapplication.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;



/**
 * Created by ttent on 2017/2/9.
 */
public class LogActivity extends BaseActivity {

    private TextView tvnumber;
    private EditText photonumber;
    private TextView tvpassword;
    private EditText etpassword;
    private Button btnregister;
    private Button btnregisternot;
    private boolean etnumber_is_enpty = true;
    private boolean etpassword_is_enpty = true;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        if (user_name != null) {
            photonumber.setText(user_name);
        }
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        photonumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 != 0) {
                    etnumber_is_enpty = false;
                    if (!etnumber_is_enpty && !etpassword_is_enpty) {
                        btnregister.setVisibility(View.VISIBLE);
                        btnregisternot.setVisibility(View.GONE);
                    }
                } else {
                    etnumber_is_enpty = true;
                    btnregister.setVisibility(View.GONE);
                    btnregisternot.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 != 0) {
                    etpassword_is_enpty = false;
                    if (!etnumber_is_enpty && !etpassword_is_enpty) {
                        btnregister.setVisibility(View.VISIBLE);
                        btnregisternot.setVisibility(View.GONE);
                    }
                    if (!photonumber.getText().toString().isEmpty()) {
                        btnregister.setVisibility(View.VISIBLE);
                        btnregisternot.setVisibility(View.GONE);
                    }
                } else {
                    etpassword_is_enpty = true;
                    btnregister.setVisibility(View.GONE);
                    btnregisternot.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("使用手机号登陆");
        this.btnregisternot = (Button) findViewById(R.id.btn_register_not);
        this.btnregister = (Button) findViewById(R.id.btn_register);
        this.etpassword = (EditText) findViewById(R.id.et_password);
        this.tvpassword = (TextView) findViewById(R.id.tv_password);
        this.photonumber = (EditText) findViewById(R.id.et_photonumber);
        this.tvnumber = (TextView) findViewById(R.id.tv_number);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
        return true;
    }

    private void signIn() {
    }
}
