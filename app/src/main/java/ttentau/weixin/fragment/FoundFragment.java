package ttentau.weixin.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class FoundFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private RelativeLayout mFriend_found;

    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_found);
        mFriend_found = (RelativeLayout) mView.findViewById(R.id.friend_found);
        mFriend_found.setOnClickListener(this);
        return mView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        UIUtils.Toast("这是朋友圈哦");
    }
}
