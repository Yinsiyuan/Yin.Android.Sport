package com.sport.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.sport.R;
import com.sport.bean.UserInfo;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;


public class ModifyNickAct extends BaseAct {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mNick)
    EditText mNick;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_modify_nick;
    }

    @Override
    public void initView() {
        mTopView.init("修改用户名", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    /**
     * 修改密码
     */
    private void modifyPwd() {
        String nick = mNick.getText().toString();

        UserInfo currentUser = BmobUser.getCurrentUser(ModifyNickAct.this, UserInfo.class);

        if (currentUser == null) {
            toast("请先登录账号");
        } else {
            if (TextUtils.isEmpty(nick)) {
                toast("请输入用户名");
                return;
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setNick(nick);
            userInfo.update(ModifyNickAct.this, currentUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    toast("用户名更改成功，请重新登录");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.e("error-->", s);
                }
            });
        }


    }

    //提交
    @OnClick(R.id.mSubmit)
    public void onClick() {
        modifyPwd();
    }
}
