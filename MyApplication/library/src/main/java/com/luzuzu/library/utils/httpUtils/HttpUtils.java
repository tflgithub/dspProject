package com.luzuzu.library.utils.httpUtils;


import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fula on 2019/3/7.
 */

public class HttpUtils {

    private static OkHttpClient okHttpClient = null;

    private HttpUtils() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //加同步安全
            synchronized (HttpUtils.class) {
                if (okHttpClient == null) {
                    File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
                    int cacheSize = 10 * 1024 * 1024;
                    okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).cache(new Cache(sdcache.getAbsoluteFile(), cacheSize)).build();
                }
            }

        }
        return okHttpClient;
    }


    /**
     * 上传文件，post请求
     *
     * @param url      请求地址
     * @param pathName 文件路径名称
     * @param fileName 文件名
     * @param callback callback
     */
    public static void postUploadFile(String url, String pathName, String fileName, Callback callback) {
        //判断文件类型
        MediaType mediaType = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder;
        if (mediaType != null) {
            builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(mediaType.type(), fileName,
                            RequestBody.create(mediaType, new File(pathName)));
            //发出请求参数
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            Call call = getInstance().newCall(request);
            call.enqueue(callback);
        }
    }


    /**
     * 下载文件
     *
     * @param url      请求地址
     * @param fileDir  父路径名字符串
     * @param fileName 子路径名的字符串
     */
    public static void downFile(String url, final String fileDir, final String fileName) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    //noinspection ConstantConditions
                    is = response.body().byteStream();
                    File file = new File(fileDir, fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        });

    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path 文件路径
     * @return 返回字符串类型的文件类型
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * get请求
     * 参数1 url
     * 参数2 回调Callback
     */
    public static  void doGet(String url, Callback callback) {
        //创建Request
        Request request = new Request.Builder().url(url).build();
        //得到Call对象
        Call call = getInstance().newCall(request);
        //执行异步请求
        call.enqueue(callback);
    }
}
