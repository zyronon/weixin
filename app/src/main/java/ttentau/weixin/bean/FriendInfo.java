package ttentau.weixin.bean;

import java.util.Arrays;

import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/27.
 */

public class FriendInfo {
    private int photo;
    private String name;
    private String content;
    //外链
    private int webContent=0;
    private String webContent_content;
    private int webContent_photo;

    private String date;
    //赞
    private int laudCount=0;
    private String[] launName=null;

    //评论
    private int commentCount=0;
    private String[] commentName=null;
    private String[] commetnContent=null;

    private int imageCount=0;
    private String[] imagePath=null;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWebContent() {
        return webContent;
    }

    public void setWebContent(int webContent) {
        this.webContent = webContent;
    }

    public String getWebContent_content() {
        return webContent_content;
    }

    public void setWebContent_content(String webContent_content) {
        this.webContent_content = webContent_content;
    }

    public int getWebContent_photo() {
        return webContent_photo;
    }

    public void setWebContent_photo(int webContent_photo) {
        this.webContent_photo = webContent_photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLaudCount() {
        return laudCount;
    }

    public void setLaudCount(int laudCount) {
        this.laudCount = laudCount;
    }

    public String[] getLaunName() {
        return launName;
    }

    public void setLaunName(String launName) {
        if (UIUtils.isEmpty(launName)){
            return;
        }
        String[] split = launName.split("\\|");
        this.launName = split;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String[] getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        if (UIUtils.isEmpty(commentName)){
            return;
        }
        String[] split = commentName.split("\\|");
        this.commentName = split;
    }

    public String[] getCommetnContent() {
        return commetnContent;
    }

    public void setCommetnContent(String commetnContent) {
        if (UIUtils.isEmpty(commetnContent)){
            return;
        }
        String[] split = commetnContent.split("\\|");
        this.commetnContent = split;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String[] getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        if (UIUtils.isEmpty(imagePath)){
            return;
        }
        String[] split = imagePath.split("\\|");
        this.imagePath = split;
    }

    @Override
    public String toString() {
        return "FriendInfo{" +
                "photo=" + photo +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", webContent=" + webContent +
                ", webContent_content='" + webContent_content + '\'' +
                ", webContent_photo=" + webContent_photo +
                ", date=" + date +
                ", laudCount=" + laudCount +
                ", launName=" + Arrays.toString(launName) +
                ", commentCount=" + commentCount +
                ", commentName=" + Arrays.toString(commentName) +
                ", commetnContent=" + Arrays.toString(commetnContent) +
                ", imageCount=" + imageCount +
                ", imagePath=" + Arrays.toString(imagePath) +
                '}';
    }
}
