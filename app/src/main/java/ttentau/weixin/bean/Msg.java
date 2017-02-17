package ttentau.weixin.bean;

/**
 * Created by ttent on 2017/2/9.
 */

public class Msg {
    public static final int TYPR_SEND=0;
    public static final int TYPR_RECEIVE=1;

    public int type;
    public String content;
    public Msg(int Type,String content){
        this.type=Type;
        this.content=content;
    }
    public String getContent(){
        return content;
    }
    public int getType(){
        return type;
    }

}
