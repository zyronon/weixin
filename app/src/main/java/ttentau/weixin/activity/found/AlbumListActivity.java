package ttentau.weixin.activity.found;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.BitmapUtils;
import ttentau.weixin.uitls.ImageCache;
import ttentau.weixin.uitls.QueryImage;


/**
 * 这是图片列表Activity
 * 用异步多线程写的，勿删
 * Created by ttent on 2017/3/9.
 */


public class AlbumListActivity extends BaseActivity {

    private GridView mGv;
    private QueryImage mQuery;
    private List<ImageModel> AllIamge;
    private ImageCache mIc;
    private TextView mTv_left;
    private TextView mTv_right;
    private int checkCount=0;
    private ArrayList<String> mList;
    private ArrayList<String> mAddfriendPath;
    public static Activity sActivity=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumlist);
        sActivity=this;
        ActionBar ab = getSupportActionBar();
        ab.setTitle("图片和视屏");
        ab.setDisplayHomeAsUpEnabled(true);
        initData();
        initView();
    }

    private void initData() {
        mAddfriendPath = getIntent().getStringArrayListExtra("addfriendPath");
        mQuery = new QueryImage(this);
        AllIamge = mQuery.getImages();
        mIc = new ImageCache();
    }

    private void initView() {
        mGv = (GridView) findViewById(R.id.gv);
        mTv_left = (TextView) findViewById(R.id.tv_left);
        mTv_right = (TextView) findViewById(R.id.tv_right);

        mGv.setAdapter(new MyAdapter());
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FriendActivity.this, "position"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AlbumListActivity.this, PhotoInfoActivity.class);
                intent.putExtra("list", (Serializable) AllIamge);
                intent.putExtra("position",position);
                if (mAddfriendPath!=null){
                    intent.putStringArrayListExtra("addfriendPath",mAddfriendPath);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return AllIamge.size();
        }

        @Override
        public ImageModel getItem(int position) {
            return AllIamge.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageModel item = getItem(position);
            final ViewHolder holder;
            if (convertView==null){
                holder=new ViewHolder();
                convertView=View.inflate(AlbumListActivity.this,R.layout.item_gv_albumlist,null);
                holder.mImageView= (ImageView) convertView.findViewById(R.id.iv);
              //  holder.mCheckBox= (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mImageView.setTag(item.getPath());
            holder.mImageView.setImageBitmap(null);
            loadImage(item,holder.mImageView);
            return convertView;
        }
    }
    class ViewHolder {
        public ImageView mImageView;
    }
    public void loadImage(final ImageModel item, final ImageView iv){
        Bitmap cache = mIc.getBitmapFromMemCache(item.getPath());
        if (cache!=null){
            iv.setImageBitmap(cache);
//            Log.e("tag","cache被调用了");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapUtils.getBitmapThumbnail(item.getPath(),iv.getMeasuredWidth(),iv.getMeasuredWidth());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (item.getPath().equals(iv.getTag())){
                            mIc.addBitmapToMemCache(item.getPath(),bitmap);
//                            Log.e("tag","加载被调用了");
                            iv.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        }).start();
        /*MySync mySync = new MySync(iv, path);
        mySync.execute("");*/
    }
   /* class MySync extends AsyncTask<String,Void,Bitmap>{

        private final ImageView mIv;
        private final String mPath;
        private final int mMeasuredWidth;
        private final int mMeasuredHeight;

        public MySync(ImageView iv,String path){
            mIv = iv;
            mPath = path;
            mMeasuredWidth = mIv.getMeasuredWidth();
            mMeasuredHeight = mIv.getMeasuredHeight();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = BitmapUtils.getBitmapThumbnail(mPath,mMeasuredWidth,mMeasuredHeight);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mIv.setImageBitmap(bitmap);
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
