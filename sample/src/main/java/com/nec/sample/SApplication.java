package com.nec.sample;

import com.nec.baselib.ApplicationBase;
import com.nec.baselib.util.SizeUtil;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/10
 */

public class SApplication extends ApplicationBase {

  @Override public void onCreate() {
    super.onCreate();

    SizeUtil.init(this);
  }
}
