package ttentau.weixin.fragment;

import android.view.View;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class ContactsFragment extends BaseFragment {

    private View mView;

    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_contacts);
        return mView;
    }

    @Override
    public void initData() {

    }
}
