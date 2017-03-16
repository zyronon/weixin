package ttentau.weixin.activity.found;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;

import java.util.ArrayList;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.QueryImage;


/**
 * 这是在图片列表那里点击略缩图之后，跳到这个acticity放大图片
 * Created by ttent on 2017/2/25.
 */

public class PhotoInfoActivity extends BaseActivity {

	private ViewPager mVp;
	private QueryImage mImage;
	private List<ImageModel> mList;
	private int mSize;
	private List<ImageModel> mAllImages;
	private Button mBtn_ok;
	private int mPosition1;
    private ArrayList<String> mAddfriendPath;
	private RelativeLayout mRl_customActionbar;
	private ImageView mIv_title_up;
	private TextView mTv_title_count;
	private boolean rlisshow;
	private int mHeight;
	private int mWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoinfo);
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowManager windowManager = getWindowManager();
		mHeight = windowManager.getDefaultDisplay().getHeight();
		mWidth = windowManager.getDefaultDisplay().getWidth();
		ab.hide();
		initData();
		initView();
	}
	private void initView() {
		ViewPager vp = (ViewPager) findViewById(R.id.vp_main);
		mIv_title_up = (ImageView) findViewById(R.id.iv_title_up);
		mRl_customActionbar = (RelativeLayout) findViewById(R.id.rl_customActionbar);
		mBtn_ok = (Button) findViewById(R.id.btn_ok);
		mTv_title_count.setText(mPosition1+"/"+mAllImages.size());
		if (mAllImages != null && mPosition1 != -1) {
			vp.setAdapter(new MyAdapter());
			vp.setCurrentItem(mPosition1);
		}
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mPosition1 = position;
				//mTv_title_count.setText(mPosition1+"/"+mAllImages.size());
			}
			@Override
			public void onPageSelected(int position) {
				mPosition1 = position;
				mTv_title_count.setText(mPosition1+"/"+mAllImages.size());
			}
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		mBtn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PhotoInfoActivity.this, FriendAddActivity.class);
				if (mAddfriendPath!=null){
					mAddfriendPath.add(mAllImages.get(mPosition1).getPath());
					intent.putStringArrayListExtra("addfriendPath",mAddfriendPath);
				}else {
					intent.putExtra("path", mAllImages.get(mPosition1).getPath());
				}
				startActivity(intent);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				finish();
				AlbumListActivity.sActivity.finish();
//				ActivityCollector.removeActivity(AlbumListActivity.this);
			}
		});
		mIv_title_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
			}
		});
	}

	private void initData() {
		mTv_title_count = (TextView) findViewById(R.id.tv_title_count);
		Intent intent = getIntent();
		mAddfriendPath = intent.getStringArrayListExtra("addfriendPath");
		mPosition1 = intent.getIntExtra("position", -1);
		mAllImages = (List<ImageModel>) intent.getSerializableExtra("list");
		mTv_title_count.setText(mPosition1+"/"+mAllImages.size());
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mAllImages.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			String path = mAllImages.get(position).getPath();
			//Bitmap bitmap = BitmapFactory.decodeFile(path);
			//Drawable fromPath = Drawable.createFromPath(path);
			View inflate = View.inflate(PhotoInfoActivity.this, R.layout.item_vp_photoinfo, null);
			PhotoView view = (PhotoView) inflate.findViewById(R.id.iv);
			view.enable();
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.e("tag","Iamgeview Click========");
					if (!rlisshow){
						mRl_customActionbar.setVisibility(View.INVISIBLE);
						rlisshow=true;
					}else {
						mRl_customActionbar.setVisibility(View.VISIBLE);
						rlisshow=false;
					}
				}
			});

			Bitmap bitmap = BitmapFactory.decodeFile(path);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap bitmap1;
			float scale = (float) width / (float) height;
			if (width<mWidth/2){
				width=mWidth/2;
				if (scale<1){
					height = (int) (width /scale);
				}else {
					height = (int) (width * scale);
				}
			}else {
				width=mWidth;
				if (scale<1){
					height = (int) (width /scale);
				}else {
					height = (int) (width * scale);
				}
			}
			bitmap1 = ThumbnailUtils.extractThumbnail(bitmap, width, height);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap1);
			view.setImageDrawable(bitmapDrawable);
			container.addView(inflate);
			return inflate;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
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
