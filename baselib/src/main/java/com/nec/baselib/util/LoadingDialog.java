package com.nec.baselib.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import com.nec.baselib.R;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/6/8
 */
public class LoadingDialog {
  private static ProgressDialog mDialog = null;

  public static void showLoadingProcess(Context context,
      DialogInterface.OnCancelListener listener) {
    if (mDialog != null) {
      dismissModalProgressDialogue();
    }
    mDialog = new ProgressDialog(context, R.style.MAlertDialogStyle);
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    try {
      mDialog.show();
    } catch (Throwable e) {
      e.printStackTrace();
      return;
    }
    try {
      mDialog.setContentView(R.layout.simple_dialogue_layout);
      mDialog.setCancelable(true);
      if (listener != null) {
        mDialog.setOnCancelListener(listener);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void dismissModalProgressDialogue() {
    if (null != mDialog) {
      try {
        mDialog.dismiss();
      } catch (Exception e) {
        e.printStackTrace();
      }
      mDialog = null;
    }
  }
}
