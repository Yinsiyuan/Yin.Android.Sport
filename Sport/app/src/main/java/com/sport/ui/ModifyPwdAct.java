package com.sport.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.sport.R;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;


public class ModifyPwdAct extends BaseAct {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mNumber)
    EditText mOldPwd;
    @BindView(R.id.mPassWord)
    EditText mNewPwd;
    @BindView(R.id.mRePassWord)
    EditText mRePwd;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_modify_pwd;
    }

    @Override
    public void initView() {
        mTopView.init("修改密码", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void modifyPwd() {
        String old = mOldPwd.getText().toString();
        String newPwd = mNewPwd.getText().toString();
        String rePwd = mRePwd.getText().toString();

        if (TextUtils.isEmpty(old)) {
            toast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(old)) {
            toast("请输入新密码");
            return;
        }
        if (!newPwd.equals(rePwd)) {
            toast("二次输入密码不同");
            return;
        }

        BmobUser.updateCurrentUserPassword(this, old, newPwd, new UpdateListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                toast("密码修改成功，可以用新密码进行登录");
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("密码修改失败：" + msg + "(" + code + ")");
            }
        });


    }

    @OnClick(R.id.mSubmit)
    public void onClick() {
        modifyPwd();
    }
}
