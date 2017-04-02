package ttentau.weixin.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ttentau.weixin.R;
import ttentau.weixin.activity.actionbar.AddFriendActivity;
import ttentau.weixin.adapter.ContactsFragmentAdapter;
import ttentau.weixin.bean.Contacts;
import ttentau.weixin.widgets.Qucikindex;
import ttentau.weixin.uitls.DataUtil;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class ContactsFragment extends BaseFragment implements View.OnClickListener {

	private View mView;
	private ListView mLv;
	private Qucikindex mQuick;
	private TextView mTv;
	private ArrayList<Contacts> friends;
	private View mView1;
	private View mSecenod_head;

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
		friends = new ArrayList<Contacts>();
		DataUtil.addContactsData(friends);
		Collections.sort(friends);

		mView1 = View.inflate(UIUtils.getContext(), R.layout.head, null);
		mView1.findViewById(R.id.ll).setOnClickListener(this);
		mView1.findViewById(R.id.ll1).setOnClickListener(this);
		mView1.findViewById(R.id.ll2).setOnClickListener(this);
		mView1.findViewById(R.id.ll3).setOnClickListener(this);
		mSecenod_head = View.inflate(UIUtils.getContext(), R.layout.second_head, null);
		mSecenod_head.findViewById(R.id.xin).setOnClickListener(this);
		mSecenod_head.findViewById(R.id.content).setOnClickListener(this);

		mLv.addHeaderView(mView1);
		mLv.addHeaderView(mSecenod_head);

		mLv.setAdapter(new ContactsFragmentAdapter(friends,getActivity()));

		mQuick.setOnTextChangedLisenter(new Qucikindex.onTextChangedLisenter() {
			@Override
			public void onTextChanged(String value) {
				mTv.setText(value);
				mTv.setVisibility(View.VISIBLE);
				if (value.equals("↑")) {
					mLv.setSelection(0);
				} else if (value.equals("☆")) {
					mLv.setSelection(1);
				} else if (value.equals("#")) {
					mLv.setSelection(mLv.getCount());
				} else {
					for (int i = 0; i < friends.size(); i++) {
						String first_word = friends.get(i).getPinyin().charAt(0) + "";
						if (first_word.equals(value)) {
							mLv.setSelection(i + 2);
							break;
						}
					}
				}
			}
			@Override
			public void onDown(String value) {
				mTv.setText(value);
				mTv.setVisibility(View.VISIBLE);
				if (value.equals("↑")) {
					mLv.setSelection(0);
				} else if (value.equals("☆")) {
					mLv.setSelection(1);
				} else if (value.equals("#")) {
					mLv.setSelection(mLv.getCount());
				} else {
					for (int i = 0; i < friends.size(); i++) {
						String first_word = friends.get(i).getPinyin().charAt(0) + "";
						if (first_word.equals(value)) {
							mLv.setSelection(i + 2);
							break;
						}
					}
				}
			}
			@Override
			public void onUP() {
				mTv.setVisibility(View.INVISIBLE);
			}
		});

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll :
				IntentUtils.startActivity(getActivity(), AddFriendActivity.class);
				break;
		}
	}
}
