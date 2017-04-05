package ttentau.weixin.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.bean.MyUser;
import ttentau.weixin.uitls.Constant;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/4/1.
 */
public class UpdataUserNameAndAuToGraghActivity extends BaseActivity {
    @BindView(R.id.iv_title_up)
    ImageView mIvTitleUp;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.et_updatauserinfo)
    EditText mEtUpdatauserinfo;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    private int mWhichform;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_updatausernameandautogragh);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {
        mBtnOk.setClickable(false);
        MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
        String name = currentUser.getName();
        String autograph = currentUser.getAutograph();
        Intent intent = getIntent();
        mWhichform = intent.getIntExtra("whichform", 0);
        switch (mWhichform) {
            case 2:
                if (!UIUtils.isEmpty(name)){
                    mEtUpdatauserinfo.setText(name);
                }
                break;
            case 8:
                if (!UIUtils.isEmpty(autograph)){
                    mEtUpdatauserinfo.setText(autograph);
                }
                mTvHint.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
        mEtUpdatauserinfo.setSelection(mEtUpdatauserinfo.getText().length());
        mEtUpdatauserinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBtnOk.setClickable(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBtnOk.setClickable(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnOk.setClickable(true);
            }
        });
/* */
    }
    @OnClick({R.id.iv_title_up, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_up:
                IntentUtils.finish(this);
                break;
            case R.id.btn_ok:
                String name = mEtUpdatauserinfo.getText().toString().trim();
                if (UIUtils.isEmpty(name)){
                    UIUtils.Toast("不能为空");
                    return;
                }
                MyUser newUser = new MyUser();
                switch (mWhichform) {
                    case 2:
                        newUser.setName(name);
                        break;
                    case 8:
                        newUser.setAutograph(name);
                        break;
                    default:
                        break;
                }
                MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            setResult(Constant.I_RESULT_PHOTOTOABLUM);
                            IntentUtils.finish(UpdataUserNameAndAuToGraghActivity.this);
                        }else{

                        }
                    }
                });
                break;
        }
    }
}
