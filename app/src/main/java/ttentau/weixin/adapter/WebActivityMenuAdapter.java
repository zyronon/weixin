package ttentau.weixin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/15.
 */
public class WebActivityMenuAdapter extends BaseAdapter {
    private int[] resources=new int[]{R.drawable.sic1,R.drawable.sic2,R.drawable.sic3,
            R.drawable.sic4,R.drawable.sic5,
            R.drawable.sic6,R.drawable.sic7,
            R.drawable.sic8,R.drawable.sic9,
            R.drawable.sic10,R.drawable.sic11};

    private String[] text={"发送给朋友","分享到朋友圈","收藏","搜索页面内容",
            "复制链接","查看公众号","在聊天中置顶","在浏览器打开","调整字体","刷新","设拆"};
    @Override
    public int getCount() {
        return 11;
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
        View view =UIUtils.inflate( R.layout.gridview_item);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        iv.setImageResource(resources[position]);
        tv.setText(text[position]);
        return view;
    }
}
