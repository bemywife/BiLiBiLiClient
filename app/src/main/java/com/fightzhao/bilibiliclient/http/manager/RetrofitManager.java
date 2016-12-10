package com.fightzhao.bilibiliclient.http.manager;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.fightzhao.bilibiliclient.app.App;
import com.fightzhao.bilibiliclient.entity.discover.ActivityCenterInfo;
import com.fightzhao.bilibiliclient.http.Api;
import com.fightzhao.bilibiliclient.http.HostType;
import com.fightzhao.bilibiliclient.http.service.BiLiService;
import com.fightzhao.bilibiliclient.utils.NetUtil;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者 ：fightzhao on 2016/12/4 21:27.
 * 功能 :
 */

public class RetrofitManager {

    //设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static final String COMMON_UA_STR = "OhMyBiliBili Android Client/2.1 (100332338@qq.com)";

    private static volatile OkHttpClient mOkHttpClient;

    /**
     * B站
     */
    private BiLiService mBiLiService;

    // 管理不同HostType的单例
    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>(
            HostType.TYPE_COUNT);

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor =  new Interceptor() {
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

    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.json(buffer.clone().readString(charset));
                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };

    private RetrofitManager() {
    }

    /**
     * 获取单例
     *
     * @param hostType host类型
     * @return 实例
     */
    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitManager(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }
    }

    private RetrofitManager(@HostType.HostTypeChecker int hostType) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.getHost(hostType))
                .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        mBiLiService = retrofit.create(BiLiService.class);
    }


    // 配置OkHttpClient
    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "BiLiBiLiCache"),
                            1024 * 1024 * 100);//100M

                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)// 1.配置cache
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(new UserAgentInterceptor())
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .build();

                }
            }
        }
        return mOkHttpClient;
    }

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

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    @NonNull
    private String getCacheControl() {
        return NetUtil.isConnected(App.getInstance()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }

    public Observable<ActivityCenterInfo> getActivityCenterObservable() {
        return mBiLiService.getActivityCenterList(getCacheControl()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }


}
