package com.nec.kboardlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2017/12/12
 */

public class KSketchView extends View implements View.OnTouchListener {
  public Context mContext;

  /**
   * 缩放手势
   */
  public ScaleGestureDetector mScaleGestureDetector = null;

  public KSketchView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    this.mContext = context;
    if (isFocusable()) {
      this.setOnTouchListener(this);
      mScaleGestureDetector =
          new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override public boolean onScale(ScaleGestureDetector detector) {
              onScaleAction(detector);
              return true;
            }

            @Override public boolean onScaleBegin(ScaleGestureDetector detector) {
              return false;
            }

            @Override public void onScaleEnd(ScaleGestureDetector detector) {

            }
          });
    }
    invalidate();
  }

  private void onScaleAction(ScaleGestureDetector detector) {

  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    return false;
  }
}
