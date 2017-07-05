package com.sport.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.R;

public class TopView extends RelativeLayout {

	private LinearLayout mBack;
	private ImageView mBackImg;
	private TextView mBackTxt;
	private TextView mTitle;
	private ImageView mTitleImg;
	private LinearLayout mFuction;
	private ImageView mFucImgLeft;
	private TextView mFucTxt;
	private ImageView mFucImgRight;

	public TopView(Context context) {
		super(context);
		initView(context);
	}

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TopView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context mContext) {
		LayoutInflater.from(mContext).inflate(R.layout.v_top_view, this, true);

		mBack = (LinearLayout) this.findViewById(R.id.mBack);
		mBackImg = (ImageView) this.findViewById(R.id.mBackImg);
		mBackTxt = (TextView) this.findViewById(R.id.mBackTxt);
		mTitle = (TextView) this.findViewById(R.id.mTitle);
		mTitleImg = (ImageView) this.findViewById(R.id.mTitleimg);
		mFuction = (LinearLayout) this.findViewById(R.id.mFuction);
		mFucImgLeft = (ImageView) this.findViewById(R.id.mFucImgLeft);
		mFucTxt = (TextView) this.findViewById(R.id.mFucTxt);
		mFucImgRight = (ImageView) this.findViewById(R.id.mFucImgRight);

	}

	public void init(String title) {
		mTitle.setText(title);
		setView(false, 0, 0, 0, 0, null);
	}

	public void init(String title,final OnClickTopListener clickTopListener) {
		mTitle.setText(title);
		setView(true, 0, 0, 0, 0, clickTopListener);
	}

	public void init(boolean isBack, int backResId, int titleResId, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {
		mTitle.setText(titleResId);
		setView(isBack, backResId, fucLeftImg, fucResId, fucRightImg, clickTopListener);
	}

	public void init(boolean isBack, int backResId, int titleResId, int titleImg, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {
		mTitle.setText(titleResId);
		setView(isBack, backResId, fucLeftImg, fucResId, fucRightImg, clickTopListener);
		setTitleImg(titleImg);
	}

	public void init(boolean isBack, int backResId, String title, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {
		mTitle.setText(title);
		setView(isBack, backResId, fucLeftImg, fucResId, fucRightImg, clickTopListener);
	}

	private void setView(boolean isBack, int backResId, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {

		if (isBack) {
			mBackImg.setVisibility(View.VISIBLE);
		} else {
			mBackImg.setVisibility(View.GONE);
		}

		if (backResId != 0) {
			mBackTxt.setText(backResId);
		}

		if (clickTopListener != null) {
			mBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clickTopListener.onLeft();
				}
			});
			mTitle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clickTopListener.onTitle();
				}
			});
			mFuction.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clickTopListener.onRight();
				}
			});
		}

		mFucImgLeft.setImageResource(fucLeftImg);
		if (fucResId != 0) {
			mFucTxt.setText(fucResId);
		}
		mFucImgRight.setImageResource(fucRightImg);

		mTitleImg.setVisibility(View.GONE);
	}

	public void setTitleImg(int resId) {
		if (resId != 0) {
			mTitleImg.setVisibility(View.VISIBLE);
			mTitleImg.setImageResource(resId);
		} else {
			mTitleImg.setVisibility(View.GONE);
			mTitleImg.setImageResource(resId);
		}
	}

	public void goneFuc() {
		mFucTxt.setVisibility(View.GONE);
	}

	public void visibleFuc() {
		mFucTxt.setVisibility(View.VISIBLE);
	}

	public void setRightText(String text) {
		mFucTxt.setText(text);
		mFucTxt.setVisibility(View.VISIBLE);
	}

	public void setTitleText(String text) {
		mTitle.setText(text);
	}

	public void setFucLeftImg(int resId) {
		mFucImgLeft.setImageResource(resId);
	}

	public void setFucRightImg(int resId) {
		mFucImgRight.setImageResource(resId);
	}

	public static abstract class OnClickTopListener {

		public void onLeft() {
		}

		public void onTitle() {
		}

		public void onRight() {
		}
	}

}
