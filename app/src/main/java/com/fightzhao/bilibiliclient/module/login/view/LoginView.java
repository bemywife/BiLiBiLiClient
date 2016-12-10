package com.fightzhao.bilibiliclient.module.login.view;

import com.fightzhao.bilibiliclient.base.BaseView;

/**
 * 作者 ：fightzhao on 2016/11/25 13:57.
 * 功能 : Login的View
 */

public interface LoginView extends BaseView{
    void setUsernameError();

    void setPasswordError();

    void navigateToHome();
}
