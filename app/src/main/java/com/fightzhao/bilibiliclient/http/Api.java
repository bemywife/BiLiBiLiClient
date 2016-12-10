package com.fightzhao.bilibiliclient.http;

/**
 * 作者 ：fightzhao on 2016/12/5 20:26.
 * 功能 :
 */

public class Api {
    private static final String API_BASE_URL = "http://bilibili-service.daoapp.io/";

    private static final String MAIN_BASE_URL = "http://www.bilibili.com/";

    private static final String APP_BASE_URL = "http://app.bilibili.com/";

    private static final String LIVE_BASE_URL = "http://live.bilibili.com/";

    private static final String HOST_API_BASE_URL = "http://api.bilibili.cn/";

    private static final String BANGUMI_BASE_URL = "http://bangumi.bilibili.com/";

    private static final String SEARCH_BASE_URL = "http://s.search.bilibili.com/";

    private static final String ACCOUNT_BASE_URL = "https://account.bilibili.com/";

    private static final String USER_DETAILS_BASE_URL = "http://space.bilibili.com/";

    private static final String IM9_BASE_URL = "http://www.im9.com/";

    public static final String HDSLB_HOST = "http://i2.hdslb.com";

    private static final String COMMON_UA_STR = "OhMyBiliBili Android Client/2.1 (100332338@qq.com)";


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        switch (hostType) {
            case HostType.API_BASE_URL:
                return Api.API_BASE_URL;

            case HostType.MAIN_BASE_URL:
                return Api.MAIN_BASE_URL;

            case HostType.APP_BASE_URL:
                return Api.APP_BASE_URL;

            case HostType.LIVE_BASE_URL:
                return Api.LIVE_BASE_URL;

            case HostType.HOST_API_BASE_URL:
                return Api.HOST_API_BASE_URL;

            case HostType.BANGUMI_BASE_URL:
                return Api.BANGUMI_BASE_URL;

            case HostType.SEARCH_BASE_URL:
                return Api.SEARCH_BASE_URL;

            case HostType.ACCOUNT_BASE_URL:
                return Api.ACCOUNT_BASE_URL;
        }
        return "";
    }

}
