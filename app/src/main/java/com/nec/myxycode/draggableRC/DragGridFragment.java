package com.nec.myxycode.draggableRC;

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
import com.nec.myxycode.draggableRC.callback.DragItemClickListener;
import com.nec.myxycode.draggableRC.callback.DragItemTouchCallback;
import com.nec.myxycode.util.ACache;
import com.nec.myxycode.util.VibratorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DragGridFragment extends Fragment implements DragItemTouchCallback.OnDragListener {
  private List<DragItem> mDataList = new ArrayList<DragItem>();

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

    ArrayList<DragItem> items = (ArrayList<DragItem>) ACache.get(getContext()).getAsObject("items");
    if (items != null) {
      mDataList.addAll(items);
    } else {
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
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return new RecyclerView(container.getContext());
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
        if (vh.getLayoutPosition() != mDataList.size() - 1) {
          itemTouchHelper.startDrag(vh);
          VibratorUtil.Vibrate(getActivity(), 100);   //震动70ms
        }
      }

      @Override public void onItemClick(RecyclerView.ViewHolder vh) {
        DragItem item = mDataList.get(vh.getLayoutPosition());
        Toast.makeText(getActivity(), item.getId() + " " + item.getName(), Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  @Override public void onFinishDrag() {
    //存入缓存
    ACache.get(getActivity()).put("items", (ArrayList<DragItem>) mDataList);
  }
}
