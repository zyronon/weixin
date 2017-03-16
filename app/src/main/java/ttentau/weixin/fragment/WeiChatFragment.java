package ttentau.weixin.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.activity.ChatActivity;
import ttentau.weixin.adapter.WeiChatFragmentAdapter;
import ttentau.weixin.bean.MsgList;
import ttentau.weixin.db.SaveMsgList;
import ttentau.weixin.uitls.IntentUtils;
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
		SaveMsgList msgList = SaveMsgList.getInstence(UIUtils.getContext());
		mMsgLists = msgList.query();
		lv_weichat.setAdapter(new WeiChatFragmentAdapter(mMsgLists));
		lv_weichat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=IntentUtils.getIntent(getActivity(),ChatActivity.class);
				String chatId = mMsgLists.get(position).getChatId();
				intent.putExtra("WeiChatFragment_userid",chatId);
				IntentUtils.startActivity(getActivity(),intent);;
			}
		});
	}
}
