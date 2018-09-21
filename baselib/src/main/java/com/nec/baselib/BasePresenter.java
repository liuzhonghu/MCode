package com.nec.baselib;

/**
 * @author Elijah <a href="zhonghu.liu@quvideo.com">Contact me.</a>
 * @since 2018/1/12
 * Base class that implements the Presenter interface and provides a base implementation for
 * {@link #attachView(T mvpView)}and {@link #detachView()}. It also handles keeping a reference
 * to the MvpView that
 * can be accessed from the children classes by calling {@link #getMvpView()}.
 */
public abstract class BasePresenter<T extends MvpView> extends MLifecycleObserver
    implements Presenter<T> {

  private T mvpView;

  @Override public void attachView(T editorView) {
    this.mvpView = editorView;
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
    if (!isViewAttached()) {
      throw new ViewNotAttachedException();
    }
  }

  public static class ViewNotAttachedException extends RuntimeException {
    public ViewNotAttachedException() {
      super("Please call Presenter.attachView(MvpView) before"
          + " requesting data to the Controller");
    }
  }
}
