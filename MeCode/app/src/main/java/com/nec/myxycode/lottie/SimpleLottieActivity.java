package com.nec.myxycode.lottie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.nec.myxycode.R;

public class SimpleLottieActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // activity_simple.xml中 lottie_fileName="xxx.json"
    // 所以只需要在 app/src/main/assets 中添加AE 生成的 json文件，重命名为xxx.json就可以显示动画
    setContentView(R.layout.activity_simple);
  }
}
