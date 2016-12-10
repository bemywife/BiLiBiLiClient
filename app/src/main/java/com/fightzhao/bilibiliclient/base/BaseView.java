package com.fightzhao.bilibiliclient.base;

/**
 * 作者 ：fightzhao on 2016/11/7 16:37.
 * 功能 : 视图的基类接口
 */

public interface BaseView {

    /**
     * 吐司
     *
     * @param msg
     */
    void toast(String msg);

    /**
     * snackbar
     * @param msg
     */
    void snackbar(String msg);

    /**
     * 显示加载框
     */
    void showProgress();

    /**
     * 隐藏加载框
     */
    void hideProgress();

    /**
     * 请求失败的处理显示
     */
    void showRequest();
}
