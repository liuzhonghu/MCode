package com.nec.structure.mvvm.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHelper {
  /**
   * progressDialog
   */
  private ProgressDialog progressDialog;

  public static DialogHelper getInstance() {
    return LoadDialogHolder.instance;
  }

  private static class LoadDialogHolder {
    static DialogHelper instance = new DialogHelper();
  }

  public void show(Context context, String msg) {
    close();
    createDialog(context, msg);
    if (progressDialog != null && !progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  public void close() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void createDialog(Context context, String msg) {
    progressDialog = new ProgressDialog(context);
    progressDialog.setCancelable(false);
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.setMessage(msg);
    progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
      @Override public void onCancel(DialogInterface dialog) {
        progressDialog = null;
      }
    });
  }
}

