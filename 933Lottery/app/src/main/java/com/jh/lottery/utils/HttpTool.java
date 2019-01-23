package com.jh.lottery.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.jh.lottery.view.XToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class HttpTool {

    public static String TAG="debug-okhttp";
    public static boolean isDebug=true;

    private OkHttpClient client;
    // 超时时间
    public static final int TIMEOUT = 1000 * 60;

    //json请求
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Handler handler = new Handler(Looper.getMainLooper());

    public HttpTool() {
        // TODO Auto-generated constructor stub
        this.init();
    }

    private void init() {

        client = new OkHttpClient();
        // 设置超时时间
        client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).addInterceptor(getInterceptor())
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();

    }

    public static void GET(String url, Map map, HttpCallback callback){
        HttpTool httpTool = new HttpTool();
        httpTool.get(url,map,callback);
    }
    public static void POST(String url, Map map, HttpCallback callback){
        HttpTool httpTool = new HttpTool();
        httpTool.post(url,map,callback);
    }

    public static void getHttpFile(String url, Map<String, String> params, List<File> mImgUrls, HttpCallback callback){
        HttpTool httpTool = new HttpTool();
        httpTool.doPostFile(url,params,mImgUrls,callback);
    }

    /**
     * post请求，json数据为body
     *
     * @param url
     * @param json
     * @param callback
     */
    public void postJson(String url, String json, final HttpCallback callback) {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        onStart(callback);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                onError(callback, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }
        });
    }

    /**
     * post提交文件
     * @param url
     * @param params
     * @param callback
     */
    public void doPostFile(final String url, Map<String, String> params, List<File> mImgUrls,
                           final HttpCallback callback) {
        Log.i("url",url);
        onStart(callback);
        try{
            MediaType mediaType = MediaType.parse("image/*");
            // mImgUrls为存放图片的url集合
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i <mImgUrls.size() ; i++) {
                File file= mImgUrls.get(i);
                if (file != null && file.exists()) {
                    builder.addFormDataPart("files", file.getName(), RequestBody.create(mediaType, file));
                }
            }
            if (params.size() > 0) {
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    builder.addFormDataPart(key, value);
                }
            }
            MultipartBody requestBody = builder.build();
            //构建请求
            Request request = new Request.Builder()
                    .url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                onError(callback, e.getMessage());
                            }
                        }
                    });
                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String str = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                onSuccess(callback, str);
                            } else {
                                onError(callback, response.message());
                            }
                        }
                    });
                }
            });
        }catch (Exception e) {
            // TODO: handle exception
            onError(callback, e.getMessage());
        }
    }

    /**
     * post请求 map为body
     *
     * @param url
     * @param map
     * @param callback
     */
    public void post(String url, Map<String, Object> map, final HttpCallback callback) {


        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 遍历key
         */
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder().url(url).post(body).build();

        onStart(callback);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                onError(callback, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    onSuccess(callback, response.body().string());

                } else {
                    onError(callback, response.message());
                }
            }
        });

    }
    public String getUrl(String urls, Map<String, String> params) {
        String url = urls;
        //添加url参数
        if(params !=null) {

            Iterator it = params.keySet().iterator();
            StringBuffer sb =null;
            while(it.hasNext()) {
                String key = it.next().toString();
                String value = params.get(key);
                if(sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                }else{
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            if (null != sb){
                url += sb.toString();
            }
        }
        return url;
    }
    /**
     * get请求
     * @param url
     * @param map
     * @param callback
     */
    private void get(String url, Map<String, String> map, final HttpCallback callback) {

        url = getUrl(url,map);
        Log.i("url",url);
        Request request = new Request.Builder().url(url).build();
        onStart(callback);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(callback, e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }
        });
    }
    /**
     * log信息打印
     * @param params
     */
    public void debug(String params){
        if(false == isDebug){
            return;
        }
        if(null == params){
            Log.d(TAG, "params is null");
        }else{
            Log.d(TAG, params);
        }
    }

    private void onStart(HttpCallback callback) {
        if (null != callback) {
            callback.onStart();
        }
    }

    private void onSuccess(final HttpCallback callback, final String data) {
        debug(data);
        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if(jsonObject.getInt("retCode") == 0 || jsonObject.getInt("retCode") == 203){
                            callback.onSuccess(jsonObject);
                        }else {
                            String msg = jsonObject.getString("message");
                            XToast.error(msg);
                            callback.onError(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        XToast.error("未知异常!");
                        callback.onError("");
                    }
                }
            });
        }
    }

    private void onError(final HttpCallback callback,final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onError(msg);
                }
            });
        }
    }


    private Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
    /**
     * http请求回调
     *
     * @author Flyjun
     *
     */
    public static abstract class HttpCallback {
        // 开始
        public void onStart() {};
        // 成功回调
        public abstract void onSuccess(JSONObject json);

        // 失败回调
        public void onError(String msg) {
            System.out.println("HttpCallback onError msg:"+msg);
        };
    }


}
