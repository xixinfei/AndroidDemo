package com.xixinfei.apex.android.common.util;

import android.content.Context;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by xixinfei on 2017/5/18.
 */

public  class ProgressSubscriber<T> implements Subscriber<T> {
    private boolean isShow = true;
    private Context mContext;
    private ProgressDialogUtil pd;

    Subscriber<T> mSubscriber;
    public ProgressSubscriber(boolean isShow, Context context, Subscriber<T> subscriber) {
        this.mSubscriber = subscriber;
        this.isShow = isShow;
        mContext = context;
        pd = new ProgressDialogUtil(mContext);

    }


    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        mSubscriber.onSubscribe(subscription);
        if (isShow){
            pd.show();
        }
    }

    @Override
    public void onNext(T t) {
        mSubscriber.onNext(t);
    }


    @Override
    public void onError(Throwable throwable) {
        mSubscriber.onError(throwable);
        pd.dismiss();
    }

    @Override
    public void onComplete() {
        mSubscriber.onComplete();
        pd.dismiss();
    }

}
