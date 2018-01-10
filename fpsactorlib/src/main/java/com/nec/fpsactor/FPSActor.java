package com.nec.fpsactor;

import android.content.Context;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/10
 */
public class FPSActor {
  public static ActorBuilder create() {
    return new ActorBuilder();
  }

  public static void hide(Context context) {
    ActorBuilder.hide(context.getApplicationContext());
  }
}
