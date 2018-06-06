package com.nec.structure.mvp;

import com.nec.structure.mvp.base.BasePresenter;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/6/6
 */
public class TestPresenter extends BasePresenter<ITestView> {

  @Override public void attachView(ITestView mvpView) {
    super.attachView(mvpView);
  }

  @Override public void detachView() {
    super.detachView();
  }
}
