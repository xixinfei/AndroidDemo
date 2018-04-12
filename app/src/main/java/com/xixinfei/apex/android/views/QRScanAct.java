package com.xixinfei.apex.android.views;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xixinfei.apex.android.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;


import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by xixinfei on 2017/4/14.
 */

public class QRScanAct extends Activity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private ZXingScannerView mScannerView;
    private static final String TAG = QRScanAct.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private TextView topbar_back_txt, topbar_title;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        if (AndPermission.hasPermission(this, Manifest.permission.CAMERA)) {

        } else {
            AndPermission.with(this).requestCode(100).permission(Manifest.permission.CAMERA).send();
        }
        initView();
        from = getIntent().getStringExtra("from");
    }

    private void initView() {
        topbar_back_txt = (TextView) findViewById(R.id.topbar_back_txt);
        topbar_back_txt.setOnClickListener(this);
        topbar_back_txt.setVisibility(View.VISIBLE);
        topbar_title = (TextView) findViewById(R.id.topbar_title_txt);
        topbar_title.setText("扫一扫");

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        contentFrame.addView(mScannerView);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_back_txt:
                this.finish();
                break;
        }
    }


    int CODE_TYPE = -1;      //标示 (1一维码、 2、二维码   3、其他码)  


    @Override
    public void handleResult(Result rawResult) {
        if (rawResult != null) {
            //            Toast.makeText(this, "Contents = " + rawResult.getText() +
            //                    ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

            vibrate();

            //扫描获取的 编码 不为空  
            if (!TextUtils.isEmpty(rawResult.getBarcodeFormat().toString())) {

                String mBarcodeFormat = rawResult.getBarcodeFormat().toString();                   //拍码后返回的编码格式  

                if (mBarcodeFormat.equals(BarcodeFormat.DATA_MATRIX.name())) {
                    CODE_TYPE = 3;
                } else if (mBarcodeFormat.equals(BarcodeFormat.QR_CODE.name())) {
                    CODE_TYPE = 2;
                    showResultDialog(rawResult);
                } else {
                    CODE_TYPE = 1;



                }
                reset();
                Log.e("---> (1一维码、2、二维码3、其他码) ", "" + CODE_TYPE + ";barcode format=" + mBarcodeFormat + ";content=" + rawResult.getText());
            }

        }

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.


    }

    private void reset() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(QRScanAct.this);
            }
        }, 2000);
    }


    private void showResultDialog(Result rawResult) {


        String tmp = rawResult.getText();

        Toast.makeText(this, tmp, Toast.LENGTH_LONG).show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
                // TODO 相应代码。
            } else if (requestCode == 101) {
                // TODO 相应代码。
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(QRScanAct.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(QRScanAct.this, requestCode).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    };

}
