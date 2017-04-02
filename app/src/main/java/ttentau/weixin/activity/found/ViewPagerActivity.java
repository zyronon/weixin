package ttentau.weixin.activity.found;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.bean.PhotoInfoMy;
import ttentau.weixin.uitls.UIUtils;


/**
 * 这是图片放大activity
 * Created by ttent on 2017/3/9.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private int mWidth;
    private int mHeight;
    private List<PhotoInfoMy> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager vp = new ViewPager(this);
        setContentView(vp);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        mPhotos = (List<PhotoInfoMy>) intent.getSerializableExtra("imageInfo");
        final int pos = intent.getIntExtra("pos",0);
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPhotos.size();
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
                PhotoInfoMy infoMy = mPhotos.get(position);
                View inflate = UIUtils.inflate(R.layout.item_vp_photoinfo);
                PhotoView view = (PhotoView) inflate.findViewById(R.id.iv);
                view.enable();
                view.setMaxScale(3);
                Glide.with(ViewPagerActivity.this).load(infoMy.path).into(view);
                container.addView(inflate);
                return inflate;
            }
        });
        vp.setCurrentItem(pos);
    }
}
