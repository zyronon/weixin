package ttentau.weixin.bean;

import java.io.Serializable;

/**
 * 
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:45:04 
*
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String photo="-1";

	public int getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(int photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	private int photoUrl;

	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public User(String id, String name, String photo) {
		this.id = id;
		this.name = name;
		this.photo = photo;
	}
	public User(String id, String name, int photoUrl) {
		this.id = id;
		this.name = name;
		this.photoUrl = photoUrl;
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
