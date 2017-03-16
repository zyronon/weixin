package ttentau.weixin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.MsgList;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/14.
 */

public class WeiChatFragmentAdapter extends BaseAdapter {
    private android.widget.ImageView ivuserphoto;
    private android.widget.TextView tvusername;
    private android.widget.TextView tvusercontent;
    private android.widget.TextView tvuserlasttime;
    private ArrayList<MsgList> mMsgLists;

    public WeiChatFragmentAdapter(ArrayList<MsgList> mMsgLists){
        this.mMsgLists=mMsgLists;
    }

    @Override
    public int getCount() {
        return mMsgLists.size();
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
        View view = UIUtils.inflate(R.layout.lv_item_weichat_fragment);
        this.tvuserlasttime = (TextView) view.findViewById(R.id.tv_user_last_time);
        this.tvusercontent = (TextView) view.findViewById(R.id.tv_user_content);
        this.tvusername = (TextView) view.findViewById(R.id.tv_user_name);
        this.ivuserphoto = (ImageView) view.findViewById(R.id.iv_user_photo);
        MsgList msgList = mMsgLists.get(position);
        tvusername.setText(msgList.getChatId());
        tvusercontent.setText(msgList.getLastContent());
        //   view.setBackgroundResource(R.drawable.main_selector);
        return view;
    }
}
