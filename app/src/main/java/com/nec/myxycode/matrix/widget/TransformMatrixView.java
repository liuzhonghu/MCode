package com.nec.myxycode.matrix.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.nec.kboardlib.util.BitmapUtils;
import com.nec.myxycode.R;
import com.nec.myxycode.matrix.utils.CanvasAidUtils;
import java.util.Random;

@SuppressLint("AppCompatCustomView") public class TransformMatrixView extends ImageView {

  private static final String TAG = TransformMatrixView.class.getSimpleName();

  private Paint mPaint; //  画笔
  private Bitmap mBitmap;   //  图片位图

  private int mBitmapWidth;   //  图片的宽度
  private int mBitmapHeight;  //  图片的高度

  private Matrix mMatrix;
  private float[] mPoints;
  private float[] mOriginPoints;

  private static final int NONE = 0;
  private static final int DRAG = 1;
  private static final int ZOOM = 2;
  int mode = NONE;
  private float oldDistance;
  private float oldRotation;
  private PointF oldCoordinate;

  public TransformMatrixView(Context context) {
    this(context, null);
  }

  public TransformMatrixView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TransformMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    obtainStyledAttributes(attrs);
    init();
  }

  private void obtainStyledAttributes(AttributeSet attrs) {
    TypedArray mTypedArray =
        getContext().obtainStyledAttributes(attrs, R.styleable.TransformMatrixView);
    Drawable srcDrawable = mTypedArray.getDrawable(R.styleable.TransformMatrixView_trans_image);

    mBitmap = drawable2Bitmap(srcDrawable);
    mBitmapWidth = mBitmap.getWidth();
    mBitmapHeight = mBitmap.getHeight();
    mOriginPoints = new float[] {
        0, 0, mBitmapWidth, 0, mBitmapWidth, mBitmapHeight, 0, mBitmapHeight, mBitmapWidth / 2,
        mBitmapHeight / 2
    };
    mTypedArray.recycle();
  }

  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStrokeWidth(3);
    mPaint.setStyle(Paint.Style.STROKE);
    mMatrix = new Matrix();
    mPoints = new float[10];

    oldCoordinate = new PointF();
  }

  //  水平错切
  public void setHosSkew() {
    mMatrix.setSkew(0.5f, 0f);
    //        mMatrix.setSkew(0.5f, 0f, mPoints[8], mPoints[9]);
    invalidate();
  }

  //  垂直错切
  public void setVerSkew() {
    mMatrix.setSkew(0f, 0.5f);
    //        mMatrix.setSkew(0f, 0.5f, mPoints[8], mPoints[9]);
    invalidate();
  }

  //复合错切
  public void setComplexSkew() {
    mMatrix.setSkew(0.5f, 0.5f);
    //        mMatrix.setSkew(0.5f, 0.5f ,mPoints[8], mPoints[9]);
    invalidate();
  }

  //旋转
  public void setRotate() {
    mMatrix.postRotate(30); //绕左上角旋转
    //        mMatrix.postRotate(30, mPoints[0], mPoints[1]); //绕左上角旋转
    //        mMatrix.postRotate(30, mPoints[8], mPoints[9]); //绕中心旋转
    invalidate();
  }

  //水平镜像
  public void setHosScale() {
    //        mMatrix.setScale(-1, 1);
    mMatrix.setScale(-0.5f, 0.5f);
    //        mMatrix.setScale(-0.5f, 0.5f, mPoints[8], mPoints[9]);//以中心点水平镜像
    invalidate();
  }

  //垂直镜像
  public void setVerScale() {
    //        mMatrix.setScale(1, -1);
    mMatrix.setScale(0.5f, -0.5f);
    //        mMatrix.setScale(0.5f, -0.5f, mPoints[8], mPoints[9]);//以中心点垂直镜像
    invalidate();
  }

  //放大缩小
  public void setZoom() {
    float[] b = { 2.f, 1.2f, .8f, .5f };
    Random rand = new Random();
    int num = rand.nextInt(4);
    mMatrix.postScale(b[num], b[num]);
    invalidate();
  }

  //水平移动
  public void setTranslate() {
    mMatrix.postTranslate(20, 20);
    invalidate();
  }

  //重置
  public void setReset() {
    mMatrix.reset();
    invalidate();
  }

  private void printLog() {
    float[] matrixValues = new float[9];
    mMatrix.getValues(matrixValues);
    for (int i = 0; i < 3; ++i) {
      String temp = new String();
      for (int j = 0; j < 3; ++j) {
        temp += matrixValues[3 * i + j] + "\t";
      }
      Log.d(TAG, "矩阵 = " + temp);
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mBitmap == null) return;
    canvas.translate(200, 200);
    // 绘制坐标系
    CanvasAidUtils.set2DAxisLength(500, 0, 600, 0);
    CanvasAidUtils.draw2DCoordinateSpace(canvas);

    mMatrix.mapPoints(mPoints, mOriginPoints);
    canvas.drawRect(0, 0, mBitmapWidth, mBitmapHeight, mPaint);
    canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    printLog();
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN:
        mode = DRAG;
        oldCoordinate.set(event.getX(), event.getY());
        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        mode = ZOOM;
        oldDistance = spacing(event);
        oldRotation = rotation(event);
        break;
      case MotionEvent.ACTION_MOVE:
        if (mode == ZOOM) {
          float rotation = rotation(event) - oldRotation;
          float newDist = spacing(event);
          float scale = newDist / oldDistance;
          mMatrix.postScale(scale, scale, mPoints[8], mPoints[9]);// 縮放
          mMatrix.postRotate(rotation, mPoints[8], mPoints[9]);// 旋轉
          oldDistance = spacing(event);
          oldRotation = rotation(event);
          invalidate();
        } else if (mode == DRAG) {
          mMatrix.postTranslate(event.getX() - oldCoordinate.x, event.getY() - oldCoordinate.y);
          oldCoordinate.set(event.getX(), event.getY());
          invalidate();
        }
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
        mode = NONE;
        break;
    }
    return true;
  }

  // 触碰两点间距离
  private float spacing(MotionEvent event) {
    float x = event.getX(0) - event.getX(1);
    float y = event.getY(0) - event.getY(1);
    return (float) Math.sqrt(x * x + y * y);
  }

  // 取旋转角度
  private float rotation(MotionEvent event) {
    double delta_x = (event.getX(0) - event.getX(1));
    double delta_y = (event.getY(0) - event.getY(1));
    double radians = Math.atan2(delta_y, delta_x);
    return (float) Math.toDegrees(radians);
  }

  private Bitmap drawable2Bitmap(Drawable drawable) {
    try {
      if (drawable == null) {
        return null;
      }

      if (drawable instanceof BitmapDrawable) {
        return ((BitmapDrawable) drawable).getBitmap();
      }
    } catch (OutOfMemoryError e) {
      return null;
    }
    int intrinsicWidth = drawable.getIntrinsicWidth() / 3;
    int intrinsicHeight = drawable.getIntrinsicHeight() / 3;
    Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth <= 0 ? 50 : intrinsicWidth,
        intrinsicHeight <= 0 ? 50 : intrinsicHeight, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }
}
