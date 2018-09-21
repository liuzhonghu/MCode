package com.nec.baselib;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/9/20
 */
public class MLifecycleObserver implements LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE) public void onCreate(@NonNull LifecycleOwner owner) {

  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START) public void onStart(@NonNull LifecycleOwner owner) {

  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) public void onResume(@NonNull LifecycleOwner owner) {

  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) public void onPause(@NonNull LifecycleOwner owner) {

  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP) public void onStop(@NonNull LifecycleOwner owner) {

  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void onDestroy(@NonNull LifecycleOwner owner) {

  }
}
