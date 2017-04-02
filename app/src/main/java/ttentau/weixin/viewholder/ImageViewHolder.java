package ttentau.weixin.viewholder;

import android.view.View;
import android.view.ViewStub;

import ttentau.weixin.R;
import ttentau.weixin.widgets.MyGridView;


/**
 * Created by suneee on 2016/8/16.
 */
public class ImageViewHolder extends MyViewHolder {
    /** 图片*/
    public MyGridView gv_friend_content_photos;

    public ImageViewHolder(View itemView){
        super(itemView);
    }

    @Override
    public void initSubView( ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.viewstub_imgbody);
        View subView = viewStub.inflate();
        MyGridView gv_friend_content_photos = (MyGridView) subView.findViewById(R.id.gv_friend_content_photos);
        if(gv_friend_content_photos != null){
            this.gv_friend_content_photos = gv_friend_content_photos;
        }
    }
}
