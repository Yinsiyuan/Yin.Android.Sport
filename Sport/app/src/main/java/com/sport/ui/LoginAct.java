package com.sport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sport.R;
import com.sport.bean.UserInfo;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import butterknife.BindView;
import cn.bmob.v3.listener.SaveListener;


public class LoginAct extends BaseAct {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.iv_user)
    ImageView iv_user;
    @BindView(R.id.et_user)
    EditText et_user;
    @BindView(R.id.iv_pwd)
    ImageView iv_pwd;
    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_login;
    }

    @Override
    public void initView() {
        mTopView.init("登录", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });

        //edittext焦点
        et_user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iv_user.setBackgroundResource(R.mipmap.login_user_p);
                } else {
                    iv_user.setBackgroundResource(R.mipmap.login_user_u);
                }
            }
        });

        et_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iv_pwd.setBackgroundResource(R.mipmap.login_pwd_p);
                } else {
                    iv_pwd.setBackgroundResource(R.mipmap.login_pwd_u);
                }
            }
        });

    }

    public void userRegister(View v) {//注册
        Intent intent = new Intent(this, RegisterAct.class);
        intent.putExtra("source", "register");
        startActivity(intent);
    }

    public void forgetPwd(View v) {//忘记密码
        Intent intent = new Intent(this, RegisterAct.class);
        intent.putExtra("source", "forgetPwd");
        startActivity(intent);
    }

    public void mLogin(View v) {//登录
        UserInfo userInfo = new UserInfo();
        String user = et_user.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
            toast("请填写帐号或密码");
            return;
        }
        userInfo.setUsername(user);
        userInfo.setPassword(pwd);
        userInfo.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                toast("登录成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                toast(s);
            }
        });
    }

}
