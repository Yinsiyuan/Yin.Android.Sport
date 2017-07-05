package com.sport.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseFrag extends Fragment {
    public static final int SELECT_CITY=100;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(setContentView(savedInstanceState),null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    public abstract int setContentView(Bundle savedInstanceState);
    public abstract void initView(View view);

    public void toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}
