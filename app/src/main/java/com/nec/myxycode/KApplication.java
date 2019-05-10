package com.nec.myxycode;

import com.nec.baselib.ApplicationBase;
import com.nec.baselib.util.SizeUtil;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/10
 */

public class KApplication extends ApplicationBase {
  private AppComponent component;

  @Override public void onCreate() {
    super.onCreate();
    component = FpsGraphFactory.getObjectGraph(this);
    component.inject(this);
    SizeUtil.init(this);
  }

  public AppComponent getComponent() {
    return component;
  }
}
