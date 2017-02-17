package ttentau.weixin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import ttentau.weixin.R;
import ttentau.weixin.uitls.ActivityCollector;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/10.
 */

public class SearchActivity extends BaseActivity {

    private TextView mTv_search_username;
    private Button mBtn_sendmessage_search;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar ab = getSupportActionBar();
        final Intent intent = getIntent();
        final String search_id = intent.getStringExtra("search_id");
        ab.setTitle("详细资料");
		ab.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_search);
        mTv_search_username = (TextView) findViewById(R.id.tv_search_username);
        mBtn_sendmessage_search = (Button) findViewById(R.id.btn_sendmessage_search);
        if (search_id!=null){
            mTv_search_username.setText(search_id);
        }
        mBtn_sendmessage_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentUser = EMClient.getInstance().getCurrentUser();
                if (search_id.equals(currentUser)){
                    UIUtils.Toast("不能和自己聊天");
                }else {
                    Intent intent1 = new Intent(UIUtils.getContext(), ChatActivity.class);
                    intent1.putExtra("SearchActivity_userid",search_id);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                    ActivityCollector.finishAll();
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
