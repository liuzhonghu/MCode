package com.nec.myxycode.damp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nec.myxycode.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

  public static final int ITEM_COUNT = 32;

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new ViewHolder(inflater.inflate(R.layout.row_empty_card, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
  }

  @Override public int getItemCount() {
    return ITEM_COUNT;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
