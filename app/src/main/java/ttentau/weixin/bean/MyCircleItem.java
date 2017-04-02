package ttentau.weixin.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 这是主Activity
 * Created by ttent on 2017/3/9.
 */

public class MyCircleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int TYPE_URL = 1;
    public final static int TYPE_IMG = 2;
    public final static int TYPE_VIDEO =3;
    public final static int TYPE_NORMAL =4;

    private int itemId;
    private User mUser;
    private String content;
    private boolean contentIsExpand=false;
    private boolean isPraise=false;
    private boolean isMy=false;
    private int type=4;//1:链接  2:图片 3:视频
    //外链
    private String linkImg;
    private String linkTitle;
    private String createTime;
    private long compareData;
    //赞
    private ArrayList<FavortItem> favorters;
    //评论
    private ArrayList<CommentItem> comments;
    //图片
    private ArrayList<PhotoInfoMy> photos;

    public MyCircleItem() {
    }

    public static int getTypeUrl() {
        return TYPE_URL;
    }

    public static int getTypeImg() {
        return TYPE_IMG;
    }

    public static int getTypeVideo() {
        return TYPE_VIDEO;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isContentIsExpand() {
        return contentIsExpand;
    }

    public void setContentIsExpand(boolean contentIsExpand) {
        this.contentIsExpand = contentIsExpand;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setIsPraise(boolean praise) {
        isPraise = praise;
    }

    public boolean isMy() {
        return isMy;
    }

    public void setIsMy(boolean my) {
        isMy = my;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getCompareData() {
        return compareData;
    }

    public void setCompareData(long compareData) {
        this.compareData = compareData;
    }

    public ArrayList<FavortItem> getFavorters() {
        return favorters;
    }

    public void setFavorters(ArrayList<FavortItem> favorters) {
        this.favorters = favorters;
    }

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
    }


    public ArrayList<PhotoInfoMy> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PhotoInfoMy> photos) {
        this.photos = photos;
    }
}