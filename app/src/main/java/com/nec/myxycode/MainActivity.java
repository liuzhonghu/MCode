package com.nec.myxycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.nec.myxycode.lottie.ALottieActivity;
import com.nec.myxycode.pop.Pop;
import com.nec.myxycode.pop.PopShowActivity;
import com.nec.myxycode.progressbar.ProgressBarActivity;
import com.nec.myxycode.scrollnumber.ScrollNumberActivity;
import com.nec.myxycode.sketch.SketchActivity;

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
