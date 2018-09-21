package com.nec.myxycode.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.nec.myxycode.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

  RecyclerView recyclerView;
  Button sendBtn;
  EditText messageEditText;

  private ChatListAdapter chatListAdapter;

  private static final int MSG_UPDATE_CHAT_LIST = 1001;
  private static final int UPDATE_TIME_DELAY = 500;

  private int unShowChatSize;
  private long chatOldIdleTimeMs;
  private int chatRecyclerListenerState = RecyclerView.SCROLL_STATE_IDLE;
  private boolean isChatStackFromEnd = true;
  private List<MessageModel> chatList = new ArrayList<>();

  @SuppressLint("HandlerLeak") private Handler handler = new Handler() {

    @Override public void handleMessage(Message msg) {
      switch (msg.what) {
        case MSG_UPDATE_CHAT_LIST:
          int count = new Random().nextInt(2) + 1;
          List<MessageModel> newMessageList = new ArrayList<>();
          for (int i = 0; i < count; i++) {
            MessageModel messageModel = new MessageModel();
            messageModel.message = "哈哈哈哈，我是" + new Random().nextInt(1000) + "号";
            newMessageList.add(messageModel);
          }

          chatList.addAll(newMessageList);
          updateChatListData();

          int delay = new Random().nextInt(8);
          sendEmptyMessageDelayed(MSG_UPDATE_CHAT_LIST, delay * UPDATE_TIME_DELAY);
          break;
        default:
          break;
      }
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    initUIData();
  }

  private void initUIData() {
    messageEditText = findViewById(R.id.et_message);
    sendBtn = findViewById(R.id.btn_send_message);
    sendBtn.setOnClickListener(v -> {
      String content = messageEditText.getText().toString();
      if (!TextUtils.isEmpty(content)) {
        MessageModel messageModel = new MessageModel();
        messageModel.message = content;
        chatList.add(messageModel);
        updateChatListData();
        messageEditText.setText("");
        hideInputMethod(messageEditText);
      }
    });
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        chatRecyclerListenerState = newState;

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          chatOldIdleTimeMs = System.currentTimeMillis();
          if (unShowChatSize > 0) {
            updateChatListData(chatList, unShowChatSize, false);
          }
          unShowChatSize = 0;
        }
      }
    });

    List<MessageModel> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      MessageModel messageModel = new MessageModel();
      messageModel.message = "哈哈哈哈，我是" + new Random().nextInt(100) + "号";
      list.add(messageModel);
    }
    chatList.addAll(list);
    handler.sendEmptyMessageDelayed(MSG_UPDATE_CHAT_LIST, 5 * UPDATE_TIME_DELAY);
  }

  private void updateChatListData() {
    if (chatRecyclerListenerState == RecyclerView.SCROLL_STATE_IDLE) {
      if (System.currentTimeMillis() - chatOldIdleTimeMs > 2500) {
        updateChatListData(chatList, unShowChatSize, true);
      } else {
        updateChatListData(chatList, unShowChatSize, false);
      }
      unShowChatSize = 0;
    } else {
      unShowChatSize++;
    }
  }

  private void updateChatListData(List<MessageModel> messageModels, int updateRangeCount,
      boolean isScrollToPosition) {
    if (messageModels != null && !messageModels.isEmpty()) {
      if (chatListAdapter == null) {
        chatListAdapter = new ChatListAdapter(this);
        chatListAdapter.setHasStableIds(false);
        LinearLayoutManager linearLayoutManager =
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(
            getResources().getDimensionPixelOffset(R.dimen.small_margin_size_3)));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.setMessageList(messageModels);
      } else {
        int chatDatasCount = messageModels.size();
        chatListAdapter.setMessageList(messageModels);
        if (updateRangeCount > 0) {
          chatListAdapter.notifyItemRangeChanged(chatDatasCount - updateRangeCount - 1,
              updateRangeCount);
        } else {
          chatListAdapter.notifyItemChanged(chatDatasCount - 1);
        }
        if (isScrollToPosition) {
          recyclerView.scrollToPosition(chatDatasCount - 1);
        }
        if (isChatStackFromEnd && recyclerView.getChildCount() > 0) {
          int chatRecyclerHeight =
              this.getResources().getDimensionPixelSize(R.dimen.chat_recyclerview_height);
          if (recyclerView.getChildAt(0).getTop() - chatRecyclerHeight / 8 <= 0) {
            isChatStackFromEnd = false;
            ((LinearLayoutManager) recyclerView.getLayoutManager()).setStackFromEnd(false);
          }
        } else if (!isChatStackFromEnd && recyclerView.getChildCount() == 1) {
          int chatRecyclerHeight =
              this.getResources().getDimensionPixelSize(R.dimen.chat_recyclerview_height);
          if (recyclerView.getChildAt(0).getTop() - chatRecyclerHeight / 8 > 0) {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).setStackFromEnd(true);
            isChatStackFromEnd = true;
          }
        }
      }
    }
  }

  public void hideInputMethod(View view) {
    if (view != null && view.getContext() != null && view.getWindowToken() != null) {
      try {
        ((InputMethodManager) view.getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
            view.getWindowToken(), 0);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
      handler = null;
    }
  }
}
