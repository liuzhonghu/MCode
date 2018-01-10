package com.nec.fpsactor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Choreographer;
import android.view.Display;
import android.view.WindowManager;
import com.nec.fpsactor.ui.Coach;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/10
 */
public class ActorBuilder {
  private static FPSConfig fpsConfig;
  private static FPSFrameCallback fpsFrameCallback;
  private static Coach coach;
  private static Foreground.Listener foregroundListener = new Foreground.Listener() {
    @Override public void onBecameForeground() {
      coach.show();
    }

    @Override public void onBecameBackground() {
      coach.hide(false);
    }
  };

  protected ActorBuilder() {
    fpsConfig = new FPSConfig();
  }

  /**
   * configures the fpsConfig to the device's hardware
   * refreshRate ex. 60fps and deviceRefreshRateInMs ex. 16.6ms
   */
  private void setFrameRate(Context context) {
    Display display =
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    fpsConfig.deviceRefreshRateInMs = 1000f / display.getRefreshRate();
    fpsConfig.refreshRate = display.getRefreshRate();
  }

  /**
   * stops the frame callback and foreground listener
   * nulls out static variables
   * called from FPSLibrary in a static context
   */
  protected static void hide(Context context) {
    // tell callback to stop registering itself
    fpsFrameCallback.setEnabled(false);

    Foreground.get(context).removeListener(foregroundListener);
    // remove the view from the window
    coach.destroy();

    // null it all out
    coach = null;
    fpsFrameCallback = null;
    fpsConfig = null;
  }

  // PUBLIC BUILDER METHODS

  /**
   * show fps meter, this regisers the frame callback that
   * collects the fps info and pushes it to the ui
   */
  public void show(Context context) {

    if (overlayPermRequest(context)) {
      //once permission is granted then you must call show() again
      return;
    }

    //are we running?  if so, call tinyCoach.show() and return
    if (coach != null) {
      coach.show();
      return;
    }

    // set device's frame rate info into the config
    setFrameRate(context);

    // create the presenter that updates the view
    coach = new Coach((Application) context.getApplicationContext(), fpsConfig);

    // create our choreographer callback and register it
    fpsFrameCallback = new FPSFrameCallback(fpsConfig, coach);
    Choreographer.getInstance().postFrameCallback(fpsFrameCallback);

    //set activity background/foreground listener
    Foreground.init((Application) context.getApplicationContext()).addListener(foregroundListener);
  }

  /**
   * this adds a frame callback that the library will invoke on the
   * each time the choreographer calls us, we will send you the frame times
   * and number of dropped frames.
   */
  public ActorBuilder addFrameDataCallback(FrameDataCallback callback) {
    fpsConfig.frameDataCallback = callback;
    return this;
  }

  /**
   * set red flag percent, default is 20%
   */
  public ActorBuilder redFlagPercentage(float percentage) {
    fpsConfig.redFlagPercentage = percentage;
    return this;
  }

  /**
   * set red flag percent, default is 5%
   */
  public ActorBuilder yellowFlagPercentage(float percentage) {
    fpsConfig.yellowFlagPercentage = percentage;
    return this;
  }

  /**
   * starting x position of fps meter default is 200px
   */
  public ActorBuilder startingXPosition(int xPosition) {
    fpsConfig.startingXPosition = xPosition;
    fpsConfig.xOrYSpecified = true;
    return this;
  }

  /**
   * starting y positon of fps meter default is 600px
   */
  public ActorBuilder startingYPosition(int yPosition) {
    fpsConfig.startingYPosition = yPosition;
    fpsConfig.xOrYSpecified = true;
    return this;
  }

  /**
   * starting gravity of fps meter default is Gravity.TOP | Gravity.START;
   */
  public ActorBuilder startingGravity(int gravity) {
    fpsConfig.startingGravity = gravity;
    fpsConfig.gravitySpecified = true;
    return this;
  }

  /**
   * request overlay permission when api >= 23
   */
  private boolean overlayPermRequest(Context context) {
    boolean permNeeded = false;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.canDrawOverlays(context)) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        permNeeded = true;
      }
    }
    return permNeeded;
  }
}
