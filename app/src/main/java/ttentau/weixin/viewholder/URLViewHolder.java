package ttentau.weixin.viewholder;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ttentau.weixin.R;


/**
 * Created by suneee on 2016/8/16.
 */
public class URLViewHolder extends MyViewHolder{
    public LinearLayout ll_friend_share;
    /** 链接的图片 */
    public ImageView iv_friend_content_web_photo;
    /** 链接的标题 */
    public TextView tv_friend_content_web_title;

    public URLViewHolder(View itemView){
        super(itemView);
    }

    @Override
    public void initSubView(ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }

        viewStub.setLayoutResource(R.layout.viewstub_urlbody);
        View subViw  = viewStub.inflate();
        ll_friend_share = (LinearLayout) subViw.findViewById(R.id.ll_friend_share);
        if(ll_friend_share != null){
            iv_friend_content_web_photo = (ImageView) subViw.findViewById(R.id.iv_friend_content_web_photo);
            tv_friend_content_web_title = (TextView) subViw.findViewById(R.id.tv_friend_content_web_title);
        }
    }
}
