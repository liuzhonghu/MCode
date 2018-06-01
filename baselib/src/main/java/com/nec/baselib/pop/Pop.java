package com.nec.baselib.pop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import java.util.Arrays;

/**
 * Created by liuzhonghu on 2017/7/27.
 *
 * @Description
 */

public class Pop {

  public static void show(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 1.1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1.2f, 70, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.85f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    animatorSet.start();
  }

  public static void showSoftly(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 1.1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1.15f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    animatorSet.start();
  }

  public static void showDeepSoftly(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 0.85f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.95f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.9f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    animatorSet.start();
  }

  public static void none2ShowDeepSoftly(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 0f, 1f, 200, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.85f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.95f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.9f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    animatorSet.start();
  }

  public static void none2Show(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 0f, 1f, 200, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1.1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1.2f, 70, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.85f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    animatorSet.start();
  }

  public static void show(View view, final Callback callback) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 1.1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1.2f, 70, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.85f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    animatorSet.addListener(new Animator.AnimatorListener() {

      @Override public void onAnimationStart(Animator animation) {
      }

      @Override public void onAnimationEnd(Animator animation) {
        if (callback != null) {
          callback.onFinish();
        }
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
    animatorSet.start();
  }

  public static AnimatorSet getPopAnimation(View view) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(Arrays.<Animator>asList(
        AnimatorUtils.getScaleAnimation(view, 1.1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1.2f, 70, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 0.85f, 50, new AccelerateDecelerateInterpolator()),
        AnimatorUtils.getScaleAnimation(view, 1f, 50, new AccelerateDecelerateInterpolator())));
    return animatorSet;
  }

  public interface Callback {
    void onFinish();
  }
}
