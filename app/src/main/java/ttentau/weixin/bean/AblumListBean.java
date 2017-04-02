package ttentau.weixin.bean;

/**
 * Created by ttent on 2017/3/23.
 */

public class AblumListBean {
    public int count;
    private String filename;
    private String imagepath;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
