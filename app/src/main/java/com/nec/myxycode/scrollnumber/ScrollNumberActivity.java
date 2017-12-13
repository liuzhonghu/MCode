package com.nec.myxycode.scrollnumber;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.nec.myxycode.R;

public class ScrollNumberActivity extends AppCompatActivity {
  MultiScrollNumber scrollNumber;
  //ScrollNumberV2 scrollNumberV2;

  EditText targetNumber;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scroll_number);

    scrollNumber = (MultiScrollNumber) findViewById(R.id.scroll_number);
    //scrollNumberV2 = (ScrollNumberV2) findViewById(R.id.scroll_number_v2);

    scrollNumber.setTextColors(new int[] {
        R.color.red01, R.color.orange02, R.color.blue03, R.color.green04, R.color.purple05
    });
    //        scrollNumber.setTextSize(64);

    //        scrollNumber.setNumber(64, 2048);
    scrollNumber.setInterpolator(new LinearInterpolator());
    scrollNumber.setTextFont("myfont.ttf");
    scrollNumber.setNumber(0, 12345);

    targetNumber = (EditText) findViewById(R.id.et_number);
    targetNumber.addTextChangedListener(new EditChangedListener());
  }

  public void scrollNumber(View view) {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    scrollNumber.startScroll();
    //scrollNumberV2.startScrollV2();
  }

  private class EditChangedListener implements TextWatcher {
    private CharSequence temp;//监听前的文本

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      temp = s;
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override public void afterTextChanged(Editable s) {
      if (!TextUtils.isEmpty(temp)) {
        scrollNumber.setNumber(0, Integer.valueOf(String.valueOf(temp)));
        //scrollNumberV2.setCircleAndNumber(2, Integer.valueOf(String.valueOf(temp)));
      }
    }
  }
}
