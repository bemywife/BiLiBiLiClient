package com.fightzhao.bilibiliclient.http.manager;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fightzhao.bilibiliclient.app.App;
import com.fightzhao.bilibiliclient.http.service.BiLiService;
import com.fightzhao.bilibiliclient.utils.NetUtil;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * Retrofit帮助类
 */
public class RetrofitHelper {

    //设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    private static OkHttpClient mOkHttpClient;

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

    static {
        initOkHttpClient();
    }


    /**
     * 获取活动中心数据
     *
     * @return
     */
    public static BiLiService getActivityCenterApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_API_BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BiLiService.class);
    }


    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     * 设置UA拦截器
     */
    private static void initOkHttpClient() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private static Interceptor mRewriteCacheControlInterceptor =  new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isConnected(App.getInstance())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                KLog.e("no network  响应头  " + request.headers().toString());
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isConnected(App.getInstance())) {
                KLog.e(request.headers().toString());

                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
                        .removeHeader("Pragma").build();
            }
        }
    };

    /**
     * 添加UA拦截器
     * B站请求API文档需要加上UA
     */
    private static class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }
}
