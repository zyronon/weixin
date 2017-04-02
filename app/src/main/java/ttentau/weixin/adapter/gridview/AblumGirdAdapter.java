package ttentau.weixin.adapter.gridview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.found.AblumActivity;
import ttentau.weixin.activity.found.PhotoInfoActivity;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.Constant;
import ttentau.weixin.uitls.UIUtils;


/**
 * Created by ttent on 2017/3/24.
 */

public class AblumGirdAdapter extends BaseAdapter {

	private final int[] mWh;
	private final RelativeLayout.LayoutParams mLp;
	private List<ImageModel> mImages;
	private List<ImageModel> selectedImages;
	private AblumActivity mContext;
	private TextView tv_right;
	private Button mBtn_ok;
	private RelativeLayout.LayoutParams mLp1;

	public AblumGirdAdapter(AblumActivity ablumActivity, List<ImageModel> images, List<ImageModel> selectedImages) {
		this.mImages = images;
		this.selectedImages = selectedImages;
		mContext = ablumActivity;
		mWh = UIUtils.getWidthAndHeight(ablumActivity);
		mLp = new RelativeLayout.LayoutParams(mWh[0] / 3, mWh[0] / 3);

	}
	public void setView(Button btn, TextView textView) {
		tv_right = textView;
		mBtn_ok = btn;
		mLp1 = (RelativeLayout.LayoutParams) mBtn_ok.getLayoutParams();
	}
	public void upData(List<ImageModel> images, List<ImageModel> selectedImages) {
		this.mImages = images;
		this.selectedImages = selectedImages;
	}
	@Override
	public int getCount() {
		return mImages.size();
	}

	@Override
	public ImageModel getItem(int position) {
		return mImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ImageModel item = getItem(position);
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = UIUtils.inflate(R.layout.item_gv_albumlist);
			holder.mImageView = (ImageView) convertView.findViewById(R.id.iv);
			holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cb);
			holder.mImageView.setLayoutParams(mLp);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PhotoInfoActivity.class);
				intent.putExtra("list", (Serializable) mImages);
				intent.putExtra("position", position);
				intent.putExtra("selectlist", (Serializable) selectedImages);
				/*
				 * if (mAddfriendPath!=null){
				 * intent.putStringArrayListExtra("addfriendPath",
				 * mAddfriendPath); }
				 */
				mContext.startActivityForResult(intent, Constant.I_REQUEST_ABLUMTOPHOTOINFO);
				mContext.overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
			}
		});
		holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (holder.mCheckBox.isChecked() && selectedImages.size() > 8) {
					holder.mCheckBox.setChecked(false);
					UIUtils.Toast("不能超过9张");
				} else {
					upDataWord(holder.mCheckBox.isChecked(), item, holder);
					item.setIsChecked(holder.mCheckBox.isChecked());
				}
			}
		});
		if (selectedImages.contains(item)){
			holder.mCheckBox.setChecked(true);
			holder.mImageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		}else {
			holder.mCheckBox.setChecked(false);
			holder.mImageView.setColorFilter(Color.TRANSPARENT);
		}
//		holder.mCheckBox.setChecked(selectedImages.contains(item) ? true : false);
		Glide.with(mContext).load(item.getPath()).into(holder.mImageView);
		return convertView;
	}
	class ViewHolder {
		public ImageView mImageView;
		public CheckBox mCheckBox;
	}
	// 更新确定按钮的文字和预览的文字
	public void upDataWord(boolean isChecked, ImageModel item, ViewHolder holder) {
		if (isChecked) {
			selectedImages.add(item);
			holder.mImageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
			mLp1.width = UIUtils.dip2px(100);
			mBtn_ok.setLayoutParams(mLp1);
		} else {
			selectedImages.remove(item);
			holder.mImageView.setColorFilter(Color.TRANSPARENT);
		}
		mBtn_ok.setText("确定(" + selectedImages.size() + "/9)");
		tv_right.setText("预览(" + selectedImages.size() + ")");
		tv_right.setEnabled(true);
		if (selectedImages.size() == 0) {
			mLp1.width = UIUtils.dip2px(60);
			mBtn_ok.setLayoutParams(mLp1);
			mBtn_ok.setText("确定");
			tv_right.setText("预览");
			tv_right.setEnabled(false);
		}
	}
}
