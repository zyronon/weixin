package com.example.lenovo.myapplication.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.actionbar.AddFriendActivity;
import com.example.lenovo.myapplication.adapter.ContactsFragmentAdapter;
import com.example.lenovo.myapplication.bean.Contacts;
import com.example.lenovo.myapplication.utils.DataUtil;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.LogUtil;
import com.example.lenovo.myapplication.utils.UIUtils;
import com.example.lenovo.myapplication.widgets.Qucikindex;
//import com.example.lenovo.myapplication.widgets.Qucikindex;

import java.util.ArrayList;
import java.util.Collections;


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
		mLv = mView.findViewById(R.id.lv);
		mQuick = mView.findViewById(R.id.quick);
		mTv = mView.findViewById(R.id.tv);

		return mView;
	}
	@Override
	public void initData() {
		friends = new ArrayList<>();
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
