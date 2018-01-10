package com.nec.myxycode;

import android.app.Application;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/10
 */

public class KApplication extends Application {
  private AppComponent component;

  @Override public void onCreate() {
    super.onCreate();
    component = FpsGraphFactory.getObjectGraph(this);
    component.inject(this);
  }

  public AppComponent getComponent() {
    return component;
  }
}
