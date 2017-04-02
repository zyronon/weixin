package ttentau.weixin.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.bean.FavortItem;
import ttentau.weixin.global.WeixinApplication;
import ttentau.weixin.uitls.UIUtils;


/**
 * Created by ttent on 2017/3/8.
 */

public class MyPraiseTextView extends TextView {
    private int itemColor=getResources().getColor(R.color.bluewx);
    private int itemSelectorColor=getResources().getColor(R.color.text_light);
    private List<FavortItem> datas;
    private OnItemClickListener onItemClickListener;

    public MyPraiseTextView(Context context) {
        super(context);
    }

    public MyPraiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPraiseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public List<FavortItem> getDatas() {
        return datas;
    }
    public void setDatas(List<FavortItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(datas != null && datas.size() > 0){
            //添加点赞图标
            builder.append(setImageSpan());
            FavortItem item = null;
            for (int i=0; i<datas.size(); i++){
                item = datas.get(i);
                if(item != null){
                    builder.append(setClickableSpan(item.getUser().getName(), i));
                    if(i != datas.size()-1){
                        builder.append(", ");
                    }
                }
            }
        }
        setText(builder);
        setMovementMethod(new CircleMovementMethod(itemSelectorColor));
    }

    private SpannableString setClickableSpan(String textStr, final int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor){
                                    @Override
                                    public void onClick(View widget) {
                                        if(onItemClickListener!=null){
                                            onItemClickListener.onClick(position);
                                        }
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    private SpannableString setImageSpan() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_friend_item_praise_blue);
        Bitmap bitmap1 = ThumbnailUtils.extractThumbnail(bitmap, UIUtils.dip2px(17), UIUtils.dip2px(17));

        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(WeixinApplication.getContext(), bitmap1),
                0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }
    public static interface OnItemClickListener{
        public void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
