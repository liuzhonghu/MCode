package com.nec.myxycode.fpsactor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import com.nec.baselib.pop.Pop;
import com.nec.fpsactor.FPSActor;
import com.nec.myxycode.AppComponent;
import com.nec.myxycode.KApplication;
import com.nec.myxycode.R;

public class FPSActorActivity extends AppCompatActivity {
  FPSRecyclerView recyclerView;
  SeekBar loadIndicator;

  private AppComponent component;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fps_actor);

    recyclerView = findViewById(R.id.recyclerView);
    loadIndicator = findViewById(R.id.loadIndicator);

    KApplication application = (KApplication) getApplication();
    component = application.getComponent();
    component.inject(this);

    setupRadioGroup();
    FPSActor.create().show(getApplicationContext());
  }

  private void setupRadioGroup() {

    recyclerView.setMegaBytes(50f);
    recyclerView.notifyDataSetChanged();

    loadIndicator.setProgress(50);
    loadIndicator.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        recyclerView.setMegaBytes(Float.valueOf(i));
        recyclerView.notifyDataSetChanged();
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }

  public void start(View view) {
    Pop.show(view);
    FPSActor.create().show(getApplicationContext());
  }

  public void stop(View view) {
    Pop.show(view);
    FPSActor.hide(getApplicationContext());
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    component = null;
  }
}
