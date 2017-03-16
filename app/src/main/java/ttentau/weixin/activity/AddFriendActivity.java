package ttentau.weixin.activity;

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
import ttentau.weixin.uitls.IntentUtils;
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
        setContentView(R.layout.activity_addfriend);
        initView();
        initData();
    }

    private void initData() {
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
                   IntentUtils.startActivity(AddFriendActivity.this,intent);
                }else {
                    UIUtils.Toast("用户名不能为空");
                }
            }
        });
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("添加朋友");
        mIv_search_friend = (ImageView) findViewById(R.id.iv_search_friend);
        mEt_search_friend = (EditText) findViewById(R.id.et_search_friend);
        mTv_my_username = (TextView) findViewById(R.id.tv_my_username);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        IntentUtils.finish(this);
        return true;
    }
}
