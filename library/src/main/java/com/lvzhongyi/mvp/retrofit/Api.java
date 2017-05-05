package com.lvzhongyi.mvp.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiongbull.jlog.JLog;
import com.lvzhongyi.mvp.BaseApp;
import com.lvzhongyi.mvp.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
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
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/7/23
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public class Api {
    public static final String X_LC_Id = "........";
    public static final String X_LC_Key = "............";
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    Interceptor mInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
            .addHeader("X-LC-Id", X_LC_Id)
            .addHeader("X-LC-Key", X_LC_Key)
            .addHeader("Content-Type", "application/json")
            .build());
    private OkHttpClient okHttpClient;

    /**
     * private constructor suppresses
     */
    private Api() {
        //拦截器用于记录应用中的网络请求的信息。
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(BaseApp.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 99999999 * 2048);//100M
        okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(mInterceptor)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();
    }

    private static class HolderClass {
        private final static Api instance = new Api();
    }

    public static final int RETROFIT_TYPE_FORMAT_DATA = 1;//格式化日期
    public static final int RETROFIT_TYPE_WRAP_ROOT = 2;//去掉根元素
    public static final int RETROFIT_TYPE_ALL = 3;//

    /**
     * get this current instance
     *
     * @return this {@link HolderClass#instance }
     */
    public static Api getInstance() {
        return HolderClass.instance;
    }

    public Retrofit getRetrofit(String baseUrl, int type) {
        synchronized (Api.class) {
            Retrofit retrofit = retrofitMap.get(baseUrl + type);
            if (retrofit == null) {
                synchronized (Api.class) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    if ((type & 1) == 1) {
                        //日期格式
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-DD'T'HH:MM:ss");
                        objectMapper.setDateFormat(dateFormat);
                    }
                    if ((type & 2) == 2) {
                        //环绕根元素
                        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
                    }
                    retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(baseUrl)
                            .build();
                    retrofitMap.put(baseUrl + type, retrofit);
                }
            }
            return retrofit;
        }
    }


    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isConnected(BaseApp.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                JLog.d("Okhttps", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (!NetUtils.isConnected(BaseApp.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cacho-Contorl", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cacho-Contorl", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
