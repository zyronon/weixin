package ttentau.weixin.adapter.viewpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.found.PhotoInfoActivity;
import ttentau.weixin.bean.ImageModel;
import ttentau.weixin.uitls.SystemBarTintManager;
import ttentau.weixin.uitls.UIUtils;

import static ttentau.weixin.R.id.iv;

/**
 * Created by ttent on 2017/3/24.
 */

public class PhotoDetailsAdapter extends PagerAdapter implements View.OnClickListener {
    private List<ImageModel> mAllImages;
    private Activity mActivity;
    private RelativeLayout mRl_customActionbar;
    private RelativeLayout rl_container_bottom;
    private View content;
    private RelativeLayout.LayoutParams mLp;
    private SystemBarTintManager tintManager;

    public PhotoDetailsAdapter(PhotoInfoActivity activity, List<ImageModel> images, List<ImageModel> selectedImages){
        this.mAllImages=images;
        mActivity=activity;
    }
    public void setView(RelativeLayout mRl_customActionbar,RelativeLayout rl_container_bottom,View content,SystemBarTintManager tintManager){
        this.mRl_customActionbar=mRl_customActionbar;
        this.rl_container_bottom=rl_container_bottom;
        this.content=content;
        this.tintManager=tintManager;
        mLp = (RelativeLayout.LayoutParams) mRl_customActionbar.getLayoutParams();
    }
    @Override
    public int getCount() {
        return mAllImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String path = mAllImages.get(position).getPath();
        View inflate = View.inflate(mActivity, R.layout.item_vp_photoinfo, null);
        PhotoView view = (PhotoView) inflate.findViewById(R.id.iv);
        view.enable();
        view.setMaxScale(3);
        view.setOnClickListener(this);
        Glide.with(mActivity).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case iv:
                if (mRl_customActionbar.getVisibility()==View.VISIBLE){
                    mRl_customActionbar.setAnimation(AnimationUtils.loadAnimation(mActivity,R.anim.photoinfo_top_exit));
                    rl_container_bottom.setAnimation(AnimationUtils.loadAnimation(mActivity,R.anim.photoinfo_bottom_exit));
                    mRl_customActionbar.setVisibility(View.INVISIBLE);
                    rl_container_bottom.setVisibility(View.INVISIBLE);
                    tintManager.setStatusBarTintResource(R.color.transparent);
                    content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                }else {
                    mRl_customActionbar.setAnimation(AnimationUtils.loadAnimation(mActivity,R.anim.photoinfo_top_enter));
                    rl_container_bottom.setAnimation(AnimationUtils.loadAnimation(mActivity,R.anim.photoinfo_bottom_enter));
                    mRl_customActionbar.setVisibility(View.VISIBLE);
                    rl_container_bottom.setVisibility(View.VISIBLE);
                    tintManager.setStatusBarTintResource(R.color.blackwx);
                    content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                mLp.topMargin= UIUtils.getStatusBarHeight();
                mRl_customActionbar.setLayoutParams(mLp);
                break;
        }
    }
}
