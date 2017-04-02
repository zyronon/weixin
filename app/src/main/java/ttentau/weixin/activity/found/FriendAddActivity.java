package ttentau.weixin.activity.found;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.adapter.gridview.FriendAddAdapter;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.bean.MyCircleItem;
import ttentau.weixin.bean.PhotoInfoMy;
import ttentau.weixin.uitls.Data2Data;
import ttentau.weixin.uitls.UIUtils;


/**这是发朋友圈的activity
 * Created by ttent on 2017/2/20.
 */
public class FriendAddActivity extends BaseActivity implements View.OnClickListener {

    private GridView mGv;
    private ArrayList<String> mListPath;
    private FriendAddAdapter mAdapter;
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
    private List<ImageModel> selectedImages;
    private int[] mWh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendadd);
        initActionBar();
        initView();
        initData();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.et_friendadd);
        mRl_customActionbar = (RelativeLayout) findViewById(R.id.rl_customActionbar);
        mIv_title_up = (ImageView) findViewById(R.id.iv_title_up);
        mBtn_ok = (Button) findViewById(R.id.btn_ok);
        mGv = (GridView) findViewById(R.id.gv);

        mBtn_ok.setOnClickListener(this);
        mIv_title_up.setOnClickListener(this);
    }

    private void initData() {
        mWh = UIUtils.getWidthAndHeight(this);
        initTime();
        Intent intent = getIntent();
        selectedImages = (List<ImageModel>) intent.getSerializableExtra("selectlist");
        mAdapter = new FriendAddAdapter(selectedImages,mWh);
        mGv.setAdapter(mAdapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedImages.size()) {
                    Intent intent = new Intent(FriendAddActivity.this, AblumActivity.class);
                    intent.putExtra("selectlist", (Serializable) selectedImages);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                    finish();
                    //Log.e("tag", "position++" + position);
                }else {
                    Intent intent = new Intent(UIUtils.getContext(), PhotoInfoActivity.class);
                    intent.putExtra("list", (Serializable) selectedImages);
                    intent.putExtra("position", position);
                    intent.putExtra("selectlist", (Serializable) selectedImages);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                    finish();
                }
            }
        });
        mGv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedImages.size()) {
                    return true;
                } else {
                    selectedImages.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    private void initTime() {
        mTime = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        // instance.setTimeInMillis(time);
        mYear = instance.get(Calendar.YEAR);
        mMouth = instance.get(Calendar.MONTH) + 1;
        mDay = instance.get(Calendar.DAY_OF_MONTH);
        mHour = instance.get(Calendar.HOUR_OF_DAY);
        mMinute = instance.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_up:
                shwoDialog();
                break;
            case R.id.btn_ok:
                String message = mEt.getText().toString();
                if (UIUtils.isEmpty(message)){
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    MyCircleItem mi = new MyCircleItem();
                    mi.setItemId(0);
                    mi.setUser(Data2Data.getCurrUser());
                    mi.setContent(message);

                    if (selectedImages!=null&&selectedImages.size()!=0){
                        mi.setType(MyCircleItem.TYPE_IMG);
                        ArrayList<PhotoInfoMy> list = new ArrayList<>();
                        for (int i = 0; i < selectedImages.size(); i++) {
                            list.add(new PhotoInfoMy(selectedImages.get(i).getPath()));
                        }
                        mi.setPhotos(list);
                    }
                    mi.setCreateTime(mYear + "|" + mMouth + "|" + mDay + "|" + mHour + "|" + mMinute+"|");
                    mi.setCompareData(mTime);
                    Intent intent = new Intent(ACTION);
                    intent.putExtra("msgInfo",mi);
                    sendBroadcast(intent);
                    finish();
                    overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                }
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode) {
            case KeyEvent.KEYCODE_BACK :
                shwoDialog();
                break;
        }
        return true;
    }
    public void shwoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("退出此次编辑？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
            }
        });
        builder.show();
    }
}
