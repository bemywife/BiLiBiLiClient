package com.fightzhao.bilibiliclient.module.login.presenter;

import com.fightzhao.bilibiliclient.base.BasePresenter;

/**
 * 作者 ：fightzhao on 2016/11/25 14:07.
 * 功能 :
 */

public interface ILoginPresenter extends BasePresenter {
    void validateCredentials(String username, String password);
}
