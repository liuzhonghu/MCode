package com.nec.structure.mvp.base;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the MvpView that
 * can be accessed from the children classes by calling getView().
 */
public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

  private T mvpView;

  @Override public void attachView(T mvpView) {
    this.mvpView = mvpView;
  }

  @Override public void detachView() {
    mvpView = null;
  }

  public boolean isViewAttached() {
    return mvpView != null;
  }

  public T getMvpView() {
    return mvpView;
  }

  public void checkViewAttached() {
    if (!isViewAttached()) throw new MvpViewViewNotAttachedException();
  }

  public static class MvpViewViewNotAttachedException extends RuntimeException {
    public MvpViewViewNotAttachedException() {
      super(
          "Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter");
    }
  }
}
