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
 * View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity，Fragment,Dialog ...)
 *
 * Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合)
 *
 * Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑
 *
 * View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合
 *
 * PS: View interface,也可用于单元测试,测试具体业务功能,省去大量的部署及测试的时间
 * 与MVC的区别:
 * 1.（最主要区别）View与Model并不直接交互
 * 2.通常View与Presenter是一对一的，但复杂的View可能绑定多个Presenter来处理逻辑
 * 3.Presenter与View的交互是通过接口来进行的，有利于添加单元测试
 *
 * MVP 强化:
 * 1 .加入模板方法（Template Method）
 * 转移逻辑操作之后可能部分较为复杂的Activity内代码量还是不少，于是需要在分层的基础上再加入模板方法（Template Method）。
 * 具体做法是在Activity内部分层。其中最顶层为BaseActivity，不做具体显示，而是提供一些基础样式，Dialog，ActionBar在内的内容，
 * 展现给用户的Activity继承BaseActivity，重写BaseActivity预留的方法。如有必要再进行二次继承，App中Activity之间的继承次数建议最多不超过3次。
 *
 * 2 .Model内部分层
 * 模型层（Model）中的整体代码量是最大的，一般由大量的Package组成，针对这部分需要做的就是在程序设计的过程中，
 * 做好模块的划分，进行接口隔离，在内部进行分层。
 *
 * 3 .强化Presenter
 * 强化Presenter的作用，将所有逻辑操作都放在Presenter内也容易造成Presenter内的代码量过大，对于这点，有一个方法
 * 是在UI层和Presenter之间设置中介者Mediator，将例如数据校验、组装在内的轻量级逻辑操作放在Mediator中；在Presenter
 * 和Model之间使用代理Proxy；通过上述两者分担一部分Presenter的逻辑操作，但整体框架的控制权还是在Presenter手中。
 * Mediator和Proxy不是必须的，只在Presenter负担过大时才建议使用。
 *
 * 4. 复杂View分层
 * 项目中View层(Activity\Fragment\Dialog)业务较为复杂,此时可以将View分成不同模块,不同模块分别独立处理业务(对应不同的Presenter)
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
