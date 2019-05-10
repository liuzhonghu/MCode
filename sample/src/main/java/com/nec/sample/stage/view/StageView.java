package com.nec.sample.stage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.nec.sample.R;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2019/5/10
 */
public class StageView extends RelativeLayout {
  public StageView(Context context) {
    this(context, null);
  }

  public StageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.stage_view_layout, this, true);
  }
}
