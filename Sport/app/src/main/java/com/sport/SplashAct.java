package com.sport;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sport.common.BaseAct;
import com.sport.ui.HomeAct;

import butterknife.BindView;

public class SplashAct extends BaseAct {

    @BindView(R.id.mSplash)
    ImageView mSplash;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.a_splash;
    }

    @Override
    public void initView() {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //控制ImageView动画
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1.0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0, 1.0f);

        ObjectAnimator animator = new ObjectAnimator().ofPropertyValuesHolder(mSplash, scaleX, alpha);
        animator.setDuration(2500).start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //睡眠0.5s
                SystemClock.sleep(500);
                startActivity(new Intent(SplashAct.this, HomeAct.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
