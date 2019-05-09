package com.nec.myxycode.damp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.nec.baselib.NormalItem;
import com.nec.myxycode.R;
import com.nec.myxycode.draggableRC.adapter.DragRecyclerAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @since 2019/5/9
 */
public class DampActivity extends AppCompatActivity {

  RecyclerView recyclerView;

  private List<NormalItem> mDataList = new ArrayList<NormalItem>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_damp);

    initData();

    recyclerView = findViewById(R.id.rc_view);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
    recyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));

    DragRecyclerAdapter adapter =
        new DragRecyclerAdapter(this, mDataList, R.layout.drag_item_vertical_list);
    recyclerView.setAdapter(adapter);

    runLayoutAnimation(recyclerView);
  }

  public void reload(View view) {
    runLayoutAnimation(recyclerView);
  }

  private void runLayoutAnimation(final RecyclerView recyclerView) {
    final Context context = recyclerView.getContext();

    final LayoutAnimationController controller =
        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);

    recyclerView.setLayoutAnimation(controller);
    recyclerView.getAdapter().notifyDataSetChanged();
    recyclerView.scheduleLayoutAnimation();
  }

  private void initData() {
    mDataList.add(new NormalItem(0, "Brown_cow", R.drawable.brown_cow));
    mDataList.add(new NormalItem(1, "Butterfly", R.drawable.butterfly));
    mDataList.add(new NormalItem(2, "Camel", R.drawable.camel));
    mDataList.add(new NormalItem(3, "Crocodile", R.drawable.crocodile));
    mDataList.add(new NormalItem(4, "Froggy", R.drawable.froggy));
    mDataList.add(new NormalItem(5, "Giraffe", R.drawable.giraffe));
    mDataList.add(new NormalItem(6, "Kitten", R.drawable.kitten));
    mDataList.add(new NormalItem(7, "Lion", R.drawable.lion));
    mDataList.add(new NormalItem(8, "Monkey", R.drawable.monkey));
    mDataList.add(new NormalItem(9, "Panda", R.drawable.panda));
    mDataList.add(new NormalItem(10, "Tiger", R.drawable.tiger));
    mDataList.add(new NormalItem(12, "Turtle", R.drawable.turtle));

    Collections.shuffle(mDataList);
  }
}
