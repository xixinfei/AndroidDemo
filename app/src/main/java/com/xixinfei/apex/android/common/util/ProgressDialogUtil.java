package com.xixinfei.apex.android.common.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xixinfei.apex.android.R;

/**
 * Created by xixinfei on 2017/5/19.
 */

public class ProgressDialogUtil {
    ProgressDialog progressDialog;
    public ProgressDialogUtil(Context context){
        
        View view = LayoutInflater.from(context).inflate(R.layout.common_loading_progress_dialog, null);
        progressDialog = ProgressDialog.show(context, "", "",true,true);
        progressDialog.setContentView(view);
        if (progressDialog.getWindow() != null) {
            WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
            params.width = (int) (100 * (context.getResources().getDisplayMetrics().density) + 0.5f);
            params.height = (int) (80 * (context.getResources().getDisplayMetrics().density) + 0.5f);
            progressDialog.getWindow().setAttributes(params);
        }
        progressDialog.dismiss();
    }
    public  void show() {

        progressDialog.show();
    }

    public  void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}  