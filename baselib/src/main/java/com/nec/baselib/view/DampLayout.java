package com.nec.baselib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import com.nec.baselib.R;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2019/5/9
 */
public class DampLayout extends LinearLayout implements NestedScrollingParent2 {

  private static final int MAX_DISTANCE = 288;
  private View headerView;
  private View footerView;
  private View childView;
  private ReboundAnimator animator = null;
  private boolean isFirstRunAnim;//针对冗余fling期间，保证回弹动画只执行一次

  private int orientation;

  public DampLayout(Context context) {
    this(context, null);
  }

  public DampLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DampLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    headerView = new View(context);
    footerView = new View(context);

    headerView.setBackgroundColor(Color.GRAY);
    footerView.setBackgroundColor(Color.RED);

    orientation = LinearLayout.HORIZONTAL;
    if (attrs != null) {
      TypedArray typedArray =
          context.obtainStyledAttributes(attrs, R.styleable.DampLayout, defStyleAttr, 0);
      orientation = typedArray.getInt(R.styleable.DampLayout_orientation, 0);
      typedArray.recycle();
    }
    setOrientation(orientation);
  }

  @Override protected void onFinishInflate() {//在setContentView之后、onMeasure之前调用的方法
    super.onFinishInflate();
    childView = getChildAt(0);

    LayoutParams layoutParams;
    if (orientation == LinearLayout.HORIZONTAL) {
      layoutParams = new LayoutParams(MAX_DISTANCE, LayoutParams.MATCH_PARENT);
    } else {
      layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_DISTANCE);
    }
    addView(headerView, 0, layoutParams);
    addView(footerView, getChildCount(), layoutParams);
    // 隐藏header

    if (orientation == LinearLayout.HORIZONTAL) {
      scrollBy(MAX_DISTANCE, 0);
    } else {
      scrollBy(0, MAX_DISTANCE);
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    ViewGroup.LayoutParams params = childView.getLayoutParams();
    if (orientation == LinearLayout.HORIZONTAL) {
      params.width = getMeasuredWidth();
    } else {
      params.height = getMeasuredHeight();
    }
  }

  @Override public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes,
      int type) {
    if (animator == null) {
      animator = new ReboundAnimator();
    }
  }

  /**
   * 返回true代表处理本次事件
   */
  @Override public boolean onStartNestedScroll(@NonNull View child, @NonNull View target,
      int nestedScrollAxes, int type) {
    return target instanceof RecyclerView;
  }

  /**
   * 复位初始位置
   */
  @Override public void onStopNestedScroll(@NonNull View target, int type) {
    isFirstRunAnim = false;
    if (orientation == LinearLayout.HORIZONTAL) {
      if (getScrollX() != MAX_DISTANCE) {
        animator.startOfFloat(target, getScrollX());
      }
    } else {
      if (getScrollY() != MAX_DISTANCE) {
        animator.startOfFloat(target, getScrollY());
      }
    }
  }

  @Override
  public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
      int type) {
    // 如果在自定义ViewGroup之上还有父View交给我来处理
    getParent().requestDisallowInterceptTouchEvent(true);
    if (type == ViewCompat.TYPE_TOUCH) {
      //手指触发的滑动
      int distance;
      if (orientation == LinearLayout.HORIZONTAL) {
        boolean hiddenLeft =
            dx > 0 && getScrollX() < MAX_DISTANCE && !target.canScrollHorizontally(-1);
        boolean showLeft = dx < 0 && !target.canScrollHorizontally(-1);

        boolean hiddenRight =
            dx < 0 && getScrollX() > MAX_DISTANCE && !target.canScrollHorizontally(1);
        boolean showRight = dx > 0 && !target.canScrollHorizontally(1);

        if (hiddenLeft || showLeft || hiddenRight || showRight) {
          if (animator.isStarted()) {
            animator.cancel();
          }
          scrollBy(damping(dx), 0);
          consumed[0] = dx;
        }
        distance = dx;
      } else {
        // dy>0向下scroll dy<0向上scroll
        boolean hiddenTop =
            dy > 0 && getScrollY() < MAX_DISTANCE && !target.canScrollVertically(-1);
        boolean showTop = dy < 0 && !target.canScrollVertically(-1);
        boolean hiddenBottom =
            dy < 0 && getScrollY() > MAX_DISTANCE && !target.canScrollVertically(1);
        boolean showBottom = dy > 0 && !target.canScrollVertically(1);

        if (hiddenTop || showTop || hiddenBottom || showBottom) {
          if (animator.isStarted()) {
            animator.cancel();
          }
          scrollBy(0, damping(dy));

          consumed[1] = dy;
        }
        distance = dy;
      }
      adjust(distance, target);
    }
  }

  @Override
  public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
      int dyUnconsumed, int type) {
    getParent().requestDisallowInterceptTouchEvent(true);
    if (type == ViewCompat.TYPE_NON_TOUCH) {
      //非手指触发的滑动，即Filing
      int unconsumedDistance;
      if (orientation == LinearLayout.HORIZONTAL) {
        //解决冗余fling问题
        boolean run =
            (Math.floor(getScrollX()) == 0) || ((Math.ceil(getScrollX()) == 2 * MAX_DISTANCE));
        if (run && !isFirstRunAnim) {
          int distance = 0;
          if (dxUnconsumed > 0) {
            distance = 2 * MAX_DISTANCE;
          }
          animator.startOfFloat(target, distance);
          isFirstRunAnim = true;
        }
        if (isFirstRunAnim) {
          return;
        }

        boolean showLeft = dxUnconsumed < 0 && !target.canScrollHorizontally(-1);
        boolean showRight = dxUnconsumed > 0 && !target.canScrollHorizontally(1);
        if (showLeft || showRight) {
          if (animator.isStarted()) {
            animator.cancel();
          }
          scrollBy(damping(dxUnconsumed), 0);
        }

        unconsumedDistance = dxUnconsumed;
      } else {
        boolean run =
            (Math.floor(getScrollY()) == 0) || ((Math.ceil(getScrollY()) == 2 * MAX_DISTANCE));
        if (run && !isFirstRunAnim) {
          int distance = 0;
          if (dyUnconsumed > 0) {
            distance = 2 * MAX_DISTANCE;
          }
          animator.startOfFloat(target, distance);
          isFirstRunAnim = true;
        }
        if (isFirstRunAnim) {
          return;
        }

        // dy>0向下fling dy<0向上fling
        boolean showTop = dyUnconsumed < 0 && !target.canScrollVertically(-1);
        boolean showBottom = dyUnconsumed > 0 && !target.canScrollVertically(1);
        if (showTop || showBottom) {
          if (animator.isStarted()) {
            animator.cancel();
          }
          scrollBy(0, damping(dyUnconsumed));
        }
        unconsumedDistance = dyUnconsumed;
      }

      adjust(unconsumedDistance, target);
    }
  }

  /**
   * 衰减可继续scroll或fling的距离
   */
  private int damping(int dis) {
    //计算衰减系数,越大可继续scroll或fling的距离越短
    int v;
    if (orientation == LinearLayout.HORIZONTAL) {
      v = (int) (Math.abs(MAX_DISTANCE - getScrollX()) * 0.01);
    } else {
      v = (int) (Math.abs(MAX_DISTANCE - getScrollY()) * 0.01);
    }
    return v < 2 ? dis : dis / v;
  }

  /**
   * 调整错位问题(强转精度损失造成的错位)
   */
  private void adjust(int dis, View target) {
    if (orientation == LinearLayout.HORIZONTAL) {
      if (dis > 0 && getScrollX() > MAX_DISTANCE && !target.canScrollHorizontally(-1)) {
        scrollTo(MAX_DISTANCE, 0);
      }
      if (dis < 0 && getScrollX() < MAX_DISTANCE && !target.canScrollHorizontally(1)) {
        scrollTo(MAX_DISTANCE, 0);
      }
    } else {
      if (dis > 0 && getScrollY() > MAX_DISTANCE && !target.canScrollVertically(-1)) {
        scrollTo(0, MAX_DISTANCE);
      }
      if (dis < 0 && getScrollY() < MAX_DISTANCE && !target.canScrollVertically(1)) {
        scrollTo(0, MAX_DISTANCE);
      }
    }
  }

  /**
   * 限制滑动 移动y轴不能超出最大范围
   */
  @Override public void scrollTo(int x, int y) {
    if (orientation == LinearLayout.HORIZONTAL) {
      if (x < 0) {
        x = 0;
      } else if (x > MAX_DISTANCE * 2) {
        x = MAX_DISTANCE * 2;
      }
    } else {
      if (y < 0) {
        y = 0;
      } else if (y > MAX_DISTANCE * 2) {
        y = MAX_DISTANCE * 2;
      }
    }
    super.scrollTo(x, y);
  }

  /**
   * 回弹动画
   */
  private class ReboundAnimator extends ValueAnimator {
    private View target;

    private ReboundAnimator() {
      init();
    }

    private void init() {
      this.setInterpolator(new DecelerateInterpolator());
      this.setDuration(320);
      this.addUpdateListener(new AnimatorUpdateListener() {
        @Override public void onAnimationUpdate(ValueAnimator animation) {
          float currValue = (float) getAnimatedValue();

          if (orientation == LinearLayout.HORIZONTAL) {
            scrollTo((int) currValue, 0);
            // 调整错位问题(强转精度损失造成的错位)
            if (getScrollX() > MAX_DISTANCE && !target.canScrollHorizontally(-1)) {
              scrollTo(MAX_DISTANCE, 0);
            }
            if (getScrollX() < MAX_DISTANCE && !target.canScrollHorizontally(1)) {
              scrollTo(MAX_DISTANCE, 0);
            }
          } else {
            scrollTo(0, (int) currValue);
            // 调整错位问题(强转精度损失造成的错位)
            if (getScrollY() > MAX_DISTANCE && !target.canScrollVertically(-1)) {
              scrollTo(0, MAX_DISTANCE);
            }
            if (getScrollY() < MAX_DISTANCE && !target.canScrollVertically(1)) {
              scrollTo(0, MAX_DISTANCE);
            }
          }
        }
      });
    }

    private void startOfFloat(View target, float value) {
      this.setFloatValues(value, MAX_DISTANCE);
      this.target = target;
      this.start();
    }
  }
}
