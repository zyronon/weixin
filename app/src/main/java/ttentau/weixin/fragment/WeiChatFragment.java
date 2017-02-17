package ttentau.weixin.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.MsgList;
import ttentau.weixin.db.SaveMsgList;
import ttentau.weixin.ui.activity.ChatActivity;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class WeiChatFragment extends BaseFragment {

	private View mView;
	private ListView lv_weichat;
	private ArrayList<MsgList> mMsgLists;

    @Override
    public View initView() {
		mView = UIUtils.inflate(R.layout.fragment_weichat);
		lv_weichat = (ListView) mView.findViewById(R.id.lv_weichat);
        return mView;
    }
    @Override
	public void initData() {
		//mMsgLists = new ArrayList<>();
		SaveMsgList msgList = SaveMsgList.getInstence(UIUtils.getContext());
		mMsgLists = msgList.query();
		lv_weichat.setAdapter(new Myadpater());
		lv_weichat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(UIUtils.getContext(), ChatActivity.class);
				MsgList msgList1 = mMsgLists.get(position);
				String chatId = msgList1.getChatId();
				intent.putExtra("WeiChatFragment_userid",chatId);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
			}
		});
	}
	private class Myadpater extends BaseAdapter {
		private android.widget.ImageView ivuserphoto;
		private android.widget.TextView tvusername;
		private android.widget.TextView tvusercontent;
		private android.widget.TextView tvuserlasttime;

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
			View view = UIUtils.inflate(R.layout.lv_item_weichat);
			this.tvuserlasttime = (TextView) view.findViewById(R.id.tv_user_last_time);
			this.tvusercontent = (TextView) view.findViewById(R.id.tv_user_content);
			this.tvusername = (TextView) view.findViewById(R.id.tv_user_name);
			this.ivuserphoto = (ImageView) view.findViewById(R.id.iv_user_photo);
            MsgList msgList = mMsgLists.get(position);
            tvusername.setText(msgList.getChatId());
            tvusercontent.setText(msgList.getLastContent());
            //   view.setBackgroundResource(R.drawable.chat_selector);
            return view;
        }
    }
}
