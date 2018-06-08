package com.nec.structure.mvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.nec.baselib.NormalItem;
import com.nec.structure.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/24
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.MyViewHolder> {
  private Context context;
  private List<NormalItem> dataList = new ArrayList<>();

  public AnimalAdapter(Context context) {
    this.context = context;
  }

  public void setDataList(List<NormalItem> dataList) {
    this.dataList = dataList;
    notifyDataSetChanged();
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.mvp_item_layout, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override public void onBindViewHolder(MyViewHolder holder, int position) {
    holder.imageView.setImageResource(dataList.get(position).getImg());
    holder.textView.setText(dataList.get(position).getName());
  }

  @Override public int getItemCount() {
    return dataList.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    MyViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.item_text);
      imageView = itemView.findViewById(R.id.item_img);
    }
  }
}
