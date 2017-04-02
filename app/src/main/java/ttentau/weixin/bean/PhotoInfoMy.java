package ttentau.weixin.bean;


import java.io.Serializable;

import ttentau.weixin.R;


/**
 * Created by suneee on 2016/11/17.
 */
public class PhotoInfoMy implements Serializable{

    private static final long serialVersionUID = 1L;
    public int url= R.drawable.a1_2;
    public String path;
    public int w;
    public int h;
    public PhotoInfoMy(){

    }
    public PhotoInfoMy(int url) {
        this.url = url;
    }
    public PhotoInfoMy(String path) {
        this.path=path;
    }
}
