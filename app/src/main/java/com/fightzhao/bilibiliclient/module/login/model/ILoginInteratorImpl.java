package com.fightzhao.bilibiliclient.module.login.model;

import android.text.TextUtils;

import com.fightzhao.bilibiliclient.base.RequestCallback;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 作者 ：fightzhao on 2016/11/25 14:00.
 * 功能 :
 */

public class ILoginInteratorImpl implements ILoginInterator {

    @Override
    public Subscription login(RequestCallback callback, String username, String password) {
        return Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (TextUtils.isEmpty(username)) {
                            callback.requestError();//model层里面回调listener
                        } else if (TextUtils.isEmpty(password)) {
                            callback.requestError("");//model层里面回调listener
                        } else {
                            callback.requestSuccess();
                        }
                    }
                });




    }
}
