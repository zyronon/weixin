package ttentau.weixin.bean;

import java.io.Serializable;
import java.util.ArrayList;

import ttentau.weixin.uitls.UIUtils;


/**
 * 这是主Activity
 * Created by ttent on 2017/3/9.
 */

public class TestFriendInfo implements Serializable {

    private String id;
    private int photo;
    private String name;
    private String content;

    private int contentIsExpand=-1;
    private int isPraise=-1;
    private int isMy=-1;
    //外链
    private String webContent_content;
    private String webContent_photo;
    private String imagePath;

    private String date;
    private long compareData;

    private int type=-1;
    //赞
    private int praiseCount =-1;

    private String praiseName;
    //评论
    private int commentCount=-1;

    private String commetnContent;

    public TestFriendInfo(){
    }
    public void setCommentList(ArrayList<CommentItem> list){
        for (int i = 0; i < list.size(); i++) {
            CommentItem ci = list.get(i);
            if (ci!=null){
                User toReplyUser = ci.getToReplyUser();
                User user = ci.getUser();
                if (toReplyUser!=null){
                    commetnContent+="|~"+ci.getId()+"~"+user.getId()+"~"+user.getName()+"~"+user.getPhoto()+
                            "~"+ci.getContent()+"~"+toReplyUser.getId()+"~"+toReplyUser.getName()+"~"+toReplyUser.getPhoto();
                }else {
                    commetnContent+="|~"+ci.getId()+"~"+user.getId()+"~"+user.getName()+"~"+user.getPhoto()+
                            "~"+ci.getContent();
                }
            }
        }
    }

    public void setPraiseList(ArrayList<FavortItem> list){
        for (int i = 0; i < list.size(); i++) {
            FavortItem fi = list.get(i);
            if (fi!=null){
                User user = fi.getUser();
                praiseName+="|~"+fi.getId()+"~"+user.getId()+"~"+user.getName()+"~"+user.getPhotoUrl();
            }
        }
    }

    public void setPhotos( ArrayList<PhotoInfoMy> list){
        for (int i = 0; i < list.size(); i++) {
            PhotoInfoMy pi = list.get(i);
            if (!UIUtils.isEmpty(pi.path)){
                imagePath+="|"+pi.path;
            }else {
                imagePath+="|"+pi.url;
            }
        }
    }
    public ArrayList<PhotoInfoMy> getPhotoInfo(){
        if (type!=MyCircleItem.TYPE_IMG){
            return null;
        }
        String[] split = imagePath.split("\\|");
        ArrayList<PhotoInfoMy> pif = new ArrayList<>();
        for (int i = 1; i < split.length; i++) {
            pif.add(new PhotoInfoMy(split[i]));
        }
        return pif;
    }
    public ArrayList<FavortItem> getFavortItem(){
        if (praiseCount==-1){
            return null;
        }
        String[] praiseNameSplit = praiseName.split("\\|");
        ArrayList<FavortItem> fiList = new ArrayList<>();
        for (int i = 1; i < praiseNameSplit.length; i++) {
            String[] split = praiseNameSplit[i].split("~");
            FavortItem fi = new FavortItem();
            fi.setId(split[1]);
            fi.setUser(new User(split[2],split[3],split[4]));
            fiList.add(fi);
        }
        return fiList;
    }
    public ArrayList<CommentItem> getCommentList(){
        if (UIUtils.isEmpty(commetnContent))return null;
        ArrayList<CommentItem> ci = new ArrayList<>();
        //分割总评论
        final String[] commetnContentSplit = commetnContent.split("\\|");
        for (int i = 1; i < commetnContentSplit.length; i++) {
            CommentItem mCommentItem;
            //每条评论
            String perCommentContent = commetnContentSplit[i];
            //分割每条评论
            String[] perCommentContentSplit = perCommentContent.split("~");
            //长度为9，就是回复别人的评论

            if (perCommentContentSplit.length == 9) {
                User user = new User(perCommentContentSplit[2],perCommentContentSplit[3],perCommentContentSplit[4]);
                User replyUser = new User( perCommentContentSplit[6],perCommentContentSplit[7],perCommentContentSplit[8]);
                mCommentItem = new CommentItem(perCommentContentSplit[1], user, perCommentContentSplit[5], replyUser);
            } else {
                //自已单评论
                User user = new User(perCommentContentSplit[2],perCommentContentSplit[3],perCommentContentSplit[4]);
                mCommentItem = new CommentItem(perCommentContentSplit[1], user, perCommentContentSplit[5]);
            }
            ci.add(mCommentItem);
        }
        return ci;
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
