package com.nec.sample.stage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
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
  }

  public void updateLevel(int level) {

  }
}
