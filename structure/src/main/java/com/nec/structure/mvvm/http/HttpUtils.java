package com.nec.structure.mvvm.http;

import com.nec.structure.mvvm.bean.NewsBean;
import com.nec.structure.mvvm.constant.URLConstant;
import com.nec.structure.mvvm.retrofitinterface.RetrofitInterface;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

  private static final int DEFAULT_TIMEOUT = 8; //连接 超时的时间，单位：秒
  private static final OkHttpClient client = new OkHttpClient.Builder().
      connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
      readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
      writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).build();
  private static HttpUtils httpUtils;
  private static Retrofit retrofit;
  private static RetrofitInterface retrofitInterface;

  private synchronized static RetrofitInterface getRetrofit() {
    //初始化retrofit的配置
    if (retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl(URLConstant.URL_BASE)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
      retrofitInterface = retrofit.create(RetrofitInterface.class);
    }
    return retrofitInterface;
  }

  //获取新闻数据
  public static Observable<NewsBean> getNewsData() {
    return getRetrofit().getNewsData();
  }
}
