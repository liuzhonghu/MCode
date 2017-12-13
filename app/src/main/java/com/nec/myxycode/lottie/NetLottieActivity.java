package com.nec.myxycode.lottie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.nec.myxycode.R;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class NetLottieActivity extends AppCompatActivity {
  LottieAnimationView animation_view_network;
  OkHttpClient client;
  OkHttpClient clientWith30sTimeout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_net_lottie);

    animation_view_network = (LottieAnimationView) findViewById(R.id.animation_view_network);
    /**
     * json from link#(https://www.lottiefiles.com/)
     */
    loadUrl(
        "https://www.lottiefiles.com/storage/datafiles/bef3daa39adedbe065d5efad0ae5ccb3/search.json");
  }

  private void loadUrl(String url) {
    Request request;
    try {
      request = new Request.Builder().url(url).build();
    } catch (IllegalArgumentException e) {
      return;
    }

    if (client == null) {
      client = new OkHttpClient();

      clientWith30sTimeout = client.newBuilder().readTimeout(30, TimeUnit.SECONDS).build();
    }
    clientWith30sTimeout.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        Log.d("log_lottie", "load url failure");
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        Log.d("log_lottie", "load url response");
        if (!response.isSuccessful()) {
        }

        try {
          JSONObject json = new JSONObject(response.body().string());
          LottieComposition.Factory.fromJson(getResources(), json,
              new OnCompositionLoadedListener() {
                @Override public void onCompositionLoaded(LottieComposition composition) {
                  setComposition(composition);
                }
              });
        } catch (JSONException e) {
        }
      }
    });
  }

  private void setComposition(LottieComposition composition) {
    animation_view_network.setProgress(0);
    animation_view_network.loop(true);
    animation_view_network.setComposition(composition);
    animation_view_network.playAnimation();
  }

  @Override protected void onStop() {
    super.onStop();
    animation_view_network.cancelAnimation();
  }
}
