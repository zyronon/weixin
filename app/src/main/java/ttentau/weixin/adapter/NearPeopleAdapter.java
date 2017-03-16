package ttentau.weixin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.Contacts;
import ttentau.weixin.uitls.UIUtils;

import static ttentau.weixin.R.id.iv_user_photo;

/**
 * Created by ttent on 2017/3/15.
 */
public class NearPeopleAdapter extends BaseAdapter {

    private ArrayList<Contacts> friends;

    public NearPeopleAdapter(ArrayList<Contacts> friends){
        this.friends=friends;
    }
    @Override
    public int getCount() {
        return friends.size();
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
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.lv_item_weichat_fragment, null);
        }
        ViewHolder holder =ViewHolder.getInstence(convertView);
        Contacts friend = friends.get(position);
        holder.tvusername.setText(friend.getName());
        holder.tvusercontent.setText("43公里以内-陆家嘴");
        holder.tvuserlasttime.setText("加个好友呗！");
        return convertView;
    }
    static class ViewHolder {
        public android.widget.ImageView ivuserphoto;
        public android.widget.TextView tvusername;
        public android.widget.TextView tvusercontent;
        public android.widget.TextView tvuserlasttime;

        private ViewHolder(View convertView) {
            this.tvuserlasttime = (TextView) convertView.findViewById(R.id.tv_user_last_time);
            this.tvusercontent = (TextView) convertView.findViewById(R.id.tv_user_content);
            this.tvusername = (TextView) convertView.findViewById(R.id.tv_user_name);
            this.ivuserphoto = (ImageView) convertView.findViewById(iv_user_photo);
        }

        public static ViewHolder getInstence(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
