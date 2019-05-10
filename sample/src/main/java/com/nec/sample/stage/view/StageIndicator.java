package com.nec.sample.stage.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nec.baselib.util.SizeUtil;
import com.nec.sample.R;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2019/5/10
 */
public class StageIndicator extends LinearLayout {

  /**
   * current indicator level,default 0
   */
  private int level;

  private LinearLayout container;
  private Animation showAnim, hideAnim;

  public StageIndicator(Context context) {
    this(context, null);
  }

  public StageIndicator(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.stage_inidcator_layout, this, true);

    container = findViewById(R.id.container);

    showAnim = new AlphaAnimation(0.1f, 1.0f);
    showAnim.setDuration(300);
    hideAnim = new AlphaAnimation(1.0f, 0f);
    hideAnim.setDuration(300);
  }

  public void up() {
    updateLevel(this.level + 1);
  }

  public void down() {
    updateLevel(this.level - 1);
  }

  public void updateLevel(int level) {
    if (level < 0 || this.level == level) {
      return;
    }
    if (this.level == 0) {
      //show
      show();
    } else if (level == 0) {
      //hide
      hide();
    }

    int diff = Math.abs(this.level - level);
    if (diff == 1) {
      int childCount = container.getChildCount();
      if (this.level > level) {
        //remove -1
        if (childCount > 0) {
          container.removeViewAt(childCount - 1);
        }
      } else {
        //add +1
        LayoutParams params = getLayoutParams(childCount);
        ImageView indicator = new ImageView(getContext());
        indicator.setImageResource(R.mipmap.indicator_level_icon);
        container.addView(indicator, childCount, params);
      }
    } else {
      this.removeAllViews();
      for (int i = 0; i < diff; i++) {
        LayoutParams params = getLayoutParams(i);
        ImageView indicator = new ImageView(getContext());
        indicator.setImageResource(R.mipmap.indicator_level_icon);
        container.addView(indicator, params);
      }
    }
    //update level
    this.level = level;
  }

  @NonNull private LayoutParams getLayoutParams(int index) {
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    if (index > 0) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        params.setMarginStart(SizeUtil.getFitPxFromDp(-3));
      } else {
        params.leftMargin = SizeUtil.getFitPxFromDp(-3);
      }
      params.gravity = Gravity.CENTER;
    }
    return params;
  }

  private void show() {
    this.setVisibility(VISIBLE);
    this.startAnimation(showAnim);
  }

  private void hide() {
    this.startAnimation(hideAnim);
    this.setVisibility(GONE);
  }
}
