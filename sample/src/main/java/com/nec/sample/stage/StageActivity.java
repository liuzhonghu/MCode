package com.nec.sample.stage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.nec.sample.R;
import com.nec.sample.stage.view.StageView;

/**
 * @author 765
 */
public class StageActivity extends AppCompatActivity {

  StageView stageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stage);

    stageView = findViewById(R.id.stage_view);
  }

  public void go(View view) {
    stageView.go();
  }

  public void reset(View view) {

  }
}
