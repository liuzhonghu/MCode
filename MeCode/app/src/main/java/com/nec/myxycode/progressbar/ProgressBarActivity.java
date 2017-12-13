package com.nec.myxycode.progressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.nec.myxycode.R;

public class ProgressBarActivity extends AppCompatActivity {
  private SegProgressbar mProgressbarView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_progress_bar);

    mProgressbarView = ((SegProgressbar) findViewById(R.id.progressbar));
    //        mProgressbarView.setProgress(progress += 10);//  ,true);
    //        mProgressbarView.setProgress(progress += 10);//  ,true);
    //        mProgressbarView.setProgress(progress += 10);//  ,true);

    mProgressbarView.setMax(100);
  }

  public void startProgress(View view){
    mProgressbarView.onCountStart();
  }

  public void delProgress(View view) {
    //        if(progress > 0){
    //            progress -= 10;
    //            mProgressbarView.removeProgress(progress);
    //        }
    mProgressbarView.removeProgress();
  }

  public void addProgress(View view) {
    //        if(progress < 100){
    //            mProgressbarView.setProgress(progress += 10);// ,true);
    //        }
    mProgressbarView.onCountPause();
  }

  public void resumeProgress(View view) {
    mProgressbarView.onCountResume();
  }

  @Override protected void onStop() {
    super.onStop();
    mProgressbarView.onCountDestroy();
  }
}
