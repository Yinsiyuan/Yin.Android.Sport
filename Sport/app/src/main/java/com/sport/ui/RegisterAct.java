package com.sport.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sport.R;
import com.sport.bean.UserInfo;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;
import cn.bmob.v3.listener.SaveListener;



public class RegisterAct extends BaseAct implements View.OnClickListener {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mNumber)
    EditText mNumber;
    @BindView(R.id.mPassWord)
    EditText mPassWord;
    @BindView(R.id.mRePassWord)
    EditText mRePassWord;
    @BindView(R.id.mCode)
    EditText mCode;
    @BindView(R.id.mGetCode)
    TextView mGetCode;
    @BindView(R.id.mSubmit)
    Button mSubmit;

    private EditText mNick;
    private LinearLayout mNickLay;


    private CountDownTimer timer;
    private String source;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        source = getIntent().getStringExtra("source");
        if (source.equals("register")) {
            return R.layout.a_register;
        } else {
            return R.layout.a_forget_pwd;
        }

    }

    @Override
    public void initView() {
        if (source.equals("register")) {
            mNick= (EditText) findViewById(R.id.mNick);
            mNickLay= (LinearLayout) findViewById(R.id.mNickLay);
            mTopView.init("用户注册", new TopView.OnClickTopListener() {
                @Override
                public void onLeft() {
                    finish();
                }
            });
        } else {
            mTopView.init("忘记密码", new TopView.OnClickTopListener() {
                @Override
                public void onLeft() {
                    finish();
                }
            });
        }
    }

    private void getCode(String num) {
        //请求短信验证码
        BmobSMS.requestSMSCode(this, num, "天气", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    waitingCode();
                    toast("发送成功");
                } else {
                    toast("错误码：" + e.getErrorCode() + ",错误原因：" + e.getLocalizedMessage());
                }
            }
        });
    }

    private String nick = "";

    private void registerUser() {
        String number = mNumber.getText().toString();
        String pwd = mPassWord.getText().toString();
        String repwd = mRePassWord.getText().toString();
        String inputCode = mCode.getText().toString();
        if (source.equals("register")) {
            nick = mNick.getText().toString();
        }

        if (TextUtils.isEmpty(number)) {
            toast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            toast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(inputCode)) {
            toast("请输入验证码");
            return;
        }

        if (!pwd.equals(repwd)) {
            toast("二次密码输入不一致");
            return;
        }

        if (source.equals("register")) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(number);
            userInfo.setPassword(pwd);
            userInfo.setMobilePhoneNumber(number);
            userInfo.setNick(nick);
            userInfo.signOrLogin(this, inputCode, new SaveListener() {
                @Override
                public void onSuccess() {
                    toast("注册成功");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("注册失败");
                }
            });
        } else {
            //重置的是绑定了该手机号的账户的密码
            BmobUser.resetPasswordBySMSCode(this, inputCode, pwd, new ResetPasswordByCodeListener() {

                @Override
                public void done(BmobException e) {
                    // TODO Auto-generated method stub
                    if (e == null) {
                        toast("设置成功");
                        finish();
                    } else {
                        toast("错误码：" + e.getErrorCode() + ",错误原因：" + e.getLocalizedMessage());
                    }
                }
            });
        }


    }

    /**
     * 获取短信60秒倒计时
     */
    private void waitingCode() {
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    mGetCode.setClickable(false);
                    mGetCode.setText("获取验证码(?)".replace("?", (millisUntilFinished / 1000) + ""));
                    mGetCode.setTextColor(Color.GRAY);
                }

                public void onFinish() {
                    mGetCode.setClickable(true);
                    mGetCode.setText("获取短信验证码");
                    mGetCode.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            };
        }
        timer.start();
    }

    @OnClick({R.id.mGetCode, R.id.mSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mGetCode:
                String number = mNumber.getText().toString();
                if (TextUtils.isEmpty(number)) {
                    toast("请输入手机号码");
                    return;
                }
                getCode(number);
                break;
            case R.id.mSubmit:
                registerUser();
                break;
        }
    }
}
