package com.sport.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.sport.R;
import com.sport.bean.Team;
import com.sport.bean.UserInfo;
import com.sport.common.BaseAct;
import com.sport.utils.DialogUtils;
import com.sport.widget.TopView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class PublishAct extends BaseAct {

    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mType)
    Spinner mType;
    @BindView(R.id.mTitle1)
    EditText mTitle;
    @BindView(R.id.mContent1)
    EditText mContent;

    private String[] places = {"篮球场", "羽毛球", "游泳馆", "其他"};

    private String type = "篮球场";

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_publish;
    }

    @Override
    public void initView() {
        mTopView.init("发布", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
        //Spinner设置
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, places);
        mType.setAdapter(adapter);
        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        type = "篮球场";
                        break;
                    case 1:
                        type = "羽毛球";
                        break;
                    case 2:
                        type = "游泳馆";
                        break;
                    case 3:
                        type = "其他";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.mCommit)
    public void onClick() {
        DialogUtils.showLoadingDialog(this);
        UserInfo userInfo = BmobUser.getCurrentUser(this, UserInfo.class);
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();

        if (TextUtils.isEmpty(title)) {
            toast("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            toast("请输入内容");
            return;
        }

        Team team = new Team();
        team.setNick(userInfo.getNick());
        team.setHeadImg(userInfo.getHeadImgUrl());
        team.setTitle(title);
        team.setContent(content);
        team.setType(type);
        team.setFlag("0");

        team.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                setResult(RESULT_OK);
                toast("发布成功");
                DialogUtils.dismiss();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                DialogUtils.dismiss();
                Log.e("error--->", s);
            }
        });
    }
}
