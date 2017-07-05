package com.sport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.sport.R;
import com.sport.common.BaseAct;
import com.sport.ui.frag.ActivityFrag;
import com.sport.ui.frag.MyFrag;
import com.sport.ui.frag.NewsFrag;
import com.sport.ui.frag.PlaceFrag;
import com.sport.ui.frag.TeamFrag;

import butterknife.OnClick;


public class HomeAct extends BaseAct {
    public static final String FRAGMENT_TAG = "fragment_tag";
    private FragmentManager fm;
    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_home;
    }

    @Override
    public void initView() {
        fragManager(new NewsFrag());
    }

    /**
     * fragmanager加载fragment
     * @param frag
     */
    private void fragManager(Fragment frag) {
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.mContent, frag,FRAGMENT_TAG).commitAllowingStateLoss();
    }

    @OnClick({R.id.news, R.id.place, R.id.team, R.id.activity, R.id.my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news://新闻
                fragManager(new NewsFrag());
                break;
            case R.id.place://寻场
                fragManager(new PlaceFrag());
                break;
            case R.id.team://组队
                fragManager(new TeamFrag());
                break;
            case R.id.activity://活动
                fragManager(new ActivityFrag());
                break;
            case R.id.my://我的
                fragManager(new MyFrag());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fw = fm.findFragmentByTag(FRAGMENT_TAG);
        fw.onActivityResult(requestCode, resultCode, data);
    }
}
