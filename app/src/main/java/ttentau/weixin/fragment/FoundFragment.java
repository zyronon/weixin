package ttentau.weixin.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.found.FriendActivity;
import ttentau.weixin.ui.activity.found.ScannerActivity;
import ttentau.weixin.ui.activity.WebActivity;
import ttentau.weixin.ui.activity.found.NearPeopleActivity;
import ttentau.weixin.ui.activity.found.PiaoLiuPingActivity;
import ttentau.weixin.ui.activity.found.YaoYiYaoActivity;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class FoundFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private RelativeLayout mFriend_found;
    private RelativeLayout mSao_yi_sao;
    private RelativeLayout mYao_yi_yao;
    private RelativeLayout mNear_people;
    private RelativeLayout mPiaoliu_ping;
    private RelativeLayout mShopping;
    private RelativeLayout mGames;
    private RelativeLayout mSmall_program;


    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_found);
        mFriend_found = (RelativeLayout) mView.findViewById(R.id.friend_found);
        mSao_yi_sao = (RelativeLayout)mView.findViewById(R.id.sao_sao);
        mYao_yi_yao = (RelativeLayout)mView.findViewById(R.id.yao_yi_yao);
        mNear_people = (RelativeLayout)mView.findViewById(R.id.near_people);
        mPiaoliu_ping = (RelativeLayout)mView.findViewById(R.id.piaoliu_ping);
        mShopping = (RelativeLayout)mView.findViewById(R.id.shopping);
        mGames = (RelativeLayout)mView.findViewById(R.id.games);
        mSmall_program = (RelativeLayout)mView.findViewById(R.id.small_program);

        mFriend_found.setOnClickListener(this);
        mSao_yi_sao.setOnClickListener(this);
        mYao_yi_yao.setOnClickListener(this);
        mNear_people.setOnClickListener(this);
        mPiaoliu_ping.setOnClickListener(this);
        mShopping.setOnClickListener(this);
        mGames.setOnClickListener(this);
        mSmall_program.setOnClickListener(this);
        return mView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friend_found:
                startActivity(new Intent(UIUtils.getContext(), FriendActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.sao_sao:
                startActivity(new Intent(UIUtils.getContext(), ScannerActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.yao_yi_yao:
                startActivity(new Intent(UIUtils.getContext(), YaoYiYaoActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.near_people:
                startActivity(new Intent(UIUtils.getContext(), NearPeopleActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.piaoliu_ping:
                startActivity(new Intent(UIUtils.getContext(),PiaoLiuPingActivity.class));
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.shopping:
                Intent intent = new Intent(UIUtils.getContext(), WebActivity.class);
                String[] shoppingValue={"http://m.jd.com","京东购物"};
                intent.putExtra("from_shopping",shoppingValue);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.games:
                Intent intent1 = new Intent(UIUtils.getContext(), WebActivity.class);
                String[] gamesValue={"http://m.4399.cn","微信游戏"};
                intent1.putExtra("from_games",gamesValue);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
            case R.id.small_program:
                break;
        }
    }
}
