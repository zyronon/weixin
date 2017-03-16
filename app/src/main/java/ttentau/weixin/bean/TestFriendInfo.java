package ttentau.weixin.bean;

import java.io.Serializable;

/**
 * 这是主Activity
 * Created by ttent on 2017/3/9.
 */

public class TestFriendInfo implements Serializable {
    private int photo;
    private String name;
    private String content;

    private int contentIsExpand=-1;
    private int isPraise=-1;
    private int isMy=-1;
    //外链
    private int webContent=-1;
    private String webContent_content;
    private String webContent_photo;

    private String date;
    private long compareData;

    //赞
    private int praiseCount =-1;
    private String praiseName;

    //评论
    private int commentCount=-1;
    private String commetnContent;

    private int imageCount=-1;
    private String imagePath;

    public TestFriendInfo(){
    }

    public TestFriendInfo(int photo, String name, String content, int contentIsExpand, int isPraise, int isMy, int webContent, String webContent_content, String webContent_photo, String date, int laudCount, String launName, int commentCount, String commetnContent, int imageCount, String imagePath) {
        this.photo = photo;
        this.name = name;
        this.content = content;
        this.contentIsExpand = contentIsExpand;
        this.isPraise = isPraise;
        this.isMy = isMy;
        this.webContent = webContent;
        this.webContent_content = webContent_content;
        this.webContent_photo = webContent_photo;
        this.date = date;
        this.praiseCount = laudCount;
        this.praiseName = launName;
        this.commentCount = commentCount;
        this.commetnContent = commetnContent;
        this.imageCount = imageCount;
        this.imagePath = imagePath;
    }

    public TestFriendInfo(int photo, String name, String content, int contentIsExpand, int isPraise, int isMy, int webContent, String webContent_photo, int laudCount, int commentCount, int imageCount) {
        this.photo = photo;
        this.name = name;
        this.content = content;
        this.contentIsExpand = contentIsExpand;
        this.isPraise = isPraise;
        this.isMy = isMy;
        this.webContent = webContent;
        this.webContent_photo = webContent_photo;
        this.praiseCount = laudCount;
        this.commentCount = commentCount;
        this.imageCount = imageCount;
    }

    public TestFriendInfo(int photo, String name, String content) {
        this.photo = photo;
        this.name = name;
        this.content = content;
    }
    public long getCompareData() {
        return compareData;
    }

    public void setCompareData(long compareData) {
        this.compareData = compareData;
    }

    public int getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getContentIsExpand() {
        return contentIsExpand;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public int getIsMy() {
        return isMy;
    }

    public int getWebContent() {
        return webContent;
    }

    public String getWebContent_content() {
        return webContent_content;
    }

    public String getWebContent_photo() {
        return webContent_photo;
    }

    public String getDate() {
        return date;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public String getPraiseName() {
        return praiseName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getCommetnContent() {
        return commetnContent;
    }

    public int getImageCount() {
        return imageCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentIsExpand(int contentIsExpand) {
        this.contentIsExpand = contentIsExpand;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }

    public void setIsMy(int isMy) {
        this.isMy = isMy;
    }

    public void setWebContent(int webContent) {
        this.webContent = webContent;
    }

    public void setWebContent_content(String webContent_content) {
        this.webContent_content = webContent_content;
    }

    public void setWebContent_photo(String webContent_photo) {
        this.webContent_photo = webContent_photo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public void setPraiseName(String praiseName) {
        this.praiseName = praiseName;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setCommetnContent(String commetnContent) {
        this.commetnContent = commetnContent;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
