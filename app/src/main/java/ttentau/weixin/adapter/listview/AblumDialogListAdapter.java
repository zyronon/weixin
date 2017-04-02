package ttentau.weixin.adapter.listview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.UIUtils;


/**
 * Created by ttent on 2017/3/24.
 */

public class AblumDialogListAdapter extends BaseAdapter {
	private List<ImageModel> mListData;
    private String mFirstImage;
    private int totalcount;
    private int listPos = 0;

	public AblumDialogListAdapter(List<ImageModel> mListData,String mFirstImage,int totalcount) {
		this.mListData = mListData;
        this.mFirstImage=mFirstImage;
        this.totalcount=totalcount;
	}
    public void setData(int listPos){
        this.listPos=listPos;
    }
	@Override
	public int getCount() {
		return mListData.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolderList holder = null;
		if (convertView == null) {
			holder = new ViewHolderList();
			convertView = UIUtils.inflate(R.layout.lv_item_ablum);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_item);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_filename);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count1);
			holder.rb = (RadioButton) convertView.findViewById(R.id.rb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderList) convertView.getTag();
		}
		if (position == 0) {
			holder.rb.setChecked(listPos == position ? true : false);
			Glide.with(UIUtils.getContext()).load(mFirstImage).into(holder.iv);
			holder.tv.setText("全部图片");
			holder.tv_count.setText(totalcount + "张");
		} else {
			holder.rb.setChecked(listPos == position ? true : false);
			ImageModel imageModel = mListData.get(position - 1);
			Glide.with(UIUtils.getContext()).load(imageModel.getPath()).into(holder.iv);
			holder.tv.setText(imageModel.getFileName());
			holder.tv_count.setText(imageModel.getCount() + "张");
		}
		return convertView;
	}
	class ViewHolderList {
		private ImageView iv;
		private TextView tv;
		private TextView tv_count;
		private RadioButton rb;
	}
}
