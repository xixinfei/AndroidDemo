package com.xixinfei.apex.android.common.util;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xixinfei.apex.android.R;
import com.xixinfei.apex.android.common.listener.DialogButtonListener;


public class DialogUtil {

    private static boolean IS_DIALOG_SHOW = false;

    public static void showTipDialog(final Context context, String title, String strMsg, String btnTxt, final boolean isActEnd) {
        final Dialog popTipDialog = new Dialog(context, R.style.dialog);
        popTipDialog.getWindow().setContentView(R.layout.common_dialog_notice);

        if (!IS_DIALOG_SHOW && !((Activity) context).isFinishing()) {
            popTipDialog.show();
            IS_DIALOG_SHOW = true;
            RelativeLayout layout1 = (RelativeLayout) popTipDialog.getWindow().findViewById(R.id.dialog_ok1);
            RelativeLayout layout2 = (RelativeLayout) popTipDialog.getWindow().findViewById(R.id.dialog_ok2);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);

            TextView tvTitle = (TextView) popTipDialog.getWindow().findViewById(R.id.tv_dialog_title);
            tvTitle.setText(title);

            TextView tvMsg = (TextView) popTipDialog.getWindow().findViewById(R.id.tv_dialog_msg);
            tvMsg.setText(strMsg);

            Button btnOk = (Button) popTipDialog.getWindow().findViewById(R.id.btok);
            btnOk.setText(btnTxt);

            btnOk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popTipDialog.dismiss();
                    IS_DIALOG_SHOW = false;
                }
            });

            popTipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    IS_DIALOG_SHOW = false;
                    if (isActEnd) {
                        ((Activity) context).finish();
                    }
                }
            });
        }
    }

    public static void showDialog(final Context context, String title, String msg, String middleTxt, final DialogButtonListener listener, final Integer tag) {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.getWindow().setContentView(R.layout.common_dialog_notice);
        dialog.show();
        RelativeLayout layout1 = (RelativeLayout) dialog.getWindow().findViewById(R.id.dialog_ok1);
        RelativeLayout layout2 = (RelativeLayout) dialog.getWindow().findViewById(R.id.dialog_ok2);

        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
        TextView tvTitle = (TextView) dialog.getWindow().findViewById(R.id.tv_dialog_title);
        tvTitle.setText(title);

        TextView tvMsg = (TextView) dialog.getWindow().findViewById(R.id.tv_dialog_msg);
        tvMsg.setText(msg);

        Button ok = (Button) dialog.getWindow().findViewById(R.id.btok);
        ok.setText(middleTxt);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.btnOkClicked(dialog, tag);
            }
        });

    }

    public static void showDialog(final Context context, String title, String msg, String leftText, String rightText, final DialogButtonListener listener, final Integer tag) {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.getWindow().setContentView(R.layout.common_dialog_notice);
        dialog.show();

        RelativeLayout layout1 = (RelativeLayout) dialog.getWindow().findViewById(R.id.dialog_ok1);
        RelativeLayout layout2 = (RelativeLayout) dialog.getWindow().findViewById(R.id.dialog_ok2);

        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);

        TextView tvTitle = (TextView) dialog.getWindow().findViewById(R.id.tv_dialog_title);
        tvTitle.setText(title);

        TextView tvMsg = (TextView) dialog.getWindow().findViewById(R.id.tv_dialog_msg);
        tvMsg.setText(msg);

        Button loginAlertGoon = (Button) dialog.getWindow().findViewById(R.id.btSure);

        loginAlertGoon.setText(rightText);

        loginAlertGoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.btnOkClicked(dialog, tag);
            }
        });

        Button loginAlertCancel = (Button) dialog.getWindow().findViewById(R.id.btCancel);

        loginAlertCancel.setText(leftText);

        loginAlertCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.btnCancelClicked(dialog, tag);
            }
        });
    }


    public static void setListViewHeightBasedOnChildren(RecyclerView listView) {
        RecyclerView.Adapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getItemCount(); i++) {
            View listItem = listView.getChildAt(i);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
