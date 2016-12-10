package com.fightzhao.bilibiliclient.module.login.presenter;

import com.fightzhao.bilibiliclient.base.BasePresenterImpl;
import com.fightzhao.bilibiliclient.module.login.model.ILoginInterator;
import com.fightzhao.bilibiliclient.module.login.model.ILoginInteratorImpl;
import com.fightzhao.bilibiliclient.module.login.view.LoginView;

import java.util.Date;

/**
 * 作者 ：fightzhao on 2016/11/25 14:07.
 * 功能 :
 */

public class ILoginPresenterImpl extends BasePresenterImpl<LoginView, Date> implements ILoginPresenter {
    private ILoginInterator mLoginInterator;


    public ILoginPresenterImpl(LoginView mView) {
        super(mView);
        mLoginInterator = new ILoginInteratorImpl();

    }


    @Override
    public void requestSuccess() {
        super.requestSuccess();
        if (mView != null)
            mView.navigateToHome();
    }

    @Override
    public void requestError() {
        super.requestError();
        if (mView != null) {
            mView.setUsernameError();
        }
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        if (mView != null) {
            mView.setPasswordError();

        }
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (mView != null)
            mView.showProgress();
        mLoginInterator.login(this, username, password);
    }
}
