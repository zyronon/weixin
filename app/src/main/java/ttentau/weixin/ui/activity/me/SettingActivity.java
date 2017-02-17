package ttentau.weixin.ui.activity.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;
import ttentau.weixin.uitls.ActivityCollector;

/**
 * Created by ttent on 2017/2/18.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

	private RelativeLayout mRl_out;
	private AlertDialog.Builder mMBuilder;
    private AlertDialog mDialog;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("设置");
		initView();
	}

	private void initView() {
		mRl_out = (RelativeLayout) findViewById(R.id.rl_out);
		mRl_out.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_out :
				View mView = View.inflate(this, R.layout.dialog_setting_out, null);
				mView.findViewById(R.id.rl_dialog_out_count).setOnClickListener(this);
				mView.findViewById(R.id.rl_dialog_out_app).setOnClickListener(this);
				mMBuilder = new AlertDialog.Builder(this);
				mMBuilder.setView(mView);
                mDialog = mMBuilder.show();
                break;
			case R.id.rl_dialog_out_count :
                mDialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("退出当前账号后不会删除任何历史数据，下次登陆依然可以使用本账号");
				builder.setPositiveButton("退出", new DialogInterface.OnClickListener() { // 设置确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // 关闭dialog
						EMClient.getInstance().logout(true);
						ActivityCollector.finishAll();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
				break;
			case R.id.rl_dialog_out_app :
                mDialog.dismiss();
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				builder1.setMessage("关闭后，你的朋友可能无法及时联系上你，还可能会影响到微信的使用体验");
				builder1.setPositiveButton("关闭微信", new DialogInterface.OnClickListener() { // 设置确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // 关闭dialog
						ActivityCollector.finishAll();
					}
				});
				builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder1.create().show();

				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
		}
		return true;
	}
}
