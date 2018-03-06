package com.xixinfei.testapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AndroidException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MyCanvas extends View {

    //-------------View相关-------------
//View自身的宽和高
    private int mHeight;
    private int mWidth;
    //-------------画笔相关-------------
    //边框的画笔
    private Paint borderPaint;
    //文字的画笔
    private Paint textPaint;
    //区域的画笔
    private Paint areaPaint;
    //-------------颜色相关-------------

    //background
    private int bgColor = Color.GREEN;
    //边框颜色
    private int mColor = Color.WHITE;
    //文字颜色
    private int textColor = Color.RED;
    //区域颜色
    private int areaColor = Color.GREEN;


    Paint paint;  //绘图

    Context mContext;
    public MyCanvas(Context context) {
        super(context);
        mContext  = context;
        init(null, 0);
    }


    private void init(AttributeSet attrs, int defStyle) {

        DisplayMetrics metrics = new DisplayMetrics();
        metrics = mContext.getApplicationContext().getResources().getDisplayMetrics();
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        // Load attributes
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeWidth(5);


        //初始化画笔  
        initPaint();
    }

    private void initPaint() {
        //边框画笔
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(mColor);
        borderPaint.setStrokeWidth(3);
        //文字画笔
        textPaint = new Paint();
        textPaint.setTextSize(30);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        //区域画笔
        areaPaint = new Paint();
        areaPaint.setColor(areaColor);
        areaPaint.setAntiAlias(true);
        areaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        canvas.drawColor(Color.BLUE);
        drawLine(canvas);
        //画红色区域
//        drawArea(canvas);



    }

    private void drawLine(Canvas canvas) {
        final int width = mWidth;
        final int height = mHeight;
        final int edgeWidth = 10;

        final int vspace = width/52+5;   //长宽间隔
        final int hspace = width/76+5;   //长宽间隔


        int vertz = 0;
        int hortz = 0;
        for(int i=0;i<52;i++){//shu xian
            canvas.drawLine(0,  vertz,  width, vertz, paint);
//            canvas.drawLine(hortz, 0, hortz, height, paint);
            vertz+=vspace;
//            hortz+=space;
        }

        for(int i=0;i<76;i++){// hengxian
//            canvas.drawLine(0,  vertz,  width, vertz, paint);
            canvas.drawLine(hortz, 0, hortz, height, paint);
//            vertz+=space;
            hortz+=hspace;
        }
    }

    private void drawArea(Canvas canvas) {

        final int vspace = mWidth/52;   //长宽间隔
        final int hspace = mHeight/76;   //长宽间隔


        int vertz = 0;
        int hortz = 0;
        for(int i=0;i<12;i++){//shu xian
            canvas.drawRect(0,  vertz,  mWidth, vertz, areaPaint);
//            canvas.drawLine(hortz, 0, hortz, height, paint);
            vertz+=vspace;
//            hortz+=space;
        }
    }
}