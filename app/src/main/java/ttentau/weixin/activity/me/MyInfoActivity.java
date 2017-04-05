package ttentau.weixin.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.bean.MyUser;
import ttentau.weixin.imageloader.GlideImageLoader;
import ttentau.weixin.uitls.Constant;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/31.
 */
public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.iv_user_photo)
    ImageView mIvUserPhoto;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_username)
    TextView mTvUserUsername;
    @BindView(R.id.iv_user_code)
    ImageView mIvUserCode;
    @BindView(R.id.tv_user_shoplocation)
    TextView mTvUserShoplocation;
    @BindView(R.id.tv_user_sex)
    TextView mTvUserSex;
    @BindView(R.id.tv_user_location)
    TextView mTvUserLocation;
    @BindView(R.id.tv_user_autograph)
    TextView mTvUserAutograph;
    @BindView(R.id.tv_user_linkaccount)
    TextView mTvUserLinkaccount;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
        String username = currentUser.getUsername();
        String name = currentUser.getName();
        String autograph = currentUser.getAutograph();
        Integer age = currentUser.getAge();
        String location = currentUser.getLocation();
        Boolean sex = currentUser.getSex();
        BmobFile photo = currentUser.getPhoto();
        if (!UIUtils.isEmpty(name)) {
            mTvUserName.setText(name);
        }
        if (!UIUtils.isEmpty(username)) {
            mTvUserUsername.setText(username);
        }
        if (!UIUtils.isEmpty(location)) {
            mTvUserLocation.setText(location);
        }
        if (!UIUtils.isEmpty(autograph)) {
            mTvUserAutograph.setText(autograph);
        }
        if (!UIUtils.isNull(sex)) {
            mTvUserSex.setText(sex ? "男" : "女");
        }
        if (!UIUtils.isEmpty(location)) {
            mTvUserLocation.setText(location);
        }
        if (!UIUtils.isNull(photo)) {
            Glide.with(MyInfoActivity.this).load(photo.getFileUrl()).into(mIvUserPhoto);
        }
    }

    @OnClick({R.id.rl_user_photo, R.id.rl_user_name, R.id.rl_user_username, R.id.rl_user_code, R.id.rl_user_shoplocation, R.id.rl_user_sex, R.id.rl_user_location, R.id.rl_user_autograph, R.id.rl_user_linkaccount})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_user_photo:
                imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());
                imagePicker.setMultiMode(false);
                Intent intent3 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent3, Constant.I_REQUEST_CROPPHOTO);
                break;
            case R.id.rl_user_name:
                intent = new Intent(this, UpdataUserNameAndAuToGraghActivity.class);
                intent.putExtra("whichform", 2);
                IntentUtils.startActivityForResult(this, intent, Constant.I_REQUEST_MYINFOTOUPDATA);
                break;
            case R.id.rl_user_username:
                break;
            case R.id.rl_user_code:
                break;
            case R.id.rl_user_shoplocation:
                break;
            case R.id.rl_user_sex:
                break;
            case R.id.rl_user_location:
                break;
            case R.id.rl_user_autograph:
                intent = new Intent(this, UpdataUserNameAndAuToGraghActivity.class);
                intent.putExtra("whichform", 8);
                IntentUtils.startActivityForResult(this, intent, Constant.I_REQUEST_MYINFOTOUPDATA);
                break;
            case R.id.rl_user_linkaccount:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constant.I_REQUEST_CROPPHOTO:
                if (data != null) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                    Log.e("tag",images.size()+"<<size>>"+images.get(0).path);
                    String picPath = images.get(0).path;
                    Glide.with(this).load(picPath).into(mIvUserPhoto);
                    final BmobFile bmobFile = new BmobFile(new File(picPath));
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                UIUtils.Toast("上传文件成功:" + bmobFile.getFileUrl());
                                MyUser newUser = new MyUser();
                                newUser.setPhoto(bmobFile);
                                MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                                newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            UIUtils.Toast("ok");
                                        } else {
                                        }
                                    }
                                });
                            } else {
                                UIUtils.Toast("上传文件失败：" + e.getMessage());
                            }
                        }

                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                        }
                    });
                } else {
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                break;
            case Constant.I_REQUEST_MYINFOTOUPDATA:
                initData();
                break;
        }
    }
}
