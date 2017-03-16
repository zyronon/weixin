package ttentau.weixin.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import ttentau.weixin.R;
import ttentau.weixin.activity.me.SettingActivity;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class AboutMeFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private RelativeLayout mSetting;
    private RelativeLayout my_info;
    private RelativeLayout rl_photo;
    private RelativeLayout rl_collect;
    private RelativeLayout rl_money;
    private RelativeLayout rl_card;
    private RelativeLayout rl_emoji;

    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_about_me);
        mSetting = (RelativeLayout) mView.findViewById(R.id.setting);
        my_info = (RelativeLayout) mView.findViewById(R.id.my_info);
        rl_photo = (RelativeLayout) mView.findViewById(R.id.rl_photo);
        rl_collect = (RelativeLayout) mView.findViewById(R.id.rl_collect);
        rl_money = (RelativeLayout) mView.findViewById(R.id.rl_money);
        rl_card = (RelativeLayout) mView.findViewById(R.id.rl_card);
        rl_emoji = (RelativeLayout) mView.findViewById(R.id.rl_emoji);
        return mView;
    }

    @Override
    public void initData() {
        my_info.setOnClickListener(this);
        rl_photo.setOnClickListener(this);
        rl_collect.setOnClickListener(this);
        rl_money.setOnClickListener(this);
        rl_card.setOnClickListener(this);
        rl_emoji.setOnClickListener(this);
        mSetting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_info:
                break;
            case R.id.rl_photo:
                break;
            case R.id.rl_collect:
                break;
            case R.id.rl_money:
                break;
            case R.id.rl_card:
                break;
            case R.id.rl_emoji:
                break;
            case R.id.setting:
                startActivity(new Intent(UIUtils.getContext(),SettingActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
        }
    }
}
