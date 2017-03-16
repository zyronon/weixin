package ttentau.weixin.activity.found;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ttentau.weixin.R;
import ttentau.weixin.bean.TestFriendInfo;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.uitls.UIUtils;

/**这是发朋友圈的activity
 * Created by ttent on 2017/2/20.
 */
public class FriendAddActivity extends BaseActivity implements View.OnClickListener {

    private GridView mGv;
    private ArrayList<String> mListPath;
    private Myadapter mAdapter;
    private RelativeLayout mRl_customActionbar;
    private ImageView mIv_title_up;
    private Button mBtn_ok;
    private EditText mEt;
    private StringBuffer sb;
    private int mYear;
    private int mHour;
    private int mMinute;
    private int mMouth;
    private int mDay;
    private static String ACTION="notifyFriendUpdate";
    private long mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendadd);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.et_friendadd);
        mRl_customActionbar = (RelativeLayout) findViewById(R.id.rl_customActionbar);
        mIv_title_up = (ImageView) findViewById(R.id.iv_title_up);
        mBtn_ok = (Button) findViewById(R.id.btn_ok);
        mGv = (GridView) findViewById(R.id.gv);
        mAdapter = new Myadapter();
        mGv.setAdapter(mAdapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mListPath.size()) {
                    Intent intent = new Intent(FriendAddActivity.this, AlbumListActivity.class);
                    intent.putStringArrayListExtra("addfriendPath", mListPath);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                    finish();
                    //Log.e("tag", "position++" + position);
                }
            }
        });
        mGv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mListPath.size()) {
                    return true;
                } else {
                    mListPath.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        mBtn_ok.setOnClickListener(this);
        mIv_title_up.setOnClickListener(this);
    }

    private void initData() {
        mTime = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        // instance.setTimeInMillis(time);
        mYear = instance.get(Calendar.YEAR);
        mMouth = instance.get(Calendar.MONTH) + 1;
        mDay = instance.get(Calendar.DAY_OF_MONTH);
        mHour = instance.get(Calendar.HOUR_OF_DAY);
        mMinute = instance.get(Calendar.MINUTE);
        mListPath = new ArrayList<>();
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        if (!TextUtils.isEmpty(path)) {
            mListPath.add(path);
        }
        ArrayList<String> addfriendPath = intent.getStringArrayListExtra("addfriendPath");
        if (addfriendPath != null) {
            mListPath = addfriendPath;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_up:
                startActivity(new Intent(FriendAddActivity.this,FriendActivity.class));
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                finish();
                break;
            case R.id.btn_ok:
                String message = mEt.getText().toString();
                if (UIUtils.isEmpty(message)){
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    TestFriendInfo tfi = new TestFriendInfo();
                    if (mListPath.size()!=0){
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < mListPath.size(); i++) {
                            String path = mListPath.get(i);
                            sb.append(path+"|");
                        }
                        tfi.setImageCount(1);
                        tfi.setImagePath(sb.toString());
                    }
                    tfi.setName("王日天");
                    tfi.setIsMy(1);
                    tfi.setContent(message);
                    tfi.setPhoto(R.drawable.myphoto);
                /*    if (mMouth<10){
                        String mouth="0"+mMouth;
                    }
                    if (mDay<10){
                        mDay=Integer.parseInt("0"+mDay);
                    }
                    if (mHour<10){
                        mHour=Integer.parseInt("0"+mHour);
                    }
                    if (mMinute<10){
                        mMinute=Integer.parseInt("0"+mMinute);
                    }*/
                    tfi.setDate(mYear + "|" + mMouth + "|" + mDay + "|" + mHour + "|" + mMinute+"|");
                    Log.e("tag",mYear + "|" + mMouth + "|" + mDay + "|" + mHour + "|" + mMinute+"|");

                    //long data=Integer.parseInt(mYear + "" + mMouth + "" + mDay + "" + mHour + "" + mMinute+"");
                    //long data=Long.parseLong(mYear + "" + mMouth + "" + mDay + "" + mHour + "" + mMinute+"");
                    tfi.setCompareData(mTime);
                    Intent intent = new Intent(ACTION);
                    intent.putExtra("msgInfo",tfi);
                    //startActivity(intent);
                    sendBroadcast(intent);
                    finish();
                    overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                }
                break;
        }
    }

    private class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListPath.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(FriendAddActivity.this, R.layout.item_gv_friendaddactivity, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            if (position == mListPath.size()) {
                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_addfriend_last_bg));
            } else {
                Bitmap bitmap1 = BitmapFactory.decodeFile(mListPath.get(position));
                iv.setImageBitmap(bitmap1);
            }
            return view;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode) {
            case KeyEvent.KEYCODE_BACK :
//                startActivity(new Intent(FriendAddActivity.this,FriendActivity.class));
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
        }
        return true;
    }
}
