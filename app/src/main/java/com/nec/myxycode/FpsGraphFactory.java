package com.nec.myxycode;

import android.app.Application;

public class FpsGraphFactory {

  public static AppComponent getObjectGraph(Application context) {
    return DaggerAppComponent.builder().appModule(new AppModule(context)).build();
  }
}