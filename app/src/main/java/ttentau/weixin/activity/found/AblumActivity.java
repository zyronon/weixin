package ttentau.weixin.activity.found;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.adapter.gridview.AblumGirdAdapter;
import ttentau.weixin.adapter.listview.AblumDialogListAdapter;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.Constant;
import ttentau.weixin.uitls.DialogUtils;
import ttentau.weixin.uitls.QueryImage;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/22.
 */
public class AblumActivity extends BaseActivity implements View.OnClickListener {

	public static Activity sActivity;
	private List<ImageModel> mImages;
	private RelativeLayout.LayoutParams mLp;
	private List<ImageModel> mImagesFileName;
	private QueryImage mQi;
	private int mHeight;
	private String mFirstImage;
	private int totalcount;
	private List<ImageModel> mListData;
	private int listPos = 0;
	private AblumGirdAdapter mAblumGirdAdapter;
	private List<ImageModel> selectedImages;

	private GridView mGv;
	private int[] mWh;
	private TextView tv_right;
	private Button mBtn_ok;
	private AblumDialogListAdapter mDialogListAdapter;
	private DialogUtils mDialogUtils;
	private Dialog mDialog;
	private List<ImageModel> mFromsend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ablum);
		sActivity = this;
		initActionBar();
		initView();
		initData();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
		return super.onOptionsItemSelected(item);
	}
	private void initActionBar() {
		getSupportActionBar().hide();
	}

	private void initData() {
		Intent intent = getIntent();
		mFromsend = (List<ImageModel>) intent.getSerializableExtra("selectlist");

		mDialogUtils = new DialogUtils();

		mWh = UIUtils.getWidthAndHeight(this);
		mLp = (RelativeLayout.LayoutParams) mBtn_ok.getLayoutParams();
		mQi = new QueryImage(this);
		mImages = mQi.getImages();
		selectedImages = new ArrayList<>();
		if (mFirstImage!=null){
			selectedImages=mFromsend;
		}
		mListData = mQi.getAblumDialogListData();

		totalcount = mImages.size();
		mFirstImage = mImages.get(0).getPath();
		mAblumGirdAdapter = new AblumGirdAdapter(this,mImages,selectedImages);
		mAblumGirdAdapter.setView(mBtn_ok,tv_right);
		mGv.setAdapter(mAblumGirdAdapter);
	}

	private void initView() {
		mGv = (GridView) findViewById(R.id.gv);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setEnabled(false);
		tv_right.setOnClickListener(this);
		mBtn_ok = (Button) findViewById(R.id.btn_ok);
		mBtn_ok.setOnClickListener(this);
		findViewById(R.id.iv_title_up).setOnClickListener(this);
		findViewById(R.id.tv_left).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_left :
				View view = UIUtils.inflate(R.layout.dialog_formbottom);
				ListView lv = (ListView) view.findViewById(R.id.lv);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						listPos = position;
						if (position == 0) {
							mImages = mQi.getImages();
						} else {
							mImages = mQi.getImagesFromFile(mListData.get(position - 1).getFileName());
						}
						mDialog.dismiss();
						mAblumGirdAdapter.upData(mImages,selectedImages);
						mAblumGirdAdapter.notifyDataSetChanged();
						mGv.smoothScrollToPosition(0);
					}
				});

				mDialogListAdapter = new AblumDialogListAdapter(mListData,mFirstImage,totalcount);
				mDialogListAdapter.setData(listPos);
				lv.setAdapter(mDialogListAdapter);
				lv.setSelection(listPos<2?listPos:listPos-2);
				mDialog = mDialogUtils.showFromBottom(AblumActivity.this,view, 0, mWh[1] - UIUtils.dip2px(100));
				break;
			case R.id.tv_right :
				Intent intent = new Intent(AblumActivity.this, PhotoInfoActivity.class);
				intent.putExtra("selectlist", (Serializable) selectedImages);
				intent.putExtra("list", (Serializable) selectedImages);
				startActivityForResult(intent, Constant.I_REQUEST_ABLUMTOPHOTOINFO);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				break;
			case R.id.btn_ok :
				Intent intent1 = new Intent(AblumActivity.this, FriendAddActivity.class);
				intent1.putExtra("selectlist", (Serializable) selectedImages);
				startActivity(intent1);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				finish();
				break;
			case R.id.iv_title_up :
				finish();
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("tag",resultCode+"<<<<result.....request>>>>>"+requestCode);
		switch (requestCode){
			case Constant.I_REQUEST_ABLUMTOPHOTOINFO:
				if (resultCode==Constant.I_RESULT_PHOTOTOABLUM){
					selectedImages = (List<ImageModel>) data.getSerializableExtra("selectlist");
					mAblumGirdAdapter.upData(mImages,selectedImages);
					mAblumGirdAdapter.notifyDataSetChanged();
					if (selectedImages.size() == 0) {
						mLp.width = UIUtils.dip2px(60);
						mBtn_ok.setLayoutParams(mLp);
						mBtn_ok.setText("确定");
						tv_right.setText("预览");
						tv_right.setEnabled(false);
					}else {
						mLp.width = UIUtils.dip2px(100);
						mBtn_ok.setLayoutParams(mLp);
						mBtn_ok.setText("确定(" + selectedImages.size() + "/9)");
						tv_right.setText("预览(" + selectedImages.size() + ")");
						tv_right.setEnabled(true);
					}
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
