package com.sport.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sport.R;



public class UploadDialog implements View.OnClickListener {
    private AlertDialog builder;
    private Context context;
    public TextView takePhoto,localPhoto,cancel;

    public UploadDialog(Context c){
        context=c;
    }

    public void showDialog(){
        View inflate = LayoutInflater.from(context).inflate(R.layout.v_photo_dialog, null);
        takePhoto= (TextView) inflate.findViewById(R.id.take_photo);
        localPhoto= (TextView) inflate.findViewById(R.id.local_photo);
        cancel= (TextView) inflate.findViewById(R.id.cancel);

        takePhoto.setOnClickListener(this);
        localPhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);

        builder = new AlertDialog.Builder(context).create();
        builder.setView(inflate);
        builder.show();

        WindowManager.LayoutParams attributes = builder.getWindow().getAttributes();
        attributes.width=800;
        builder.getWindow().setAttributes(attributes);
    }

    public void hideDialog(){
        builder.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_photo:
                hideDialog();
                PhotoUtil.getPhotoFromCarema((FragmentActivity) context);
                break;
            case R.id.local_photo:
                hideDialog();
                PhotoUtil.getPhotoFromAlbum((FragmentActivity) context);
                break;
            case R.id.cancel:
                hideDialog();
                break;
        }
    }
}
