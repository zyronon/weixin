package ttentau.weixin.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.log.SplashActivity;
import ttentau.weixin.uitls.ActivityCollector;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class AboutMeFragment extends BaseFragment {

    private View mView;
    private RelativeLayout mSetting;

    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_about_me);
        mSetting = (RelativeLayout) mView.findViewById(R.id.setting);
        return mView;
    }

    @Override
    public void initData() {
        mSetting.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(UIUtils.getContext(), SplashActivity.class);
                        startActivity(intent);
                        ActivityCollector.finishAll();
                    }
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
                return false;
            }
        });

    }
}
