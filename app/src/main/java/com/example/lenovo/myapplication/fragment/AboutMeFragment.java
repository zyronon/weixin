package com.example.lenovo.myapplication.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.me.MyInfoActivity;
import com.example.lenovo.myapplication.activity.me.SettingActivity;
import com.example.lenovo.myapplication.utils.UIUtils;

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
        mSetting = mView.findViewById(R.id.setting);
        my_info = mView.findViewById(R.id.my_info);
        rl_photo = mView.findViewById(R.id.rl_photo);
        rl_collect = mView.findViewById(R.id.rl_collect);
        rl_money = mView.findViewById(R.id.rl_money);
        rl_card = mView.findViewById(R.id.rl_card);
        rl_emoji = mView.findViewById(R.id.rl_emoji);
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
                startActivity(new Intent(UIUtils.getContext(),MyInfoActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
//            case R.id.rl_photo:
//                break;
//            case R.id.rl_collect:
//                break;
//            case R.id.rl_money:
//                break;
//            case R.id.rl_card:
//                break;
//            case R.id.rl_emoji:
//                break;
            case R.id.setting:
                startActivity(new Intent(UIUtils.getContext(),SettingActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
        }
    }
}
