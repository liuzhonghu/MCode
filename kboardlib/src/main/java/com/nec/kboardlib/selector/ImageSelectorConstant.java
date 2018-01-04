package com.nec.kboardlib.selector;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @desc
 * @since 2017/12/15
 */

public class ImageSelectorConstant {

  // Single choice
  public static final int MODE_SINGLE = 0;
  // Multi choice
  public static final int MODE_MULTI = 1;

  /**
   * Max image size，int，{@link #DEFAULT_IMAGE_SIZE} by default
   */
  public static final String EXTRA_SELECT_COUNT = "max_select_count";
  /**
   * Select mode，{@link #MODE_MULTI} by default
   */
  public static final String EXTRA_SELECT_MODE = "select_count_mode";
  /**
   * Whether show camera，true by default
   */
  public static final String EXTRA_SHOW_CAMERA = "show_camera";
  /**
   * Result data set，ArrayList&lt;String&gt;
   */
  public static final String EXTRA_RESULT = "select_result";
  /**
   * View bounds;
   */
  public static final String EXTRA_BOUNDS = "activity_bounds";
  /**
   * Original data set
   */
  public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
  // Default image size
  public static final int DEFAULT_IMAGE_SIZE = 9;
  public static final String EXTRA_REQUEST_TYPE = "request_type";
}
