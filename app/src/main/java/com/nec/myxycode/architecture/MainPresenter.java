package com.nec.myxycode.architecture;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;
import com.nec.baselib.BasePresenter;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/9/20
 */
public class MainPresenter extends BasePresenter<IPresenter> {
  private static final String TAG = "MainPresenter";

  public MainPresenter() {
  }

  @Override public void detachView() {
    super.detachView();
  }

  @Override public void attachView(IPresenter editorView) {
    super.attachView(editorView);
  }

  @Override public void onPause(@NonNull LifecycleOwner owner) {
    super.onPause(owner);
    Log.d(TAG, "Host: onPause");
  }

  @Override public void onResume(@NonNull LifecycleOwner owner) {
    super.onResume(owner);
    Log.d(TAG, "Host: onResume");
  }

  @Override public void onDestroy(@NonNull LifecycleOwner owner) {
    super.onDestroy(owner);
    Log.d(TAG, "Host: onDestroy");
  }
}
