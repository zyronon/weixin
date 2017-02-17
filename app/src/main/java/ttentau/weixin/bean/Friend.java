package ttentau.weixin.bean;

import ttentau.weixin.uitls.PinYinUtil;

public class Friend implements Comparable<Friend>{
	private String name;
	private String pinyin;

	//使用成员变量生成构造方法：alt+shift+s->o
	public Friend(String name) {
		super();
		this.name = name;
		
		//一开始就转化好拼音
		setPinyin(PinYinUtil.getPinyin(name));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Friend another) {
		return getPinyin().compareTo(another.getPinyin());
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	
}
