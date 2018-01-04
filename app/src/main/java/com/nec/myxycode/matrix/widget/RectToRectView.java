package com.nec.myxycode.matrix.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.nec.myxycode.R;

public class RectToRectView extends View {

  private static final String TAG = "RectToRectView";
  private Paint mPaint;
  private float cellSize = 0;
  private float cellHorizontalOffset = 0;
  private float cellVerticalOffset = 0;
  private Matrix mRectMatrix;         // 测试etRectToRect用的Matrix
  float textSize = getResources().getDimensionPixelSize(R.dimen.textSize);

  private Matrix.ScaleToFit[] scaleToFit = {
      Matrix.ScaleToFit.FILL, Matrix.ScaleToFit.FILL, Matrix.ScaleToFit.FILL,
      Matrix.ScaleToFit.FILL, Matrix.ScaleToFit.START, Matrix.ScaleToFit.START,
      Matrix.ScaleToFit.START, Matrix.ScaleToFit.START, Matrix.ScaleToFit.CENTER,
      Matrix.ScaleToFit.CENTER, Matrix.ScaleToFit.CENTER, Matrix.ScaleToFit.CENTER,
      Matrix.ScaleToFit.END, Matrix.ScaleToFit.END, Matrix.ScaleToFit.END, Matrix.ScaleToFit.END
  };

  private Bitmap[] bitmaps = {
      drawable2Bitmap(getResources().getDrawable(R.drawable.red_oval)),
      drawable2Bitmap(getResources().getDrawable(R.drawable.green_oval)),
      drawable2Bitmap(getResources().getDrawable(R.drawable.blue_round)),
      drawable2Bitmap(getResources().getDrawable(R.drawable.black_round)),
  };

  private String[] sLabels = {
      "FILL", "FILL", "FILL", "FILL", "START", "START", "START", "START", "CENTER", "CENTER",
      "CENTER", "CENTER", "END", "END", "END", "END"
  };

  public RectToRectView(Context context) {
    this(context, null);
  }

  public RectToRectView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RectToRectView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mRectMatrix = new Matrix();
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setTextSize(textSize);
    mPaint.setTextAlign(Paint.Align.CENTER);
    mPaint.setStrokeWidth(2);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    cellSize = w / 5f;
    cellHorizontalOffset = cellSize / 6;
    cellVerticalOffset = cellSize * 0.5f;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawARGB(255, 139, 197, 186);

    int canvasWidth = canvas.getWidth();
    int canvasHeight = canvas.getHeight();

    for (int row = 0; row < 5; row++) {
      for (int column = 0; column < 4; column++) {
        canvas.save();
        int layer = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        float translateX = (cellSize + cellHorizontalOffset) * column;
        float translateY = (cellSize + cellVerticalOffset) * row;
        canvas.translate(translateX, translateY);
        RectF src = new RectF(0, 0, bitmaps[column].getWidth(), bitmaps[column].getHeight());

        // 根据Matrix绘制一个变换后的图片
        if (row != 0) {
          //画文字
          int index = (row - 1) * 4 + column;
          String text = sLabels[index];
          mPaint.setColor(Color.BLACK);
          float textXOffset = cellSize / 2;
          float textYOffset = textSize + (cellVerticalOffset - textSize) / 2;
          canvas.drawText(text, textXOffset, textYOffset, mPaint);
          canvas.translate(0, cellVerticalOffset);
          //画边框
          mPaint.setStyle(Paint.Style.STROKE);
          mPaint.setColor(0xff000000);
          canvas.drawRect(2, 2, cellSize - 2, cellSize - 2, mPaint);
          mPaint.setStyle(Paint.Style.FILL);
          //目标矩形
          RectF dst = new RectF(2, 2, cellSize - 2, cellSize - 2);
          mRectMatrix.setRectToRect(src, dst, scaleToFit[index]);
        }
        canvas.drawBitmap(bitmaps[column], mRectMatrix, new Paint());
        canvas.restore();
        canvas.restoreToCount(layer);
      }
    }
  }

  /**
   * drawable转bitmap
   */

  private Bitmap drawable2Bitmap(Drawable drawable) {
    try {
      if (drawable == null) {
        return null;
      }
      if (drawable instanceof BitmapDrawable) {
        return ((BitmapDrawable) drawable).getBitmap();
      }
      int intrinsicWidth = drawable.getIntrinsicWidth();
      int intrinsicHeight = drawable.getIntrinsicHeight();
      Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth <= 0 ? getWidth() : intrinsicWidth,
          intrinsicHeight <= 0 ? getHeight() : intrinsicHeight, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      canvas.drawARGB(0, 0, 0, 0);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);
      return bitmap;
    } catch (OutOfMemoryError e) {
      return null;
    }
  }
}