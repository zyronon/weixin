package ttentau.weixin.bean;


import ttentau.weixin.R;

/**
 * Created by suneee on 2016/11/17.
 */
public class PhotoInfo {

    public int url= R.drawable.a1_2;
    public String path;
    public int w;
    public int h;

    public PhotoInfo(int url) {
        this.url = url;
    }
    public PhotoInfo(String path) {
        this.path=path;
    }
}
