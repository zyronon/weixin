package ttentau.weixin.bean;

/**
 * 
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:45:04 
*
 */
public class User {
	private String id;
	private String name;
	private String photo;

	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public User(String id, String name, String photo) {
		this.id = id;
		this.name = name;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getPhoto() {
		return photo;
	}
}
