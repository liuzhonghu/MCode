package com.nec.structure.mvvm.base;

import java.util.List;

public interface BaseLoadListener<T> {
  /**
   * 加载数据成功
   */
  void loadSuccess(List<T> list);

  /**
   * 加载失败
   */
  void loadFailure(String message);

  /**
   * 开始加载
   */
  void loadStart();

  /**
   * 加载结束
   */
  void loadComplete();
}
