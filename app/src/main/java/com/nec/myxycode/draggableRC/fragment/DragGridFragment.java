package com.nec.myxycode.draggableRC.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.nec.myxycode.R;
import com.nec.myxycode.draggableRC.adapter.DividerGridItemDecoration;
import com.nec.baselib.NormalItem;
import com.nec.myxycode.draggableRC.adapter.DragRecyclerAdapter;
import com.nec.myxycode.draggableRC.callback.DragItemClickListener;
import com.nec.myxycode.draggableRC.callback.DragItemTouchCallback;
import com.nec.baselib.util.ACache;
import com.nec.baselib.util.VibratorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DragGridFragment extends Fragment implements DragItemTouchCallback.OnDragListener {
  private List<NormalItem> mDataList = new ArrayList<NormalItem>();

  public DragGridFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment DragGridFragment.
   */
  public static DragGridFragment newInstance() {
    return new DragGridFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ArrayList<NormalItem> items = (ArrayList<NormalItem>) ACache.get(getContext()).getAsObject("items");
    if (items != null) {
      mDataList.addAll(items);
    } else {
      for (int i = 0; i < 3; i++) {
        mDataList.add(new NormalItem(i * 7, "Brown_cow", R.drawable.brown_cow));
        mDataList.add(new NormalItem(i * 7 + 1, "Butterfly", R.drawable.butterfly));
        mDataList.add(new NormalItem(i * 7 + 2, "Camel", R.drawable.camel));
        mDataList.add(new NormalItem(i * 7 + 3, "Crocodile", R.drawable.crocodile));
        mDataList.add(new NormalItem(i * 7 + 4, "Froggy", R.drawable.froggy));
        mDataList.add(new NormalItem(i * 7 + 5, "Giraffe", R.drawable.giraffe));
        mDataList.add(new NormalItem(i * 7 + 6, "Kitten", R.drawable.kitten));
        mDataList.add(new NormalItem(i * 7 + 7, "Lion", R.drawable.lion));
        mDataList.add(new NormalItem(i * 7 + 8, "Monkey", R.drawable.monkey));
        mDataList.add(new NormalItem(i * 7 + 9, "Panda", R.drawable.panda));
        mDataList.add(new NormalItem(i * 7 + 10, "Tiger", R.drawable.tiger));
        mDataList.add(new NormalItem(i * 7 + 12, "Turtle", R.drawable.turtle));
      }
      Collections.shuffle(mDataList);
    }
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

    DragRecyclerAdapter adapter =
        new DragRecyclerAdapter(getContext(), mDataList, R.layout.drag_item_grid);
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

    final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(adapter));
    itemTouchHelper.attachToRecyclerView(recyclerView);
    recyclerView.addOnItemTouchListener(new DragItemClickListener(recyclerView) {
      @Override public void onLongClick(RecyclerView.ViewHolder vh) {
        if (vh.getLayoutPosition() < mDataList.size()) {
          itemTouchHelper.startDrag(vh);
          VibratorUtil.Vibrate(getActivity(), 100);   //震动70ms
        }
      }

      @Override public void onItemClick(RecyclerView.ViewHolder vh) {
        NormalItem item = mDataList.get(vh.getLayoutPosition());
        Toast.makeText(getActivity(), item.getId() + " " + item.getName(), Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  @Override public void onFinishDrag() {
    //存入缓存
    ACache.get(getActivity()).put("items", (ArrayList<NormalItem>) mDataList);
  }
}
