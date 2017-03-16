package ttentau.weixin.uitls;

import android.content.Context;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.Contacts;
import ttentau.weixin.bean.TestFriendInfo;
import ttentau.weixin.db.FriendDb;


/**
 * Created by ttent on 2017/3/9.
 */

public class DataUtil {
	public static ArrayList<TestFriendInfo> getListInfo(Context context) {
		ArrayList<TestFriendInfo> tfili = new ArrayList<>();

		TestFriendInfo tfi = new TestFriendInfo();
		tfi.setPhoto(R.drawable.myic_1);
		tfi.setName("哈哈哈傻逼");
		tfi.setContent(
				"哈哈，18123456789,ChinaAr  http://www.ChinaAr.com;一个不错的VR网站。哈哈，ChinaAr  http://www.ChinaAr.com;一个不错的VR网站。哈哈，ChinaAr  http://www.ChinaAr.com;一个不错的VR网站。哈哈，ChinaAr  http://www.ChinaAr.com;一个不错的VR网站");
		tfi.setIsMy(1);
		tfi.setPraiseCount(1);
		tfi.setPraiseName("傻逼啊你SB|哎哟我草6666|你笑啥子|现在都3点钟了哎");
		tfi.setCommentCount(1);
		tfi.setCommetnContent("0~0~哥哥~你干哈啊~1~弟弟|1~0~哥哥~你干哈啊|2~0~哥哥~你是不是傻逼？");
		tfi.setDate("2017|3|10|2|46");
		tfi.setCompareData(201703100246L);

		TestFriendInfo tfi1 = new TestFriendInfo();
		tfi1.setIsMy(1);
		tfi1.setPhoto(R.drawable.myphoto);
		tfi1.setName("Excuse me?");
		tfi1.setContent(
				"VR（Virtual Reality，即虚拟现实，简称VR），是由美国VPL公司创建人拉尼尔（Jaron Lanier）在20世纪80年代初提出的。其具体内涵是：综合利用计算机图形系统和各种现实及控制等接口设备，在计算机上生成的、可交互的三维环境中提供沉浸感觉的技术。其中，计算机生成的、可交互的三维环境称为虚拟环境（即Virtual Environment，简称VE）。虚拟现实技术是一种可以创建和体验虚拟世界的计算机仿真系统的技术。它利用计算机生成一种模拟环境，利用多源信息融合的交互式三维动态视景和实体行为的系统仿真使用户沉浸到该环境中。");
		tfi1.setDate("2017|3|9|1|46");
		tfi1.setCompareData(201703090146L);

		TestFriendInfo tfi2 = new TestFriendInfo();
		tfi2.setPhoto(R.drawable.myicon);
		tfi2.setName("你瞅啥？？");
		tfi2.setContent("我勒个去");
		//tfi2.setImageCount(1);
		tfi2.setIsMy(1);
		//tfi2.setImagePath("R.drawable.a1_1|R.drawable.a1_2|R.drawable.a1_3|R.drawable.a1_4");
		tfi2.setDate("2016|3|10|2|46");
		tfi2.setCompareData(201603100246L);

		TestFriendInfo tfi3 = new TestFriendInfo();
		tfi3.setPhoto(R.drawable.myic_2);
		tfi3.setName("逗比");
		tfi3.setContent("百度一下，你就知道");
		tfi3.setDate("2011|5|10|2|46");
		tfi3.setCompareData(201105100246L);

		TestFriendInfo tfi4 = new TestFriendInfo();
		tfi4.setPhoto(R.drawable.myic_3);
		tfi4.setName("呵呵");
		tfi4.setContent("这是我用来测试的");
		tfi4.setDate("2017|3|7|2|46");
		tfi4.setCompareData(201703070246L);

		TestFriendInfo tfi5 = new TestFriendInfo();
		tfi5.setPhoto(R.drawable.myic_1);
		tfi5.setName("大sB");
		tfi5.setContent("因为我不知道写啥啊，写点啥好呢？哈哈，知道我有多无聊了吧");
		tfi5.setDate("2017|3|9|2|46");
		tfi5.setCompareData(201703090246L);

		TestFriendInfo tfi6 = new TestFriendInfo();
		tfi6.setPhoto(R.drawable.myic_1);
		tfi6.setName("大sB");
		tfi6.setContent("对方分享了一条链接给你，并骂了你一句傻逼");
		//tfi6.setWebContent(1);
		//tfi6.setWebContent_photo(R.drawable.myicon);
		//tfi6.setWebContent_content("愿下世不再做程序员");
		tfi6.setDate("2017|1|9|2|46");
		tfi6.setCompareData(201701090246l);

		TestFriendInfo tfi7 = new TestFriendInfo();
		tfi7.setPhoto(R.drawable.myphoto);
		tfi7.setName("你望洋啊");
		tfi7.setContent("这个项目写了我两个多月了，哎哎，当初就不该选择android啊，真是入门到住院");
		tfi7.setDate("2017|3|9|2|43");
		tfi7.setCompareData(201703090243l);

		FriendDb db = FriendDb.getInstence(context);
		db.insert(tfi);
		db.insert(tfi1);
		db.insert(tfi2);
		db.insert(tfi3);
		db.insert(tfi4);
		db.insert(tfi5);
		db.insert(tfi6);
		db.insert(tfi7);

		tfili.add(tfi);
		tfili.add(tfi1);
		tfili.add(tfi2);
		tfili.add(tfi3);
		tfili.add(tfi4);
		tfili.add(tfi5);
		tfili.add(tfi6);
		tfili.add(tfi7);
		return tfili;
	}
	public static void addContactsData(ArrayList<Contacts> friends){
		// 虚拟数据
		friends.add(new Contacts("李伟"));
		friends.add(new Contacts("张三"));
		friends.add(new Contacts("阿三"));
		friends.add(new Contacts("阿四"));
		friends.add(new Contacts("段誉"));
		friends.add(new Contacts("段正淳"));
		friends.add(new Contacts("张三丰"));
		friends.add(new Contacts("陈坤"));
		friends.add(new Contacts("林俊杰1"));
		friends.add(new Contacts("陈坤2"));
		friends.add(new Contacts("林俊杰a"));
		friends.add(new Contacts("张四"));
		friends.add(new Contacts("林俊杰"));
		friends.add(new Contacts("赵四"));
		friends.add(new Contacts("杨坤"));
		friends.add(new Contacts("赵子龙"));
		friends.add(new Contacts("子龙"));
		friends.add(new Contacts("龙"));
		friends.add(new Contacts("一子龙"));
		friends.add(new Contacts("上子龙"));
		friends.add(new Contacts("赵有子龙"));
		friends.add(new Contacts("不子龙"));
		friends.add(new Contacts("中子龙"));
		friends.add(new Contacts("杨坤1"));
		friends.add(new Contacts("李伟1"));
		friends.add(new Contacts("人宋江"));
		friends.add(new Contacts("灶宋江1"));
		friends.add(new Contacts("仍李伟3"));
		friends.add(new Contacts("二李伟3"));
		friends.add(new Contacts("李呻伟3"));
		friends.add(new Contacts("入李伟3"));
		friends.add(new Contacts("写好伟3"));
	}
}
