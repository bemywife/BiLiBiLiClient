package com.fightzhao.bilibiliclient.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者 ：fightzhao on 2016/12/4 21:49.
 * 功能 :
 */

public class HostType {

    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 3;

    /**
     * 基本的host
     */
    @HostTypeChecker
    public static final int API_BASE_URL = 1;


    @HostTypeChecker
    public static final int MAIN_BASE_URL = 2;


    @HostTypeChecker
    public static final int APP_BASE_URL = 3;

    @HostTypeChecker
    public static final int LIVE_BASE_URL = 4;

    @HostTypeChecker
    public static final int HOST_API_BASE_URL = 5;

    @HostTypeChecker
    public static final int BANGUMI_BASE_URL = 6;

    @HostTypeChecker
    public static final int SEARCH_BASE_URL = 7;

    @HostTypeChecker
    public static final int ACCOUNT_BASE_URL = 8;

    @HostTypeChecker
    public static final int USER_DETAILS_BASE_URL = 9;

    @HostTypeChecker
    public static final int IM9_BASE_URL = 10;


    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({API_BASE_URL, MAIN_BASE_URL, APP_BASE_URL, LIVE_BASE_URL, HOST_API_BASE_URL, BANGUMI_BASE_URL, SEARCH_BASE_URL, ACCOUNT_BASE_URL, USER_DETAILS_BASE_URL, IM9_BASE_URL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }
}
