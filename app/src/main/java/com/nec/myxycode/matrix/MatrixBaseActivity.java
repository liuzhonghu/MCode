package com.nec.myxycode.matrix;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.nec.myxycode.R;
import com.nec.myxycode.matrix.widget.TransformMatrixView;
import com.nec.baselib.pop.Pop;

public class MatrixBaseActivity extends AppCompatActivity implements View.OnClickListener {

  private TransformMatrixView ivTest;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_matrix_base);
    findViewById(R.id.btnHosSkew).setOnClickListener(this);
    findViewById(R.id.btnVerSkew).setOnClickListener(this);
    findViewById(R.id.btnComplexSkew).setOnClickListener(this);
    findViewById(R.id.btnRotate).setOnClickListener(this);
    findViewById(R.id.btnHosScale).setOnClickListener(this);
    findViewById(R.id.btnVerScale).setOnClickListener(this);
    findViewById(R.id.btnZoom).setOnClickListener(this);
    findViewById(R.id.btnTranslate).setOnClickListener(this);
    findViewById(R.id.btnReset).setOnClickListener(this);
    ivTest = (TransformMatrixView) findViewById(R.id.ivTest);
  }

  @Override public void onClick(View v) {
    Pop.showSoftly(v);
    switch (v.getId()) {
      case R.id.btnHosSkew:
        ivTest.setHosSkew();
        break;

      case R.id.btnVerSkew:
        ivTest.setVerSkew();
        break;

      case R.id.btnComplexSkew:
        ivTest.setComplexSkew();
        break;

      case R.id.btnRotate:
        ivTest.setRotate();
        break;

      case R.id.btnHosScale:
        ivTest.setHosScale();
        break;

      case R.id.btnVerScale:
        ivTest.setVerScale();
        break;

      case R.id.btnZoom:
        ivTest.setZoom();
        break;

      case R.id.btnTranslate:
        ivTest.setTranslate();
        break;
      case R.id.btnReset:
        ivTest.setReset();
        break;
    }
  }
}
