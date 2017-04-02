package ttentau.weixin.adapter.gridview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.UIUtils;


/**
 * Created by ttent on 2017/3/26.
 */

public class FriendAddAdapter extends BaseAdapter {
	private List<ImageModel> selectedImages;
    private int[] mWh;
    private LinearLayout.LayoutParams mLp;

	public FriendAddAdapter(List<ImageModel> selectedImages,int[] mWh){
        this.selectedImages=selectedImages;
        this.mWh=mWh;
        mLp = new LinearLayout.LayoutParams((mWh[0] / 5)-5,(mWh[0] / 5)-5);
    }

	@Override
	public int getCount() {
		return selectedImages.size() + 1;
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
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = UIUtils.inflate(R.layout.item_gv_friendaddactivity);
		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		iv.setLayoutParams(mLp);
		if (position == selectedImages.size()) {
			if (selectedImages.size() == 9) {
				iv.setVisibility(View.GONE);
			} else {
				Glide.with(UIUtils.getContext()).load(R.drawable.ic_addfriend_last_bg).into(iv);
			}
		} else {
			Glide.with(UIUtils.getContext()).load(selectedImages.get(position).getPath()).into(iv);
		}
		return view;
	}
}
