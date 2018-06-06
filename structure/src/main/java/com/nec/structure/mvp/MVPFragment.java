package com.nec.structure.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nec.structure.BaseFragment;
import com.nec.structure.R;

/**
 * View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity，Fragment)
 *
 * Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合)
 *
 * Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑
 *
 * View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合
 */
public class MVPFragment extends BaseFragment implements ITestView {

  TestPresenter presenter;
  RecyclerView recyclerView;

  public MVPFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment MVPFragment.
   */
  public static MVPFragment newInstance() {
    MVPFragment fragment = new MVPFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_mv, container, false);
    presenter = new TestPresenter();
    presenter.attachView(this);

    recyclerView = view.findViewById(R.id.recyclerview);
    return view;
  }
}
