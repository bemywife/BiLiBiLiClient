package com.fightzhao.bilibiliclient.base;

/**
 * 作者 ：fightzhao on 2016/11/7 16:40.
 * 功能 : 请求数据的基类接口
 */

public interface RequestCallback<D> {
    /**
     * 请求之前调用
     */
    void beforeRequest();

    /**
     * 请求错误调用
     *
     * @param msg
     */
    void requestError(String msg);

    /**
     * 请求错误调用
     *
     */
    void requestError();

    /**
     * 请求完成时调用
     */
    void requestComplete();

    /**
     * 请求成功时候调用
     *
     * @param data 数据
     */
    void requestSuccess(D data);

    /**
     * 请求成功时候调用,无参数
     */
    void requestSuccess();
}
