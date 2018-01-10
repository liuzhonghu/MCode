package com.nec.fpsactor;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2018/1/10
 */
public interface FrameDataCallback {
  /**
   * this is called for every doFrame() on the choreographer callback
   * use this very judiciously.  Logging synchronously from here is a bad
   * idea as doFrame will be called every 16-32ms.
   *
   * @param previousFrameNS previous vsync frame time in NS
   * @param currentFrameNS current vsync frame time in NS
   * @param droppedFrames number of dropped frames between current and previous times
   */
  void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames);
}
