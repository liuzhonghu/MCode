package com.nec.myxycode.popshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.nec.baselib.pop.Pop;
import com.nec.myxycode.R;

public class PopShowActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pop_show);

    findViewById(R.id.normal).setOnClickListener(this);
    findViewById(R.id.normal_with_callback).setOnClickListener(this);
    findViewById(R.id.soft).setOnClickListener(this);
    findViewById(R.id.deep_soft).setOnClickListener(this);
    findViewById(R.id.none_show).setOnClickListener(this);
    findViewById(R.id.soft_none_show).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.normal:
        Pop.show(findViewById(R.id.normal));
        break;
      case R.id.normal_with_callback:
        Pop.show(findViewById(R.id.normal_with_callback), new Pop.Callback() {
          @Override public void onFinish() {
            Toast.makeText(PopShowActivity.this, "哈哈哈哈哈", Toast.LENGTH_LONG).show();
          }
        });
        break;
      case R.id.soft:
        Pop.showSoftly(findViewById(R.id.soft));
        break;
      case R.id.deep_soft:
        Pop.showDeepSoftly(findViewById(R.id.deep_soft));
        break;
      case R.id.none_show:
        Pop.none2Show(findViewById(R.id.none_show));
        break;
      case R.id.soft_none_show:
        Pop.none2ShowDeepSoftly(findViewById(R.id.soft_none_show));
        break;
    }
  }
}
