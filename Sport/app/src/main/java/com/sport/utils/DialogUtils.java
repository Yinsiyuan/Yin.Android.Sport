package com.sport.utils;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.sport.R;

/**
 * 一些dialog的封装工具类
 */
public class DialogUtils {
    private static Dialog dialog;
    private static TextView name_dialog;

    /**
     * Loding加载动画（DIY内容）
     */
    public static void showLoadingDialog(Context context, String text) {
        dialog = new Dialog(context, R.style.progressBar_LodingDialog);
        dialog.setContentView(R.layout.v_loading_dialog);
        name_dialog = (TextView) dialog.findViewById(R.id.textView_loding_dialog);
        name_dialog.setText(text);
        //为true点击对话框以外时候ProgressDialog就会关闭反之不关
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showLoadingDialog(Context context) {
        showLoadingDialog(context, "卖力加载中...");
    }

    /**
     * Dialog消失
     */
    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }
    }
}
