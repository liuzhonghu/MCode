package com.nec.sample.stage.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.nec.sample.R;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2019/5/10
 */
public class StageView extends RelativeLayout {

  StageIndicator indicatorView;
  RecyclerView recyclerView;

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

    indicatorView = findViewById(R.id.indicator);
    recyclerView = findViewById(R.id.rc_view);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    indicatorView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        indicatorView.down();
      }
    });
  }

  public void go() {
    indicatorView.up();
  }
}
