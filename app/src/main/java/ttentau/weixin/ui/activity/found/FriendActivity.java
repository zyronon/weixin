package ttentau.weixin.ui.activity.found;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;
import ttentau.weixin.ui.view.FriendRefreshView;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/14.
 */
public class FriendActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("朋支圈");
        initView();
    }

    private void initView() {
        final FriendRefreshView frv = (FriendRefreshView) findViewById(R.id.frv);
        frv.setAdapter(new MyAdapter());
        frv.setOnRefreshListener(new FriendRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frv.stopRefresh();
                    }
                }, 2000);
            }
        });

    }
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
            ViewHolder mHolder = null;
            if (convertView == null) {
                convertView = View.inflate(FriendActivity.this, R.layout.friend_item, null);
            }
            mHolder = ViewHolder.getInstence(convertView);
            return convertView;
        }
    }
    static class ViewHolder {
        private ViewHolder(View convertView) {
        }
        public static ViewHolder getInstence(View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
            case 789:
                View mView = View.inflate(this,R.layout.dialogview,null);
                RelativeLayout mRl_dialog_camera= (RelativeLayout) mView.findViewById(R.id.rl_dialog_camera);
                RelativeLayout mRl_dialog_form_photo = (RelativeLayout) mView.findViewById(R.id.rl_dialog_form_photo);
                mRl_dialog_camera.setOnClickListener(this);
                mRl_dialog_form_photo.setOnClickListener(this);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                mBuilder.setView(mView);
                mBuilder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,789,0,"设置").setEnabled(true).setIcon(R.drawable.actionbar_icon_camera).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode) {
            case KeyEvent.KEYCODE_BACK :
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_dialog_camera:
                UIUtils.Toast("这是拍摄");
                break;
            case R.id.rl_dialog_form_photo:
                UIUtils.Toast("这是从相册选择");
                break;
        }
    }
}
