package com.nec.structure.mvp;

import com.nec.baselib.BasePresenter;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/6/6
 */
public class TestPresenter extends BasePresenter<ITestView> {

  MvpViewModel viewModel;

  @Override public void attachView(ITestView mvpView) {
    super.attachView(mvpView);
  }

  @Override public void detachView() {
    super.detachView();
  }

  public void init() {
    viewModel = new MvpViewModel();
  }

  public void getAnimals() {
    if (viewModel != null) {
      viewModel.getAnimalData(list -> getMvpView().onAnimalResult(list));
    }
  }
}
