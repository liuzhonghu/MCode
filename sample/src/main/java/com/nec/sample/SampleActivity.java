package com.nec.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.nec.baselib.pop.Pop;
import com.nec.sample.stage.StageActivity;

/**
 * @author 765
 */
public class SampleActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
  }

  public void stage(View view) {
    Pop.show(findViewById(R.id.btn_stage));
    this.startActivity(new Intent(this, StageActivity.class));
  }
}
