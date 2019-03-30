package com.example.lenovo.myapplication.fragment;

import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.utils.UIUtils;

/**
 * Created by ttent on 2017/2/5.
 */

public class WeiChatFragment extends BaseFragment {

	private View mView;
	private ListView lv_weichat;
//	private ArrayList<MsgList> mMsgLists;
	private String[] mUrl = {"https://m.4399.cn", "WebView"};
	private WebView mWeb;

	@Override
    public View initView() {
		mView = UIUtils.inflate(R.layout.fragment_weichat);
		mWeb = mView.findViewById(R.id.webview);
//		lv_weichat = (ListView) mView.findViewById(R.id.lv_weichat);
        return mView;
    }
    @Override
	public void initData() {
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        mWeb.loadUrl(mUrl[0]);
        mWeb.loadUrl("file:///android_asset/wechat.html");

//		SaveMsgList msgList = SaveMsgList.getInstence(UIUtils.getContext());
//		mMsgLists = msgList.query();
//		lv_weichat.setAdapter(new WeiChatFragmentAdapter(mMsgLists));
//		lv_weichat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent intent=IntentUtils.getIntent(getActivity(),ChatActivity.class);
//				String chatId = mMsgLists.get(position).getChatId();
//				intent.putExtra("WeiChatFragment_userid",chatId);
//				IntentUtils.startActivity(getActivity(),intent);;
//			}
//		});
	}
}
