package com.nec.myxycode.draggableRC.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nec.myxycode.R;
import com.nec.myxycode.draggableRC.adapter.DragItem;
import com.nec.myxycode.draggableRC.adapter.DragRecyclerAdapter;
import com.nec.myxycode.draggableRC.callback.DragItemClickListener;
import com.nec.myxycode.draggableRC.callback.DragItemTouchCallback;
import com.nec.myxycode.util.VibratorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DragListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DragListFragment extends Fragment {
  private boolean isVertical = true;
  private static final String ARG_PARAM1 = "param1";
  private List<DragItem> mDataList = new ArrayList<DragItem>();

  public DragListFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment DragListFragment.
   */
  public static DragListFragment newInstance(boolean isVertical) {
    DragListFragment fragment = new DragListFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_PARAM1, isVertical);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    for (int i = 0; i < 3; i++) {
      mDataList.add(new DragItem(i * 8, "Brown_cow", R.mipmap.brown_cow));
      mDataList.add(new DragItem(i * 8 + 1, "Butterfly", R.mipmap.butterfly));
      mDataList.add(new DragItem(i * 8 + 2, "Camel", R.mipmap.camel));
      mDataList.add(new DragItem(i * 8 + 3, "Crocodile", R.mipmap.crocodile));
      mDataList.add(new DragItem(i * 8 + 4, "Froggy", R.mipmap.froggy));
      mDataList.add(new DragItem(i * 8 + 5, "Giraffe", R.mipmap.giraffe));
      mDataList.add(new DragItem(i * 8 + 6, "Kitten", R.mipmap.kitten));
      mDataList.add(new DragItem(i * 8 + 7, "Lion", R.mipmap.lion));
      mDataList.add(new DragItem(i * 8 + 8, "Monkey", R.mipmap.monkey));
      mDataList.add(new DragItem(i * 8 + 9, "Panda", R.mipmap.panda));
      mDataList.add(new DragItem(i * 8 + 10, "Tiger", R.mipmap.tiger));
      mDataList.add(new DragItem(i * 8 + 12, "Turtle", R.mipmap.turtle));
    }
    Collections.shuffle(mDataList);

    isVertical = getArguments().getBoolean(ARG_PARAM1, true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    RecyclerView view = new RecyclerView(container.getContext());
    view.setBackgroundColor(Color.GRAY);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    DragRecyclerAdapter adapter = new DragRecyclerAdapter(this.getContext(), mDataList,
        isVertical ? R.layout.drag_item_vertical_list : R.layout.drag_item_horizontal_list);
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
        isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(layoutManager);

    final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(adapter));
    itemTouchHelper.attachToRecyclerView(recyclerView);
    recyclerView.addOnItemTouchListener(new DragItemClickListener(recyclerView) {
      @Override public void onLongClick(RecyclerView.ViewHolder vh) {
        if (vh.getLayoutPosition() < mDataList.size()) {
          itemTouchHelper.startDrag(vh);
          VibratorUtil.Vibrate(getActivity(), 100);   //震动70ms
        }
      }
    });
  }
}
