package ttentau.weixin.ui.activity.found;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;

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
        ActionBar ab = getSupportActionBar();
        ab.setTitle("扫一扫");
        ab.setDisplayHomeAsUpEnabled(true);

        this.mScannerView = (ScannerView) findViewById(R.id.scan);
        int width = getWindowManager().getDefaultDisplay().getWidth();

        mScannerView.setLaserFrameSize(200, 200);// 扫描框大小
        mScannerView.setLaserFrameCornerLength(15);// 设置4角长度
        mScannerView.setLaserLineHeight(5);// 设置扫描线高度
        mScannerView.setLaserFrameCornerWidth(5);
        mScannerView.setLaserLineResId(R.drawable.wx_scan_line);

        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                URIParsedResult uriParsedResult = (URIParsedResult) parsedResult;
                String uri = uriParsedResult.getURI();
                Toast.makeText(ScannerActivity.this, uri, Toast.LENGTH_SHORT).show();
            }
    });
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
        finish();
        overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode) {
            case KeyEvent.KEYCODE_BACK :
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
        }
        return true;
    }
}
