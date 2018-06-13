package com.nec.myxycode.fpsactor;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.nec.myxycode.KApplication;
import javax.inject.Inject;

public class FPSRecyclerView extends RecyclerView {
  @Inject FPSSampleAdpater adapter;

  public FPSRecyclerView(Context context) {
    this(context, null);
  }

  public FPSRecyclerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    setLayoutManager(layoutManager);
    addItemDecoration(new RCItemDecoration(getContext(), RCItemDecoration.VERTICAL_LIST));
    setAdapter(adapter);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

  public FPSRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    ((KApplication) (context).getApplicationContext()).getComponent().inject(this);
  }

  public void setMegaBytes(Float megaBytes) {
    adapter.setMegaBytes(megaBytes);
  }

  public void notifyDataSetChanged() {
    adapter.notifyDataSetChanged();
  }
}
