package com.nec.myxycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.nec.baselib.pop.Pop;
import com.nec.cropper.CropperActivity;
import com.nec.myxycode.architecture.ArchitectureActivity;
import com.nec.myxycode.chat.ChatActivity;
import com.nec.myxycode.draggableRC.DraggableRCActivity;
import com.nec.myxycode.fpsactor.FPSActorActivity;
import com.nec.myxycode.lottie.ALottieActivity;
import com.nec.myxycode.matrix.MatrixActivity;
import com.nec.myxycode.popshow.PopShowActivity;
import com.nec.myxycode.progressbar.ProgressBarActivity;
import com.nec.myxycode.rxbinding.RxBindingActivity;
import com.nec.myxycode.scrollnumber.ScrollNumberActivity;
import com.nec.myxycode.sketch.SketchActivity;
import com.nec.structure.StructureActivity;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void pop(View view) {
    Pop.show(findViewById(R.id.btn_pop));
    this.startActivity(new Intent(this, PopShowActivity.class));
  }

  public void lottie(View view) {
    Pop.show(findViewById(R.id.btn_lottie));
    this.startActivity(new Intent(this, ALottieActivity.class));
  }

  public void progressbar(View view) {
    Pop.show(findViewById(R.id.btn_progressbar));
    this.startActivity(new Intent(this, ProgressBarActivity.class));
  }

  public void scrollNumber(View view) {
    Pop.show(findViewById(R.id.btn_scroll_number));
    this.startActivity(new Intent(this, ScrollNumberActivity.class));
  }

  public void sketch(View view) {
    Pop.show(findViewById(R.id.btn_sticker));
    this.startActivity(new Intent(this, SketchActivity.class));
  }

  public void matrix(View view) {
    Pop.show(findViewById(R.id.btn_matrix));
    this.startActivity(new Intent(this, MatrixActivity.class));
  }

  public void FPSActor(View view) {
    Pop.show(findViewById(R.id.btn_fps));
    this.startActivity(new Intent(this, FPSActorActivity.class));
  }

  public void draggableRCView(View view) {
    Pop.show(findViewById(R.id.btn_drag_rc));
    this.startActivity(new Intent(this, DraggableRCActivity.class));
  }

  public void structure(View view) {
    Pop.show(findViewById(R.id.btn_structure));
    this.startActivity(new Intent(this, StructureActivity.class));
  }

  public void chat(View view) {
    Pop.show(findViewById(R.id.btn_chat));
    this.startActivity(new Intent(this, ChatActivity.class));
  }

  public void arch(View view) {
    Pop.show(findViewById(R.id.btn_arch));
    this.startActivity(new Intent(this, ArchitectureActivity.class));
  }

  public void rxbinding(View view) {
    Pop.show(findViewById(R.id.btn_rxbinding));
    this.startActivity(new Intent(this, RxBindingActivity.class));
  }

  public void cropper(View view) {
    Pop.show(findViewById(R.id.btn_cropper));
    this.startActivity(new Intent(this, CropperActivity.class));
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
