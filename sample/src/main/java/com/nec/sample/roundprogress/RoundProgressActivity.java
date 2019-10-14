package com.nec.sample.roundprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.nec.sample.R;

public class RoundProgressActivity extends AppCompatActivity {

  RoundProgressView mRoundProgressView;
  private int power;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_round_progress);

    mRoundProgressView = findViewById(R.id.round_Progress);
    mRoundProgressView.postDelayed(mAction, 500);
  }

  private Runnable mAction = new Runnable() {
    @Override public void run() {
      int pow = power++ % 100;
      mRoundProgressView.setProgress(pow);
      mRoundProgressView.postDelayed(mAction, 100);
    }
  };
}
