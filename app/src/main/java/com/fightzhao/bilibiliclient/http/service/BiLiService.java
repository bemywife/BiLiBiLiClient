package com.fightzhao.bilibiliclient.http.service;

import com.fightzhao.bilibiliclient.entity.discover.ActivityCenterInfo;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * 作者 ：fightzhao on 2016/12/5 20:00.
 * 功能 : B站Api获取
 */

public interface BiLiService {

    /**
     *  http://api.bilibili.cn/event/getlist?device=phone&mobi_app=iphone&page=1&pagesize=20
     * @param
     * @param
     * @return
     */
    @GET("event/getlist?device=phone&mobi_app=iphone")
    Observable<ActivityCenterInfo> getActivityCenterList(@Header("Cache-Control") String cacheControl);
}
