package com.luzuzu.library.http;

import com.luzuzu.library.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fula on 2019/3/7.
 */

public class RetrofitWrapper {

    private Retrofit mRetrofit;
    private final OkHttpClient.Builder builder;

    /**
     * 创建Retrofit
     * @param url               baseUrl
     */
    public RetrofitWrapper(String url) {
        builder = new OkHttpClient.Builder();
        //拦截日志，依赖
        builder.addInterceptor(InterceptorUtils.getHttpLoggingInterceptor(BuildConfig.DEBUG));
        OkHttpClient build = builder.build();
        //添加请求头拦截器
        //builder.addInterceptor(InterceptorUtils.getRequestHeader());
        //添加统一请求拦截器
        //builder.addInterceptor(InterceptorUtils.commonParamsInterceptor());
        //设置缓存
        builder.addNetworkInterceptor(InterceptorUtils.getCacheInterceptor());
        builder.addInterceptor(InterceptorUtils.getCacheInterceptor());
        initBuilder(url,build);
    }


    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 创建Retrofit
     * @param url               baseUrl
     */
    public RetrofitWrapper(String url , ArrayList<Interceptor> interceptors) {
        builder = new OkHttpClient.Builder();
        //拦截日志，依赖
        builder.addInterceptor(InterceptorUtils.getHttpLoggingInterceptor(BuildConfig.DEBUG));
        OkHttpClient build = builder.build();
        builder.addInterceptor(InterceptorUtils.getCacheInterceptor());
        if (interceptors!=null && interceptors.size()>0){
            for (Interceptor interceptor : interceptors){
                builder.addInterceptor(interceptor);
            }
        }
        //添加自定义CookieJar
        InterceptorUtils.addCookie(builder);
        initBuilder(url,build);
    }


    private void initBuilder(String url, OkHttpClient build) {
        initSSL();
        initTimeOut();
        if(BuildConfig.DEBUG){
            //不需要错误重连
            builder.retryOnConnectionFailure(false);
        }else {
            //错误重连
            builder.retryOnConnectionFailure(true);
        }
        //获取实例
        mRetrofit = new Retrofit
                //设置OKHttpClient,如果不设置会提供一个默认的
                .Builder()
                //设置baseUrl
                .baseUrl(url)
                //添加转换器Converter(将json 转为JavaBean)，用来进行响应数据转化(反序列化)的ConvertFactory
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getJson()))
                //添加自定义转换器
                //.addConverterFactory(buildGsonConverterFactory())
                //添加rx转换器，用来生成对应"Call"的CallAdapter的CallAdapterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build)
                .build();
    }

    /**
     * 初始化完全信任的信任管理器
     */
    @SuppressWarnings("deprecation")
    private void initSSL() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取实例，使用单利模式
     * 这里传递url参数，是因为项目中需要访问不同基类的地址
     * @param url               baseUrl
     * @return                  实例对象
     */
    public static RetrofitWrapper getInstance(String url){
        //synchronized 避免同时调用多个接口，导致线程并发
        RetrofitWrapper instance;
        synchronized (RetrofitWrapper.class){
            instance = new RetrofitWrapper(url);
        }
        return instance;
    }


    /**
     * 设置读取超时时间，连接超时时间，写入超时时间值
     */
    private void initTimeOut() {
        builder.readTimeout(20000, TimeUnit.SECONDS);
        builder.connectTimeout(10000, TimeUnit.SECONDS);
        builder.writeTimeout(20000, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
    }
}
