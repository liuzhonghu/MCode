package com.nec.myxycode.lottie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.nec.myxycode.R;
import com.nec.baselib.pop.Pop;

public class ALottieActivity extends AppCompatActivity implements View.OnClickListener {

  private Button mLayoutBtn, mCodeBtn, mNetBtn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lottie);

    mLayoutBtn = (Button) findViewById(R.id.btn_layout);
    mCodeBtn = (Button) findViewById(R.id.btn_code);
    mNetBtn = (Button) findViewById(R.id.btn_net);

    mLayoutBtn.setOnClickListener(this);
    mCodeBtn.setOnClickListener(this);
    mNetBtn.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_layout:
        Pop.show(mLayoutBtn);
        this.startActivity(new Intent(this, SimpleLottieActivity.class));
        break;
      case R.id.btn_code:
        Pop.show(mCodeBtn);
        this.startActivity(new Intent(this, CodeLottieActivity.class));
        break;
      case R.id.btn_net:
        Pop.show(mNetBtn);
        this.startActivity(new Intent(this, NetLottieActivity.class));
        break;
    }
  }
}
