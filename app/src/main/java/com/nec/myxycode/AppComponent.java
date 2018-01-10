package com.nec.myxycode;

import com.nec.myxycode.fpsactor.FPSActorActivity;
import com.nec.myxycode.fpsactor.FPSRecyclerView;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {
  void inject(KApplication a);

  void inject(FPSActorActivity a);

  void inject(FPSRecyclerView a);
}