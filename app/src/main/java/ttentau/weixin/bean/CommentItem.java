package ttentau.weixin.bean;

import java.io.Serializable;

/**
 * 
* @ClassName: CommentItem 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:44:38 
*
 */
public class CommentItem implements Serializable{

	private String id;
	private User user;
	private User toReplyUser;
	private String content;

	public CommentItem(String id, User user, String content, User toReplyUser) {
		this.id = id;
		this.user = user;
		this.content = content;
		this.toReplyUser = toReplyUser;
	}

	public CommentItem(String id, User user, String content) {
		this.content = content;
		this.user = user;
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getToReplyUser() {
		return toReplyUser;
	}
	public void setToReplyUser(User toReplyUser) {
		this.toReplyUser = toReplyUser;
	}
	
}
