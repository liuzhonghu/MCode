package com.nec.myxycode.matrix;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.nec.kboardlib.util.BitmapUtils;
import com.nec.myxycode.R;

public class MatrixActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_matrix);
    findViewById(R.id.btnBase).setOnClickListener(this);
    findViewById(R.id.btnRectToRect).setOnClickListener(this);
    findViewById(R.id.btnPolyToPoly).setOnClickListener(this);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnBase:
        startActivity(new Intent(this, MatrixBaseActivity.class));
        break;
      case R.id.btnPolyToPoly:
        startActivity(new Intent(this, PolyToPolyActivity.class));
        break;
      case R.id.btnRectToRect:
        startActivity(new Intent(this, RectToRectActivity.class));
        break;
    }
  }
}
