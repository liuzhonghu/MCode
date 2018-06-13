package com.nec.structure.mvvm.model;

import com.nec.structure.mvvm.base.BaseLoadListener;
import com.nec.structure.mvvm.bean.SimpleNewsBean;

public interface INewsModel {
  /**
   * 获取新闻数据
   *
   * @param page 页数
   */
  void loadNewsData(int page, BaseLoadListener<SimpleNewsBean> loadListener);
}
