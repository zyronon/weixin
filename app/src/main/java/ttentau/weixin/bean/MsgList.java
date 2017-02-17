package ttentau.weixin.bean;

/**
 * Created by ttent on 2017/2/9.
 */

public class MsgList {

    public String chatId;
    public String lastcontent;
    public MsgList(String chatId, String lastcontent){
        this.chatId=chatId;
        this.lastcontent=lastcontent;
    }
    public String getLastContent(){
        return lastcontent;
    }
    public String getChatId(){
        return chatId;
    }

}
