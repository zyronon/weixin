package ttentau.weixin.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ttentau.weixin.R;
import ttentau.weixin.bean.Friend;
import ttentau.weixin.ui.activity.AddFriendActivity;
import ttentau.weixin.ui.activity.UserInfoActivity;
import ttentau.weixin.ui.view.Qucikindex;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class ContactsFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private ListView mLv;
    private Qucikindex mQuick;
    private TextView mTv;
    private ArrayList<Friend> friends;

    @Override
    public View initView() {
        mView = UIUtils.inflate(R.layout.fragment_contacts);
        mLv = (ListView) mView.findViewById(R.id.lv);
        mQuick = (Qucikindex) mView.findViewById(R.id.quick);
        mTv = (TextView) mView.findViewById(R.id.tv);

        return mView;
    }
    @Override
    public void initData() {
        friends = new ArrayList<Friend>();
        fillList();
        Collections.sort(friends);
        View view = View.inflate(UIUtils.getContext(), R.layout.head, null);
        view.findViewById(R.id.ll).setOnClickListener(this);
        view.findViewById(R.id.ll1).setOnClickListener(this);
        view.findViewById(R.id.ll2).setOnClickListener(this);
        view.findViewById(R.id.ll3).setOnClickListener(this);
        View secenod_head = View.inflate(UIUtils.getContext(), R.layout.second_head, null);
        secenod_head.findViewById(R.id.xin).setOnClickListener(this);
        secenod_head.findViewById(R.id.content).setOnClickListener(this);
        mLv.addHeaderView(view);
        mLv.addHeaderView(secenod_head);
        mLv.setAdapter(new MyAdapter());
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = friends.get(position-2);
                //UIUtils.Toast(friend.getName());
                Log.e("tag",friend.getName());
                Intent intent = new Intent(UIUtils.getContext(), UserInfoActivity.class);
                intent.putExtra("ContactsFragment_user_name",friend.getName());
                intent.putExtra("ContactsFragment_userChatid","contacts"+position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
            }
        });

        mQuick.setOnTextChangedLisenter(new Qucikindex.onTextChangedLisenter() {
            @Override
            public void onTextChanged(String value) {
                mTv.setText(value);
                mTv.setVisibility(View.VISIBLE);
                if (value.equals("↑")){
                    mLv.setSelection(0);
                }else if (value.equals("☆")){
                    mLv.setSelection(1);
                }else if (value.equals("#")){
                    mLv.setSelection(mLv.getCount());
                } else {
                    for (int i = 0; i < friends.size(); i++) {
                        String first_word = friends.get(i).getPinyin().charAt(0) + "";
                        if (first_word.equals(value)){
                            mLv.setSelection(i+2);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onDown(String value) {
                mTv.setText(value);
                mTv.setVisibility(View.VISIBLE);
                if (value.equals("↑")){
                    mLv.setSelection(0);
                }else if (value.equals("☆")){
                    mLv.setSelection(1);
                }else if (value.equals("#")){
                    mLv.setSelection(mLv.getCount());
                } else {
                    for (int i = 0; i < friends.size(); i++) {
                        String first_word = friends.get(i).getPinyin().charAt(0) + "";
                        if (first_word.equals(value)){
                            mLv.setSelection(i+2);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onUP(String value) {
                mTv.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll:
                Intent intent = new Intent(UIUtils.getContext(), AddFriendActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return friends.size();
        }

        @Override
        public Friend getItem(int position) {
            return friends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=View.inflate(UIUtils.getContext(),R.layout.item,null);
            }
            ViewHolder holder = ViewHolder.getInstence(convertView);
            Friend friend = friends.get(position);
            if (position>0){
                Friend lastFriend = friends.get(position - 1);
                String lastPinyin1 = lastFriend.getPinyin().charAt(0)+"";
                String pinyin = friend.getPinyin().charAt(0)+"";
                if (lastPinyin1.equals(pinyin)){
                    holder.mFirst_word.setVisibility(View.GONE);
//                    holder.mFirst_word.setText(friend.getPinyin());
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
          /*  holder.mFirst_word.setOnClickListener(null);
            holder.mLll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),"我点击了"+position,Toast.LENGTH_SHORT).show();
                }
            });*/
            return convertView;
        }
    }
    static class ViewHolder {

        private final TextView mFirst_word;
        private final TextView mName;
        private final LinearLayout mLll;

        private ViewHolder(View convertView){
            mFirst_word = (TextView) convertView.findViewById(R.id.first_word);
            mName = (TextView) convertView.findViewById(R.id.name);
            mLll = (LinearLayout) convertView.findViewById(R.id.lll);
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
    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("子龙"));
        friends.add(new Friend("龙"));
        friends.add(new Friend("一子龙"));
        friends.add(new Friend("上子龙"));
        friends.add(new Friend("赵有子龙"));
        friends.add(new Friend("不子龙"));
        friends.add(new Friend("中子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("人宋江"));
        friends.add(new Friend("灶宋江1"));
        friends.add(new Friend("仍李伟3"));
        friends.add(new Friend("二李伟3"));
        friends.add(new Friend("李呻伟3"));
        friends.add(new Friend("入李伟3"));
        friends.add(new Friend("写好伟3"));
    }
}
