package com.fightzhao.bilibiliclient.module.login.model;

import com.fightzhao.bilibiliclient.base.RequestCallback;

import rx.Subscription;

/**
 * 作者 ：fightzhao on 2016/11/25 13:59.
 * 功能 :
 */

public interface ILoginInterator<D> {
    Subscription login(RequestCallback<D> data, String username, String password);

}
