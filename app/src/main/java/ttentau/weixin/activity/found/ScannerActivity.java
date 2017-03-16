package ttentau.weixin.activity.found;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.activity.WebActivity;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/11.
 */

public class ScannerActivity extends BaseActivity {
	private com.mylhyl.zxing.scanner.ScannerView mScannerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		initActionBar();

		initData();
	}

	private void initData() {
		this.mScannerView = (ScannerView) findViewById(R.id.scan);
		int width = getWindowManager().getDefaultDisplay().getWidth();

		mScannerView.setLaserFrameSize(200, 200);// 扫描框大小
		mScannerView.setLaserFrameCornerLength(15);// 设置4角长度
		mScannerView.setLaserLineHeight(5);// 设置扫描线高度
		mScannerView.setLaserFrameCornerWidth(5);
		mScannerView.setLaserLineResId(R.drawable.photo_scan_line);

		mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
			@Override
			public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
				URIParsedResult uriParsedResult = (URIParsedResult) parsedResult;
				String uri = uriParsedResult.getURI();
				Intent intent = new Intent(UIUtils.getContext(), WebActivity.class);
				String[] shoppingValue = {uri, "扫描"};
				intent.putExtra("normal", shoppingValue);
				IntentUtils.startActivity(ScannerActivity.this, intent);
			}
		});
	}

	private void initActionBar() {
		ActionBar ab = getSupportActionBar();
		ab.setTitle("扫一扫");
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onResume() {
		mScannerView.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mScannerView.onPause();
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		IntentUtils.finish(this);
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				IntentUtils.finish(this);
				break;
		}
		return true;
	}
}
