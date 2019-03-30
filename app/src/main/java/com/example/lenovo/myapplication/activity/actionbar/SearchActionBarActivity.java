package com.example.lenovo.myapplication.activity.actionbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.activity.find.WebActivity;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.UIUtils;

/**
 * Created by ttent on 2017/3/15.
 */

public class SearchActionBarActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEt_search_actionbar;
    private TextView mTv_result;
    private RelativeLayout mRl_result;
    private RelativeLayout mRl_root;
    private LinearLayout mLl_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_actionbar);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        mRl_root.setBackgroundColor(UIUtils.getColor(R.color.light_gray1));
        mLl_display.setVisibility(View.VISIBLE);
        mRl_result.setVisibility(View.INVISIBLE);
        mEt_search_actionbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             /*   if (count==0){
                    mLl_display.setVisibility(View.VISIBLE);
                    mRl_result.setVisibility(View.INVISIBLE);
                    mTv_result.setText("");
                    mRl_root.setBackgroundColor(UIUtils.getColor(R.color.light_gray1));
                }*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = mEt_search_actionbar.getText().toString();
                if (search.length()==0){
                    mLl_display.setVisibility(View.VISIBLE);
                    mRl_result.setVisibility(View.INVISIBLE);
                    mTv_result.setText("");
                    mRl_root.setBackgroundColor(UIUtils.getColor(R.color.light_gray1));
                }else {
                    mLl_display.setVisibility(View.INVISIBLE);
                    mRl_result.setVisibility(View.VISIBLE);
                    mTv_result.setText(search);
                    mRl_root.setBackgroundColor(UIUtils.getColor(R.color.main_white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initView() {
        findViewById(R.id.iv_title_up).setOnClickListener(this);
        mEt_search_actionbar = (EditText) findViewById(R.id.et_search_actionbar);
        mTv_result = (TextView) findViewById(R.id.tv_result);
        mRl_result = (RelativeLayout) findViewById(R.id.rl_result);
        mRl_root = (RelativeLayout) findViewById(R.id.rl_root);
        mLl_display = (LinearLayout) findViewById(R.id.ll_display);
        mRl_result.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_up:
                IntentUtils.finish(this);
                break;
            case R.id.rl_result:
                String result = "https://m.baidu.com/from=844b/s?word="+mEt_search_actionbar.getText().toString();
                Intent intent = new Intent(UIUtils.getContext(), WebActivity.class);
                String[] shoppingValue={result,"搜索"};
                intent.putExtra("normal",shoppingValue);
                IntentUtils.startActivity(this,intent);
                break;
        }
    }
}
