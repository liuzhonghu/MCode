package com.nec.myxycode.fpsactor;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nec.fpsactor.FPSActor;
import com.nec.myxycode.AppComponent;
import com.nec.myxycode.KApplication;
import com.nec.myxycode.R;

public class FPSActorActivity extends AppCompatActivity {
  @Bind(R.id.recyclerView) FPSRecyclerView recyclerView;
  @Bind(R.id.loadIndicator) SeekBar loadIndicator;

  private AppComponent component;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fps_actor);

    KApplication application = (KApplication) getApplication();
    component = application.getComponent();
    component.inject(this);
    ButterKnife.bind(this);

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

  @OnClick(R.id.start) public void start() {
    FPSActor.create().show(getApplicationContext());
  }

  @OnClick(R.id.stop) public void stop() {
    FPSActor.hide((Application) getApplicationContext());
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    component = null;
    ButterKnife.unbind(this);
  }
}
