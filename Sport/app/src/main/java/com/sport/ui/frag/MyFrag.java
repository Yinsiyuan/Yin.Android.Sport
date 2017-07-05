package com.sport.ui.frag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sport.R;
import com.sport.bean.UserInfo;
import com.sport.common.BaseFrag;
import com.sport.ui.CollectAct;
import com.sport.ui.LoginAct;
import com.sport.ui.PublishDetailAct;
import com.sport.ui.SettingAct;
import com.sport.utils.PhotoUtil;
import com.sport.utils.UploadDialog;
import com.sport.widget.CircleTransform;
import com.sport.widget.TopView;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/
public class MyFrag extends BaseFrag {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.mName)
    TextView mName;
    @BindView(R.id.mUser)
    LinearLayout mUser;
    @BindView(R.id.mPublish)
    LinearLayout mPublish;
    @BindView(R.id.mMessage)
    LinearLayout mMessage;
    @BindView(R.id.mSetting)
    LinearLayout mSetting;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private UserInfo bmobUser;
    private UploadDialog dialog;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.f_my;
    }

    @Override
    public void initView(View view) {
        mTopView.init("新闻");
        setName();
    }

    /**
     * 设置用户相关信息
     */
    private void setName() {
        bmobUser = BmobUser.getCurrentUser(getActivity(), UserInfo.class);
        if (bmobUser == null) {
            mName.setText("登录/注册");
            mName.setTextColor(Color.parseColor("#2196F3"));
            headImg.setImageResource(R.mipmap.item_user_default);
        } else {
            mName.setText(bmobUser.getNick());
            mName.setTextColor(Color.BLACK);
            String headImgUrl = (String) BmobUser.getObjectByKey(getActivity(), "headImgUrl");
            if (headImgUrl != null && !TextUtils.isEmpty(headImgUrl)){
                Picasso.with(getActivity()).load(headImgUrl).transform(new CircleTransform()).into(headImg);
            }
        }
    }

    /**
     * 上传图片
     * @param file
     */
    private void upload(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(getActivity(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                UserInfo info = new UserInfo();
                info.setHeadImgUrl(bmobFile.getFileUrl(getActivity()));
                info.update(getActivity(), bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        toast("上传成功");
                        setName();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("上传失败");
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                toast("上传失败");
            }
        });
    }

    /**
     * 接受上传图片返回相关参数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoUtil.PiC_FROM_ALBUM) {
            PhotoUtil.zoomPhoto(getActivity(), data.getData(), 1, 1, 200, 200);
        } else if (requestCode == PhotoUtil.PiC_FROM_CAMERA) {
            // 判断存储卡是否可以用，可用进行存储
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File tempFile = new File(path, PhotoUtil.IMAGE_NAME);
                PhotoUtil.zoomPhoto(getActivity(), Uri.fromFile(tempFile), 1, 1, 200, 200);
            } else {
                toast("未找到存储卡，无法存储照片！");
            }
        } else if (requestCode == PhotoUtil.PiC_FROM_CROP) {
            if (data == null || data.getExtras() == null) {
                toast("剪切失败!");
            } else {
                getImageToView(data.getExtras());

            }
        } else if(requestCode==111){
            setName();
        }
    }

    private void getImageToView(Bundle extras) {
        Bitmap bitmap = extras.getParcelable("data");
        File file = PhotoUtil.saveFile(bitmap, System.currentTimeMillis() + ".jpg");
        upload(file);
    }

    /**
     *点击事件
     * @param view
     */
    @OnClick({R.id.headImg, R.id.mUser, R.id.mPublish, R.id.mMessage,R.id.mSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headImg:
                if (bmobUser == null) {
                    Intent intent3 = new Intent(getActivity(), LoginAct.class);
                    startActivityForResult(intent3,111);
                    return;
                }
                dialog = new UploadDialog(getActivity());
                dialog.showDialog();
                break;
            case R.id.mUser:
                if (bmobUser == null) {
                    Intent intent3 = new Intent(getActivity(), LoginAct.class);
                    startActivityForResult(intent3,111);
                }
                break;
            case R.id.mPublish:
                if(bmobUser==null){
                    Intent intent3 = new Intent(getActivity(), LoginAct.class);
                    startActivityForResult(intent3,111);
                }else {
                    startActivity(new Intent(getActivity(),PublishDetailAct.class));
                }
                break;
            case R.id.mMessage:
                if(bmobUser==null){
                    Intent intent3 = new Intent(getActivity(), LoginAct.class);
                    startActivityForResult(intent3,111);
                }else {
                    startActivity(new Intent(getActivity(),CollectAct.class));
                }
                break;
            case R.id.mSetting:
                Intent intent = new Intent(getActivity(), SettingAct.class);
                startActivityForResult(intent,111);
                break;
        }
    }
}
