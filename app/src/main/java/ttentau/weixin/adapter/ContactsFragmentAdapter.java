package ttentau.weixin.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.activity.UserInfoActivity;
import ttentau.weixin.bean.Contacts;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/14.
 */

public class ContactsFragmentAdapter extends BaseAdapter {
    private ArrayList<Contacts> friends;
    private Activity mActivity;
    public ContactsFragmentAdapter(ArrayList<Contacts> friends,Activity mActivity){
        this.friends=friends;
        this.mActivity=mActivity;
    }
    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Contacts getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(UIUtils.getContext(), R.layout.lv_item_contacts_fragment,null);
        }
        ViewHolder holder = ViewHolder.getInstence(convertView);
        Contacts friend = friends.get(position);
        if (position>0){
            Contacts lastFriend = friends.get(position - 1);
            String lastPinyin1 = lastFriend.getPinyin().charAt(0)+"";
            String pinyin = friend.getPinyin().charAt(0)+"";
            if (lastPinyin1.equals(pinyin)){
                holder.mFirst_word.setVisibility(View.GONE);
//                    holder.mFirst_word.setText(ic_friend_friend.getPinyin());
                holder.mName.setText(friend.getName());
            }else {
                holder.mFirst_word.setVisibility(View.VISIBLE);
                holder.mFirst_word.setText(friend.getPinyin().charAt(0)+"");
                holder.mName.setText(friend.getName());
            }
        }else {
            holder.mFirst_word.setVisibility(View.VISIBLE);
            holder.mFirst_word.setText(friend.getPinyin().charAt(0)+"");
            holder.mName.setText(friend.getName());
        }
            holder.mFirst_word.setOnClickListener(null);
            holder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contacts friend = friends.get(position);
                    Intent intent =new Intent(UIUtils.getContext(),UserInfoActivity.class);
                    intent.putExtra("ContactsFragment_user_name",friend.getName());
                    intent.putExtra("ContactsFragment_userChatid",friend.getName()+position);
                    IntentUtils.startActivity(mActivity,intent);
                }
            });
        return convertView;
    }
    static class ViewHolder {

        private final TextView mFirst_word;
        private final TextView mName;
        private final LinearLayout ll_container;

        private ViewHolder(View convertView){
            mFirst_word = (TextView) convertView.findViewById(R.id.first_word);
            mName = (TextView) convertView.findViewById(R.id.name);
            ll_container = (LinearLayout) convertView.findViewById(R.id.ll_container);
        }
        public static ViewHolder getInstence(View convertView){
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder==null){
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}

