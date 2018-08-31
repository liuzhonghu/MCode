package com.nec.myxycode.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nec.myxycode.R;
import java.util.List;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/8/31
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

  private Context context;
  private List<MessageModel> messageList;

  public ChatListAdapter(@NonNull Context context) {
    this.context = context;
  }

  public void setMessageList(List<MessageModel> messageList) {
    this.messageList = messageList;
  }

  @Override public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.chat_item_layout, null);
    return new ChatViewHolder(view);
  }

  @Override public void onBindViewHolder(ChatViewHolder holder, int position) {
    MessageModel message = messageList.get(position);
    holder.content.setText(message.message);
  }

  @Override public int getItemCount() {
    return messageList == null ? 0 : messageList.size();
  }

  class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView content;

    ChatViewHolder(View itemView) {
      super(itemView);
      content = itemView.findViewById(R.id.content);
    }
  }
}
