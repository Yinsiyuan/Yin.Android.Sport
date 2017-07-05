package com.sport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sport.R;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


public class SettingAct extends BaseAct implements View.OnClickListener {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.btn_modify_pwd)
    LinearLayout modifyPwd;
    @BindView(R.id.btn_modify_nick)
    LinearLayout modifyNick;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_setting;
    }

    @Override
    public void initView() {
        mTopView.init("设置", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });

    }

    @OnClick({R.id.btn_modify_pwd, R.id.btn_modify_nick, R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_pwd:
                startActivity(new Intent(this, ModifyPwdAct.class));
                break;
            case R.id.btn_modify_nick:
                startActivity(new Intent(this, ModifyNickAct.class));
                break;
            case R.id.exit:
                BmobUser.logOut(this);
                toast("成功退出");
//                SignalManager.INSTANCE.send(new Signal.Builder().setTarget(MyFrag.TAG).Build());
                finish();
                break;
        }
    }
}
