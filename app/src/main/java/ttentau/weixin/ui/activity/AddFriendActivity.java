package ttentau.weixin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/10.
 */
public class AddFriendActivity extends BaseActivity{

    private ImageView mIv_search_friend;
    private EditText mEt_search_friend;
    private TextView mTv_my_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("添加朋友");
        setContentView(R.layout.activity_addfriend);
        mIv_search_friend = (ImageView) findViewById(R.id.iv_search_friend);
        mEt_search_friend = (EditText) findViewById(R.id.et_search_friend);
        mTv_my_username = (TextView) findViewById(R.id.tv_my_username);
        String currentUser = EMClient.getInstance().getCurrentUser();
        if (!TextUtils.isEmpty(currentUser)){
            mTv_my_username.setText(currentUser);
        }
        mIv_search_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = mEt_search_friend.getText().toString();
                if (!TextUtils.isEmpty(search)){
                    Intent intent = new Intent(UIUtils.getContext(), SearchActivity.class);
                    intent.putExtra("search_id",search);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                }else {
                    UIUtils.Toast("用户名不能为空");
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
        return true;
    }
}
