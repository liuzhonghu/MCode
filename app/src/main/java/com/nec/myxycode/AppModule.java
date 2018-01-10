package com.nec.myxycode;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class AppModule {
  private Application context;

  public AppModule(Application context) {
    this.context = context;
  }

  @Singleton @Provides Application provideApplication() {
    return context;
  }
}