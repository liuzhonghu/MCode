package com.nec.kboardlib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * 图片选择器
 */
public class MultiImageSelector {

  public static final String EXTRA_RESULT = ImageSelectorConstant.EXTRA_RESULT;
  private boolean mIsShowCarema = true;
  private int mMaxCount = 9;
  private int mSelectMode = ImageSelectorConstant.MODE_MULTI;
  private ArrayList<String> mOriginData;
  private static MultiImageSelector sSelector;
  private Context mContext;
  private int mRequstType;

  private MultiImageSelector(Context context) {
    mContext = context;
  }

  public static MultiImageSelector create(Context context) {
    if (sSelector == null) {
      sSelector = new MultiImageSelector(context);
    }
    return sSelector;
  }

  public MultiImageSelector showCamera(boolean show) {
    mIsShowCarema = show;
    return sSelector;
  }

  public MultiImageSelector count(int count) {
    mMaxCount = count;
    return sSelector;
  }

  public MultiImageSelector single() {
    mSelectMode = ImageSelectorConstant.MODE_SINGLE;
    return sSelector;
  }

  public MultiImageSelector multi() {
    mSelectMode = ImageSelectorConstant.MODE_MULTI;
    return sSelector;
  }

  public MultiImageSelector origin(ArrayList<String> images) {
    mOriginData = images;
    return sSelector;
  }

  public void start(Activity activity, int requestCode) {
    if (hasPermission()) {
      activity.startActivityForResult(createIntent(), requestCode);
    } else {
      Toast.makeText(mContext, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
    }
  }

  public void start(android.app.Fragment fragment, int[] boundsInts, int requestCode) {
    if (hasPermission()) {
      mRequstType = requestCode;
      fragment.startActivityForResult(createIntent(boundsInts), requestCode);
    } else {
      Toast.makeText(mContext, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
    }
  }

  public void start(android.support.v4.app.Fragment fragment, int[] boundsInts, int requestCode) {
    if (hasPermission()) {
      mRequstType = requestCode;
      fragment.startActivityForResult(createIntent(boundsInts), requestCode);
    } else {
      Toast.makeText(mContext, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
    }
  }

  private boolean hasPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      // Permission was added in API Level 16
      return ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
          == PackageManager.PERMISSION_GRANTED;
    }
    return true;
  }

  private Intent createIntent() {
    return createIntent(null);
  }

  private Intent createIntent(int[] boundsInts) {
    Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
    if (boundsInts != null) {
      intent.putExtra(ImageSelectorConstant.EXTRA_BOUNDS, boundsInts);
    }
    intent.putExtra(ImageSelectorConstant.EXTRA_SHOW_CAMERA, mIsShowCarema);
    intent.putExtra(ImageSelectorConstant.EXTRA_SELECT_COUNT, mMaxCount);
    if (mOriginData != null) {
      intent.putStringArrayListExtra(ImageSelectorConstant.EXTRA_DEFAULT_SELECTED_LIST,
          mOriginData);
    }
    intent.putExtra(ImageSelectorConstant.EXTRA_SELECT_MODE, mSelectMode);
    intent.putExtra(ImageSelectorConstant.EXTRA_REQUEST_TYPE, mRequstType);
    return intent;
  }
}
