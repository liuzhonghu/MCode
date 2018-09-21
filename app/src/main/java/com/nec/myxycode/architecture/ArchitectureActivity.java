package com.nec.myxycode.architecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.nec.myxycode.R;

public class ArchitectureActivity extends AppCompatActivity implements IPresenter {
  private MainPresenter mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_architecture);

    mPresenter = new MainPresenter();
    mPresenter.attachView(this);
    //将mPresenter加入宿主生命周期观察者队列
    getLifecycle().addObserver(mPresenter);
  }
}
