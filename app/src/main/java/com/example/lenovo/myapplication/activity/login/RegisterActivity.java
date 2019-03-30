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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.utils.IntentUtils;


/**
 * Created by ttent on 2017/2/9.
 */
public class RegisterActivity extends BaseActivity {
    private ImageView ivphoto;
    private EditText etphotonumber;
    private TextView tvpassword;
    private EditText etpassword;
    private Button btnregister;
    private Button btnregisternot;
    private EditText etname;
    private boolean etname_is_enpty=true;
    private boolean etnumber_is_enpty=true;
    private boolean etpassword_is_enpty=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
    }

    private void initData() {
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signUp();
            }
        });


        etphotonumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2!=0){
                    etnumber_is_enpty=false;
                    if (!etname_is_enpty&&!etnumber_is_enpty&&!etpassword_is_enpty){
                        btnregister.setVisibility(View.VISIBLE);
                        btnregisternot.setVisibility(View.GONE);
                    }
                }else {
                    etnumber_is_enpty=true;
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
                if (i2!=0){
                    etpassword_is_enpty=false;
                    if (!etname_is_enpty&&!etnumber_is_enpty&&!etpassword_is_enpty){
                        btnregister.setVisibility(View.VISIBLE);
                        btnregisternot.setVisibility(View.GONE);
                    }
                }
                else {
                    etpassword_is_enpty=true;
                    btnregister.setVisibility(View.GONE);
                    btnregisternot.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2!=0){
                    etname_is_enpty=false;
                    if (!etname_is_enpty&&!etnumber_is_enpty&&!etpassword_is_enpty){
                        btnregister.setVisibility(View.VISIBLE);
                        btnregisternot.setVisibility(View.GONE);
                    }
                }else {
                    etname_is_enpty=true;
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
        getSupportActionBar().setTitle("填写手机号");
        this.etname = (EditText) findViewById(R.id.et_name);
        this.btnregisternot = (Button) findViewById(R.id.btn_register_not);
        this.btnregister = (Button) findViewById(R.id.btn_register);
        this.etpassword = (EditText) findViewById(R.id.et_password);
        this.tvpassword = (TextView) findViewById(R.id.tv_password);
        this.etphotonumber = (EditText) findViewById(R.id.et_photonumber);
        this.ivphoto = (ImageView) findViewById(R.id.iv_photo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        IntentUtils.finish(this);
        return true;
    }
    private void signUp() {

    }
}
