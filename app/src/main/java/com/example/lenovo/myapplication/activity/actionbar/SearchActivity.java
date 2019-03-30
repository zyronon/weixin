package com.example.lenovo.myapplication.activity.actionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.utils.IntentUtils;

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
        setContentView(R.layout.activity_search);
        initView();

        initData();
    }

    private void initData() {
        final Intent intent = getIntent();
        final String search_id = intent.getStringExtra("search_id");
        if (search_id!=null){
            mTv_search_username.setText(search_id);
        }
        mBtn_sendmessage_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String currentUser = EMClient.getInstance().getCurrentUser();
//                if (search_id.equals(currentUser)){
//                    UIUtils.Toast("不能和自己聊天");
//                }else {
//                    Intent intent1 = new Intent(UIUtils.getContext(), ChatActivity.class);
//                    intent1.putExtra("SearchActivity_userid",search_id);
//                    IntentUtils.startActivity(SearchActivity.this,intent1);
//                    ActivityCollector.finishAll();
//                }
            }
        });
    }

    private void  initView() {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("详细资料");
        ab.setDisplayHomeAsUpEnabled(true);
        mTv_search_username = (TextView) findViewById(R.id.tv_search_username);
        mBtn_sendmessage_search = (Button) findViewById(R.id.btn_sendmessage_search);
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		IntentUtils.finish(this);
		return true;
	}
}
