package com.nec.myxycode.damp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.nec.myxycode.R;

public class AnimateRcActivity extends AppCompatActivity {

  RecyclerView mRecyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animate_rc);

    mRecyclerView = findViewById(R.id.animate_rc);

    setupRecyclerView();
    runLayoutAnimation(mRecyclerView);
  }

  private void setupRecyclerView() {
    final Context context = mRecyclerView.getContext();
    final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    mRecyclerView.setAdapter(new CardAdapter());
    mRecyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
  }

  private void runLayoutAnimation(final RecyclerView recyclerView) {
    final Context context = recyclerView.getContext();

    final LayoutAnimationController controller =
        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);

    recyclerView.setLayoutAnimation(controller);
    recyclerView.getAdapter().notifyDataSetChanged();
    recyclerView.scheduleLayoutAnimation();
  }

  public void reload(View view) {
    runLayoutAnimation(mRecyclerView);
  }
}
