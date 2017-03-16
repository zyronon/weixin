package ttentau.weixin.activity.found;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bm.library.PhotoView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.PhotoInfo;


/**
 * 这是图片放大activity
 * Created by ttent on 2017/3/9.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private ArrayList<PhotoInfo> mMPhotoInfos;
    private int mWidth;
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager vp = new ViewPager(this);
        setContentView(vp);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager windowManager = getWindowManager();
        mHeight = windowManager.getDefaultDisplay().getHeight();
        mWidth = windowManager.getDefaultDisplay().getWidth();
        Intent intent = getIntent();
        String path = intent.getStringExtra("imageInfo");
        final int pos = intent.getIntExtra("pos",0);
        String[] split = path.split("\\|");
        mMPhotoInfos = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            mMPhotoInfos.add(new PhotoInfo(split[i]));
        }
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mMPhotoInfos.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView pv = new PhotoView(getApplicationContext());
                pv.enable();
                pv.setBackgroundColor(getResources().getColor(R.color.main_black));
                pv.setLayoutParams(new ViewGroup.LayoutParams(mWidth,mHeight));
                //pv.setImageDrawable(Drawable.createFromPath(mMPhotoInfos.get(position).path));
                Bitmap bitmap = BitmapFactory.decodeFile(mMPhotoInfos.get(position).path);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                Bitmap bitmap1;
                float scale = (float) width / (float) height;
                if (width<mWidth/2){
                    width=mWidth/2;
                    if (scale<1){
                        height = (int) (width /scale);
                    }else {
                        height = (int) (width * scale);
                    }
                }else {
                    width=mWidth;
                    if (scale<1){
                        height = (int) (width /scale);
                    }else {
                        height = (int) (width * scale);
                    }
                }
                bitmap1 = ThumbnailUtils.extractThumbnail(bitmap, width, height);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap1);
                pv.setImageDrawable(bitmapDrawable);
                //pv.setImageDrawable(Drawable.createFromPath(mMPhotoInfos.get(position).path));
                container.addView(pv);
                return pv;
            }
        });
        vp.setCurrentItem(pos);
    }
}
