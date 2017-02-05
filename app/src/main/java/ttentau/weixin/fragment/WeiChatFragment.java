package ttentau.weixin.fragment;

import android.view.View;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class WeiChatFragment extends BaseFragment {

    private View mView;
    private TextView mTv;

    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_weichat);
        mTv = (TextView) mView.findViewById(R.id.textView);
        return mView;
    }

    @Override
    public void initData() {
        mTv.setText("ys fs fsdf s");
    }
}
