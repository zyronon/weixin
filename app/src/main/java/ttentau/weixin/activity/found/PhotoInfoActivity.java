package ttentau.weixin.activity.found;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.adapter.viewpager.PhotoDetailsAdapter;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.Constant;
import ttentau.weixin.uitls.SystemBarTintManager;
import ttentau.weixin.uitls.UIUtils;


/**
 * 这是在图片列表那里点击略缩图之后，跳到这个acticity放大图片
 * Created by ttent on 2017/2/25.
 */

public class PhotoInfoActivity extends BaseActivity implements View.OnClickListener {

	private List<ImageModel> mAllImages;
	private List<ImageModel> selectedImages;
	private Button mBtn_ok;
	private int mPosition1;
    private ArrayList<String> mAddfriendPath;
	private RelativeLayout mRl_customActionbar;
	private RelativeLayout rl_container_bottom;
	private ImageView mIv_title_up;
	private TextView mTv_title_count;
	private boolean rlisshow;
	private SystemBarTintManager tintManager;
	private View content;
	private RelativeLayout.LayoutParams mLp;
	private CheckBox mCb;
	private ViewPager mVp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoinfo);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.blackwx);  //设置上方状态栏的颜色

		initView();
		initData();
	}
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	private void initView() {
		content = findViewById(R.id.content);
		mTv_title_count = (TextView) findViewById(R.id.tv_title_count);
		mVp = (ViewPager) findViewById(R.id.vp_main);
		mIv_title_up = (ImageView) findViewById(R.id.iv_title_up);
		mBtn_ok = (Button) findViewById(R.id.btn_ok);
		mCb = (CheckBox) findViewById(R.id.cb);
		mRl_customActionbar = (RelativeLayout) findViewById(R.id.rl_customActionbar);
		rl_container_bottom = (RelativeLayout) findViewById(R.id.rl_container_bottom);
		mLp=(RelativeLayout.LayoutParams) mRl_customActionbar.getLayoutParams();
		mCb.setOnClickListener(this);
		mBtn_ok.setOnClickListener(this);
		mIv_title_up.setOnClickListener(this);
	}

	private void initData() {
		Intent intent = getIntent();
		mAddfriendPath = intent.getStringArrayListExtra("addfriendPath");
		mPosition1 = intent.getIntExtra("position", 0);
		mAllImages = (List<ImageModel>) intent.getSerializableExtra("list");
		selectedImages = (List<ImageModel>) intent.getSerializableExtra("selectlist");

		PhotoDetailsAdapter adapter = new PhotoDetailsAdapter(this, mAllImages, selectedImages);
		adapter.setView(mRl_customActionbar,rl_container_bottom,content,tintManager);
		mVp.setAdapter(adapter);

		if (selectedImages!=null&&selectedImages.size()!=0){
			setBtnWidth(100);
			mBtn_ok.setText("确定(" + selectedImages.size() + "/9)");
			mTv_title_count.setText(((mPosition1==0?1:mPosition1+1)+"/"+mAllImages.size()));
			if (selectedImages.contains(mAllImages.get(mPosition1))){
				mCb.setChecked(true);
			}else {
				mCb.setChecked(false);
			}
		}
		if (mAllImages != null) {
			if ( mPosition1 !=0){
				mVp.setCurrentItem(mPosition1);
				mTv_title_count.setText((mPosition1+1)+"/"+mAllImages.size());
			}
		}


		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			mLp.topMargin= UIUtils.getStatusBarHeight() ;
			mRl_customActionbar.setLayoutParams(mLp);
		}

		mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mPosition1 = position+1;
			}
			@Override
			public void onPageSelected(int position) {
				mPosition1 = position+1;
				mTv_title_count.setText(mPosition1+"/"+mAllImages.size());
				if (selectedImages!=null){
					if (selectedImages.contains(mAllImages.get(mVp.getCurrentItem()))){
						mCb.setChecked(true);
					}else {
						mCb.setChecked(false);
					}
				}
			}
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}
	public void setBtnWidth(int width){
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mBtn_ok.getLayoutParams();
		lp.width = UIUtils.dip2px(width);
		mBtn_ok.setLayoutParams(lp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cb:
				if (mCb.isChecked()&&selectedImages.size()>8){
					mCb.setChecked(false);
					Toast.makeText(PhotoInfoActivity.this, "不能超过9张", Toast.LENGTH_SHORT).show();
				}else {
					if (mCb.isChecked()){
						if (mAllImages!=null){
							selectedImages.add(mAllImages.get(mPosition1-1));
						}else {
							selectedImages.add(selectedImages.get(mPosition1-1));
						}
						setBtnWidth(100);
						mBtn_ok.setText("确定(" + selectedImages.size() + "/9)");
					}else {
						if (mAllImages!=null){
							selectedImages.remove(mAllImages.get(mPosition1-1));
						}else {
							selectedImages.remove(selectedImages.get(mPosition1-1));
						}
						if (selectedImages.size() != 0) {
							mBtn_ok.setText("确定(" + selectedImages.size() + "/9)");
						} else {
							setBtnWidth(60);
							mBtn_ok.setText("确定");
						}
					}
					if (mAllImages!=null){
						mAllImages.get(mPosition1-1).setIsChecked(mCb.isChecked());
					}else {
						selectedImages.get(mPosition1-1).setIsChecked(mCb.isChecked());
					}
				}
				break;
			case R.id.btn_ok:
				Intent intent = new Intent(PhotoInfoActivity.this, FriendAddActivity.class);
				if (mAddfriendPath!=null){
					mAddfriendPath.add(mAllImages.get(mPosition1).getPath());
					intent.putStringArrayListExtra("addfriendPath",mAddfriendPath);
				}else {
					intent.putExtra("selectlist", (Serializable) selectedImages);
				}
				startActivity(intent);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				finish();
				AblumActivity.sActivity.finish();
				break;
			case R.id.iv_title_up:
				Intent intent1 = new Intent();
				intent1.putExtra("selectlist", (Serializable) selectedImages);
				setResult(Constant.I_RESULT_PHOTOTOABLUM,intent1);
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch ( keyCode) {
			case KeyEvent.KEYCODE_BACK :
				Intent intent = new Intent();
				intent.putExtra("selectlist", (Serializable) selectedImages);
				setResult(Constant.I_RESULT_PHOTOTOABLUM,intent);
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
		}
		return true;
	}
}
