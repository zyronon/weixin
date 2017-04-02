package ttentau.weixin.bean;

import java.io.Serializable;

/**
 * Created by ttent on 2017/2/19.
 */
public class ImageModel implements Serializable {

    private String id;//图片id
    private int count;//图片总数
    private String path;//路径
    private String fileName;//文件夹名
    private Boolean isChecked = false;//是否被选中

    @Override
    public boolean equals(Object o) {
        try {
            ImageModel other = (ImageModel) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ImageModel(String id, String path, Boolean isChecked) {
        this.id = id;
        this.path = path;
        this.isChecked = isChecked;
    }

    public ImageModel(String id, String path) {
        this.id = id;
        this.path = path;
    }
    public ImageModel(int count, String fileName) {
        this.count = count;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

}
