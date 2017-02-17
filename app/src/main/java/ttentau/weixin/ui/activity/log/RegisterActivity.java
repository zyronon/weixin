package ttentau.weixin.ui.activity.log;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;

/**
 * Created by ttent on 2017/2/9.
 */
public class RegisterActivity extends BaseActivity {
    private android.widget.ImageView ivphoto;
    private android.widget.TextView tvname;
    private android.widget.TextView tvnumber;
    private android.widget.EditText etnumber;
    private android.widget.TextView tvpassword;
    private android.widget.EditText etpassword;
    private android.widget.Button btnregister;
    private android.widget.Button btnregisternot;
    private EditText etname;
    private boolean etname_is_enpty=true;
    private boolean etnumber_is_enpty=true;
    private boolean etpassword_is_enpty=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("填写手机号");
        this.etname = (EditText) findViewById(R.id.et_name);
        this.btnregisternot = (Button) findViewById(R.id.btn_register_not);
        this.btnregister = (Button) findViewById(R.id.btn_register);
        this.etpassword = (EditText) findViewById(R.id.et_password);
        this.tvpassword = (TextView) findViewById(R.id.tv_password);
        this.etnumber = (EditText) findViewById(R.id.et_number);
        this.tvnumber = (TextView) findViewById(R.id.tv_number);
        this.tvname = (TextView) findViewById(R.id.tv_name);
        this.ivphoto = (ImageView) findViewById(R.id.iv_photo);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signUp();
            }
        });


        etnumber.addTextChangedListener(new TextWatcher() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
        return true;
    }
    private void signUp() {
        // 注册是耗时过程，所以要显示一个dialog来提示下用户
         final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
        mDialog.setMessage("注册中，请稍后...");
        mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String username = etnumber.getText().toString().trim();
                    String password = etpassword.getText().toString().trim();
                    EMClient.getInstance().createAccount(username, password);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!RegisterActivity.this.isFinishing()) {
                                mDialog.dismiss();
                            }
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(RegisterActivity.this, LogActivity.class);
                    intent.putExtra("user_name",username);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                    finish();
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!RegisterActivity.this.isFinishing()) {
                                mDialog.dismiss();
                            }
                            /**
                             * 关于错误码可以参考官方api详细说明
                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                             */
                            int errorCode = e.getErrorCode();
                            String message = e.getMessage();
                            Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                            switch (errorCode) {
                                // 网络错误
                                case EMError.NETWORK_ERROR:
                                    Toast.makeText(RegisterActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                                    break;
                                // 用户已存在
                                case EMError.USER_ALREADY_EXIST:
                                    Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_LONG).show();
                                    break;
                                // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                case EMError.USER_ILLEGAL_ARGUMENT:
                                    Toast.makeText(RegisterActivity.this, "参数不合法", Toast.LENGTH_LONG).show();
                                    break;
                                // 服务器未知错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    Toast.makeText(RegisterActivity.this, "服务器未知错误", Toast.LENGTH_LONG).show();
                                    break;
                                case EMError.USER_REG_FAILED:
                                    Toast.makeText(RegisterActivity.this, "账户注册失败"  ,Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(RegisterActivity.this, "ml_sign_up_failed code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
