package com.nec.structure.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nec.baselib.NormalItem;
import com.nec.baselib.pop.Pop;
import com.nec.baselib.util.LoadingDialog;
import com.nec.structure.BaseFragment;
import com.nec.structure.R;
import java.util.List;

/**
 * View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity，Fragment)
 *
 * Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合)
 *
 * Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑
 *
 * View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合
 * PS: View interface,也可用于单元测试,测试具体业务功能
 */
public class MVPFragment extends BaseFragment implements ITestView {

  TestPresenter presenter;
  RecyclerView recyclerView;
  AnimalAdapter animalAdapter;

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
    presenter.init();

    initView(view);
    return view;
  }

  private void initView(View view) {
    recyclerView = view.findViewById(R.id.recyclerview);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    animalAdapter = new AnimalAdapter(getContext());
    recyclerView.setAdapter(animalAdapter);

    View button = view.findViewById(R.id.btn);
    button.setOnClickListener(v -> {
      Pop.show(button);
      getData();
    });
  }

  private void getData() {
    LoadingDialog.showLoadingProcess(getContext(), null);
    presenter.getAnimals();
  }

  @Override public void onAnimalResult(List<NormalItem> animals) {
    LoadingDialog.dismissModalProgressDialogue();
    if (animals != null) {
      animalAdapter.setDataList(animals);
    }
  }
}
