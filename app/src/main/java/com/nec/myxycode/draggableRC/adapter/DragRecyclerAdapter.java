package com.nec.myxycode.draggableRC.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.nec.myxycode.R;
import com.nec.myxycode.draggableRC.callback.DragItemTouchCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/24
 */

public class DragRecyclerAdapter extends RecyclerView.Adapter<DragRecyclerAdapter.MyViewHolder>
    implements DragItemTouchCallback.ItemTouchAdapter {
  private Context context;
  private List<DragItem> dataList = new ArrayList<>();
  private int itemLayoutID;

  public DragRecyclerAdapter(Context context, List<DragItem> dataList, int itemLayoutID) {
    this.context = context;
    this.dataList = dataList;
    this.itemLayoutID = itemLayoutID;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(itemLayoutID, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override public void onBindViewHolder(MyViewHolder holder, int position) {
    holder.imageView.setImageResource(dataList.get(position).getImg());
    holder.textView.setText(dataList.get(position).getName());
  }

  @Override public int getItemCount() {
    return dataList.size();
  }

  @Override public void onMove(int fromPosition, int toPosition) {
    if (fromPosition == dataList.size() - 1 || toPosition == dataList.size() - 1) {
      return;
    }
    if (fromPosition < toPosition) {
      for (int i = fromPosition; i < toPosition; i++) {
        Collections.swap(dataList, i, i + 1);
      }
    } else {
      for (int i = fromPosition; i > toPosition; i--) {
        Collections.swap(dataList, i, i - 1);
      }
    }
    notifyItemMoved(fromPosition, toPosition);
  }

  @Override public void onSwiped(int position) {
    dataList.remove(position);
    notifyItemRemoved(position);
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    MyViewHolder(View itemView) {
      super(itemView);
      WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      int width = wm.getDefaultDisplay().getWidth();
      ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
      layoutParams.height = width / 4;
      itemView.setLayoutParams(layoutParams);
      textView = (TextView) itemView.findViewById(R.id.item_text);
      imageView = (ImageView) itemView.findViewById(R.id.item_img);
    }
  }
}
