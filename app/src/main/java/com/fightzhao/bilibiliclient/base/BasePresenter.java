package com.fightzhao.bilibiliclient.base;

/**
 * 作者 ：fightzhao on 2016/11/7 16:42.
 * 功能 : 协调器的基类
 */

public interface BasePresenter {
    /**
     * 重新开始
     */
    void onResume();

    /**
     * 刷新
     */
    void onDestroy();
}
