package com.nec.myxycode.scrollnumber;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by liuzhonghu on 2017/8/15.
 *
 * @Description
 */

public class ScrollNumberV2 extends SurfaceView implements SurfaceHolder.Callback {
  private boolean isRun;
  private SurfaceHolder holder;
  private int scrollCircle; // 滚动圈数，0-9为一圈
  private int scrollNumber; // 滚动到的数字

  public ScrollNumberV2(Context context) {
    super(context);
  }

  public ScrollNumberV2(Context context, AttributeSet attr) {
    super(context, attr);

    holder = getHolder();
    holder.addCallback(this);
  }

  @Override public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
  }

  @Override public void surfaceCreated(SurfaceHolder arg0) {
    //isRun = true;
    //new NumberThread(holder).start();
  }

  @Override public void surfaceDestroyed(SurfaceHolder arg0) {
    isRun = false;
  }

  public void setCircleAndNumber(int scrollCircle, int scrollNumber) {
    this.scrollCircle = scrollCircle;
    this.scrollNumber = scrollNumber;
  }

  public void startScrollV2() {
    isRun = true;
    new NumberThread(holder).start();
  }

  private class NumberThread extends Thread {
    SurfaceHolder holder;

    public NumberThread(SurfaceHolder holder) {
      this.holder = holder;
    }

    @Override public void run() {
      super.run();

      float startX = 50f; // 数字的初始x坐标
      float startY = 50f; // 数字的初始y坐标
      float curY = startY; // 当前的y坐标

      Paint paint = new Paint();
      paint.setColor(Color.WHITE);
      paint.setAntiAlias(true);
      paint.setTextSize(sp2px(24));
      Paint.FontMetrics fontMetrics = paint.getFontMetrics();
      float numberHeight = fontMetrics.bottom - fontMetrics.top; // 数字的高度
      RectF r = new RectF(startX, startY - numberHeight / 2 - 10,
          startX + paint.measureText("" + scrollNumber), startY + numberHeight / 2);

      int elapseCount = 0; // 每一位要转过的数字的个数
      float width = paint.measureText("0"); // 一个数字的宽度
      int totalBits = ("" + scrollNumber).length(); // 要滚动的数字的位数
      boolean[] scrollFinished = new boolean[totalBits]; // 纪录每一位是否滚动完毕
      int toNumber = scrollNumber / (int) Math.pow(10, totalBits - 1); // 每一位要滚动到的数字，初始值为最高位的值
      int alreadyBits = 0; // 当前已滚动完毕的位数
      int movingStep = 10; // 每次滚动的距离
      int radix = 10; // 基数，也就是0-9共10个数字
      int interval = 50; // 每次滚动的间隔

      while (isRun) {
        Canvas canvas = null;

        try {
          canvas = holder.lockCanvas();

          canvas.clipRect(r);

          canvas.drawColor(Color.BLACK);

          for (int i = 0; i < totalBits; i++) {
            if (scrollFinished[i]) { // 该位滚动完成，直接绘制该位数字
              canvas.drawText(("" + scrollNumber).charAt(i) - 48 + "", startX + width * i, startY,
                  paint);
            } else { // 尚在滚动中，需要绘制该位以及下一位数字
              canvas.drawText("" + (elapseCount + 1) % radix, startX + width * i,
                  curY - numberHeight, paint);
              canvas.drawText("" + elapseCount % radix, startX + width * i, curY, paint);
            }
          }

          if (curY < startY + numberHeight) {
            curY += movingStep;

            if (elapseCount == scrollCircle * radix + toNumber - 1
                && curY
                > startY + numberHeight) { // 如果已经在滚向最后一位数字，并且如果滚动movingStep会超过要移动到的最终距离，则只滚动需要的部分
              curY = startY + numberHeight;
            }
          } else { // 两个数字之间的高度差是numberHeight，所以当新的数字出现时，要给curY和elapseCount重新赋值
            curY = startY;
            elapseCount++;

            if (elapseCount == scrollCircle * radix + toNumber) { // alreadyBits位已滚动到位
              scrollFinished[alreadyBits] = true;

              scrollCircle = 0; // 一旦最高位转动完毕，后面的位数转动都不再会超过一圈，所以圈数赋值为0
              elapseCount = (scrollNumber + "").charAt(alreadyBits) - 48; // 获取到alreadyBits位的值

              alreadyBits++;

              if (alreadyBits == totalBits) { // 所有位数滚动完毕
                isRun = false;
              } else { // 还有位数没有滚动完毕
                int nextValue = (scrollNumber + "").charAt(alreadyBits) - 48; // 下一位要滚动到的值

                toNumber = elapseCount; // 刚滚动完毕那位的值

                if (toNumber
                    >= nextValue) { // 如果下一位比当前位要小或者相等，那么要滚动到的位数就要＋10，比如21，那么当2滚动到位的时候，1那位的值是2，它还要滚动一圈才能到1
                  toNumber = radix + nextValue;
                } else {
                  toNumber = nextValue;
                }
              }
            }
          }

          Thread.sleep(interval);
        } catch (Exception e) {
          Log.d("ScrollNumber", "Error:" + e.toString());
        } finally {
          if (canvas != null) {
            holder.unlockCanvasAndPost(canvas);
          }
        }
      }
    }
  }

  private int dp2px(float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
        getResources().getDisplayMetrics());
  }

  private int sp2px(float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dpVal,
        getResources().getDisplayMetrics());
  }
}
