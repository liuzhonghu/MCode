package com.nec.baselib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2019/5/9
 */
public class BounceRcView extends RecyclerView {
  private int orientation;

  public BounceRcView(Context context) {
    this(context, null);
  }

  public BounceRcView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BounceRcView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    paddingTop = getPaddingTop();
    paddingBottom = getPaddingBottom();
    paddingLeft = getPaddingLeft();
    paddingRight = getPaddingRight();

    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.BounceRcView, defStyle, 0);
    orientation = typedArray.getInt(R.styleable.BounceRcView_orientation, 0);
    typedArray.recycle();
  }

  public int getOrientation() {
    return orientation;
  }

  /**
   * 只能设置0和1
   * 0表示RecylerView是竖直排布
   * 1表示RecylerView是水平排布
   * 否则默认为0---竖直排布
   */
  public void setOrientation(int orientation) {
    if (orientation != 0 && orientation != 1) {
      orientation = 0;
    }
    this.orientation = orientation;
  }

  private float downTouch;
  //因为Item设置了点击监听导致RecyclerView收不到ACTION_DOWN的触摸事件
  private boolean firstTouch = true;
  private static final int MOVE_VERTIFY = 10;
  //可以延伸到屏幕的四分之一
  private static final int DEFAULT_DEVIDE = 4;

  //recyclerView_thumbnail的padding
  private int paddingTop;
  private int paddingBottom;
  private int paddingLeft;
  private int paddingRight;

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        //                downY = event.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        //这样写是因为无法监听到down事件所以第一次move事件的坐标作为down
        if (firstTouch) {
          //消除第一次downX和moveX不一致
          if (orientation == 0) {
            downTouch = event.getY();
          } else if (orientation == 1) {
            downTouch = event.getX();
          }
          firstTouch = false;
          return false;
        }
        float moveTouch = 0;
        if (orientation == 0) {
          moveTouch = event.getY();
          if (!canScrollVertically(-1)) {
            if ((moveTouch - downTouch) >= MOVE_VERTIFY) {
              int deltY = (int) (moveTouch - downTouch) / DEFAULT_DEVIDE;
              setPadding(getPaddingLeft(), getPaddingTop() + deltY, getPaddingRight(),
                  getPaddingBottom());
            } else if ((moveTouch - downTouch) <= -MOVE_VERTIFY) {
              setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
            }
          } else if (!canScrollVertically(1)) {
            if ((downTouch - moveTouch) >= MOVE_VERTIFY) {
              int deltY = (int) (downTouch - moveTouch) / DEFAULT_DEVIDE;
              setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(),
                  getPaddingBottom() + deltY);
            } else if ((downTouch - moveTouch) <= -MOVE_VERTIFY) {
              setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
            }
          } else {
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
          }
        } else if (orientation == 1) {
          moveTouch = event.getX();
          if (!canScrollHorizontally(-1)) {
            if ((moveTouch - downTouch) >= MOVE_VERTIFY) {
              int deltY = (int) (moveTouch - downTouch) / DEFAULT_DEVIDE;
              setPadding(getPaddingLeft() + deltY, getPaddingTop(), getPaddingRight(),
                  getPaddingBottom());
            } else if ((moveTouch - downTouch) <= -MOVE_VERTIFY) {
              setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
            }
          } else if (!canScrollHorizontally(1)) {
            if ((downTouch - moveTouch) >= MOVE_VERTIFY) {
              int deltY = (int) (downTouch - moveTouch) / DEFAULT_DEVIDE;
              setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + deltY,
                  getPaddingBottom());
            } else if ((downTouch - moveTouch) <= -MOVE_VERTIFY) {
              setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
            }
          } else {
            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
          }
        }

        //防止在既不是顶部也不是底部时的闪烁
        downTouch = moveTouch;
        break;
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        if (orientation == 0) {
          setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
        } else if (orientation == 1) {
          setPadding(paddingLeft, getPaddingTop(), paddingRight, getPaddingBottom());
        }
        firstTouch = true;
        break;
      default:
        break;
    }
    return super.onTouchEvent(event);
  }
}
