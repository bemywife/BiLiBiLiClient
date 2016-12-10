package com.fightzhao.bilibiliclient.base;

import rx.Subscription;

/**
 * 作者 ：fightzhao on 2016/11/7 18:30.
 * 功能 : Presenter的实现类，必须持有View和RequestCallback,将数据在不同情况下的显示状态展示为具体的View
 */

public class BasePresenterImpl<V extends BaseView, D> implements BasePresenter, RequestCallback<D> {

    protected Subscription mSubscription;
    protected V mView;

    public BasePresenterImpl(V mView) {
        this.mView = mView;
    }

    @Override
    public void onResume() {

    }

    /**
     * 清理，防止内存泄露
     */
    @Override
    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void requestError(String msg) {
        mView.toast(msg);
        mView.hideProgress();
    }

    @Override
    public void requestError() {
        mView.hideProgress();
    }

    @Override
    public void requestComplete() {
        mView.hideProgress();
    }

    @Override
    public void requestSuccess(D data) {

    }

    @Override
    public void requestSuccess() {
        mView.hideProgress();
    }


}
