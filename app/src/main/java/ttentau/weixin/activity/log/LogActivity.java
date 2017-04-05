package ttentau.weixin.activity.log;

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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.activity.MainActivity;
import ttentau.weixin.bean.MyUser;
import ttentau.weixin.uitls.ActivityCollector;
import ttentau.weixin.uitls.UIUtils;


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
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在登陆，请稍后...");
        mDialog.show();
        String username = photonumber.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    mDialog.dismiss();
                    Intent intent = new Intent(LogActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                    ActivityCollector.finishAll();
                } else {
                    UIUtils.Toast(e);
                }
            }
        });
        /*EMClient.getInstance().login(username, password, new EMCallBack() {
			*//**
         * 登陆成功的回调
         *//*
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mDialog.dismiss();

						// 加载所有会话到内存
						EMClient.getInstance().chatManager().loadAllConversations();
						// 加载所有群组到内存，如果使用了群组的话
						// EMClient.getInstance().groupManager().loadAllGroups();
						String userInfo= photonumber.toString().trim()+"|未命名|空";
						Sp.putUserInfo("userinfo",userInfo);
						// 登录成功跳转界面
						Intent intent = new Intent(LogActivity.this, FriendActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
						ActivityCollector.finishAll();
					}
				});
			}

			*//**
         * 登陆错误的回调
         * @param i
         * @param s
         *//*
			@Override
			public void onError(final int i, final String s) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mDialog.dismiss();
						Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
						*//**
         * 关于错误码可以参考官方api详细说明
         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
         *//*
						switch (i) {
							// 网络异常 2
							case EMError.NETWORK_ERROR:
								Toast.makeText(LogActivity.this, "网络错误", Toast.LENGTH_LONG).show();
								break;
							// 无效的用户名 101
							case EMError.INVALID_USER_NAME:
								Toast.makeText(LogActivity.this, "无效的用户名 " , Toast.LENGTH_LONG).show();
								break;
							// 无效的密码 102
							case EMError.INVALID_PASSWORD:
								Toast.makeText(LogActivity.this, "无效的密码", Toast.LENGTH_LONG).show();
								break;
							// 用户认证失败，用户名或密码错误 202
							case EMError.USER_AUTHENTICATION_FAILED:
								Toast.makeText(LogActivity.this, "用户认证失败，用户名或密码错误", Toast.LENGTH_LONG).show();
								break;
							// 用户不存在 204
							case EMError.USER_NOT_FOUND:
								Toast.makeText(LogActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
								break;
							// 无法访问到服务器 300
							case EMError.SERVER_NOT_REACHABLE:
								Toast.makeText(LogActivity.this, "无法访问到服务器 ", Toast.LENGTH_LONG).show();
								break;
							// 等待服务器响应超时 301
							case EMError.SERVER_TIMEOUT:
								Toast.makeText(LogActivity.this, "等待服务器响应超时", Toast.LENGTH_LONG).show();
								break;
							// 服务器繁忙 302
							case EMError.SERVER_BUSY:
								Toast.makeText(LogActivity.this, "服务器繁忙", Toast.LENGTH_LONG).show();
								break;
							// 未知 Server 异常 303 一般断网会出现这个错误
							case EMError.SERVER_UNKNOWN_ERROR:
								Toast.makeText(LogActivity.this, "未知的服务器异常", Toast.LENGTH_LONG).show();
								break;
							default:
								Toast.makeText(LogActivity.this, "无法访问到服务器", Toast.LENGTH_LONG).show();
								break;
						}
					}
				});
			}
			@Override
			public void onProgress(int i, String s) {
			}
		});*/
    }
}
