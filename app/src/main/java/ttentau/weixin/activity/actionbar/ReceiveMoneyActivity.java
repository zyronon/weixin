package ttentau.weixin.activity.actionbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/17.
 */
public class ReceiveMoneyActivity extends BaseActivity implements View.OnClickListener {

	private TextView tv_money_size;
	private TextView tv_setmoney;
	private TextView tv_savephoto;
	private TextView tv_explain;
	private boolean isHaveMoneySize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_receivemoney);
		initView();
	}

	private void initView() {
		findViewById(R.id.iv_pay_overflow).setOnClickListener(this);
		findViewById(R.id.iv_title_up).setOnClickListener(this);

		tv_money_size = (TextView) findViewById(R.id.tv_money_size);
		tv_explain = (TextView) findViewById(R.id.tv_explain);

		tv_setmoney = (TextView) findViewById(R.id.tv_setmoney);
		tv_savephoto = (TextView) findViewById(R.id.tv_savephoto);
		tv_setmoney.setOnClickListener(this);
		tv_savephoto.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_title_up:
				IntentUtils.finish(this);
				break;
			case R.id.iv_pay_overflow:
				View mView = UIUtils.inflate(R.layout.dialog_pay_iv_overflow);
				TextView tv1 = (TextView) mView.findViewById(R.id.tv1);
				TextView tv2 = (TextView) mView.findViewById(R.id.tv2);
				tv1.setText("开启收款提示音");
				tv1.setOnClickListener(this);
				tv2.setVisibility(View.GONE);
				showdialog(mView);
				break;
			case R.id.tv1:
				// TODO: 2017/3/17
				UIUtils.Toast("已开启");
				break;
			case R.id.tv_setmoney:
				if (!isHaveMoneySize){
					IntentUtils.startActivityForResult(this,SetMoneySize.class,666);
				}else {
					tv_setmoney.setText("设置金额");
					tv_explain.setVisibility(View.GONE);
					tv_money_size.setVisibility(View.GONE);
					isHaveMoneySize=false;
				}
				break;
			case R.id.tv_savephoto:
				UIUtils.Toast("图片已保存");
				//TODO
				break;
		}
	}
	private void showdialog(View mView){
		Dialog dialog = new Dialog(this,R.style.dialog_formbottom);
		dialog.setContentView(mView);
		Window dialogWindow = dialog.getWindow();
		//设置位置
		dialogWindow.setGravity(Gravity.BOTTOM);
		//设置dialog的宽高属性
		dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialogWindow.setWindowAnimations(R.style.alert_anim);
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==999){
			String moneysize = data.getStringExtra("moneysize");
			String explain = data.getStringExtra("explain");
			tv_money_size.setVisibility(View.VISIBLE);
			tv_money_size.setText(moneysize);
			if (!UIUtils.isEmpty(explain)){
				tv_explain.setVisibility(View.VISIBLE);
				tv_explain.setText(explain);
			}
			isHaveMoneySize=true;
			tv_setmoney.setText("清除金额");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK){
			IntentUtils.finish(this);
		}
		return super.onKeyDown(keyCode, event);
	}
}
