package com.nec.structure.mvvm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nec.structure.BaseFragment;
import com.nec.structure.R;
import com.nec.structure.databinding.FragmentMvvmBinding;
import com.nec.structure.mvvm.adapter.NewsAdapter;
import com.nec.structure.mvvm.helper.DialogHelper;
import com.nec.structure.mvvm.utils.ToastUtils;
import com.nec.structure.mvvm.viewmodel.NewsVM;

import static com.nec.structure.mvvm.constant.MainConstant.LoadData.FIRST_LOAD;

/**
 * Model
 * Model层就是职责数据的存储、读取网络数据、操作数据库数据以及I/O，一般会有一个ViewModel对象来调用获取这一部分的数据。
 *
 * View
 * 我感觉这里的View才是真正的View，为什么这么说？View层做的仅仅和UI相关的工作，我们只在XML、Activity、Fragment写View层的代码，
 * View层不做和业务相关的事，也就是我们的Activity 不写和业务逻辑相关代码，一般Activity不写更新UI的代码，
 * 如果非得要写，那更新的UI必须和业务逻辑和数据是没有关系的，只是单纯UI逻辑来更新UI，
 * 比如：滑动时头部颜色渐变、editttext根据输入内容显示隐藏等，简单的说：View层不做任何业务逻辑、不涉及操作数据、不处理数据、UI和数据严格的分开。
 *
 * ViewModel
 * ViewModel 只做和业务逻辑和业务数据相关的事，不做任何和UI、控件相关的事，ViewModel 层不会持有任何控件的引用，
 * 更不会在ViewModel中通过UI控件的引用去做更新UI的事情。ViewModel就是专注于业务的逻辑处理，
 * 操作的也都是对数据进行操作，这些个数据源绑定在相应的控件上会自动去更改UI，开发者不需要关心更新UI的事情。
 *
 *
 * 总结: View层的Activity通过DataBinding生成Binding实例,把这个实例传递给ViewModel，ViewModel层通过把自身与Binding实例绑定，
 * 从而实现View中layout与ViewModel的双向绑定
 * mvvm的缺点数据绑定使得 Bug 很难被调试。你看到界面异常了，有可能是你 View 的代码有 Bug，也可能是 Model 的代码有问题。
 * 数据绑定使得一个位置的 Bug 被快速传递到别的位置，要定位原始出问题的地方就变得不那么容易了。
 *
 * PS: 通常情况下，数据的流向是单方面的，只能从代码流向UI，也就是单向绑定；
 * 而双向绑定的数据流向是双向的，当业务代码中的数据改变时，UI上的数据能够得到刷新；
 * 当用户通过UI交互编辑了数据时，数据的变化也能自动的更新到业务代码中的数据上。
 * 而DataBinding是一个实现数据和UI绑定的框架，是构建MVVM模式的一个关键的工具，它是支持双向绑定的。
 */
public class MVVMFragment extends BaseFragment implements INewsView, XRecyclerView.LoadingListener {

  private FragmentMvvmBinding binding;
  private NewsAdapter newsAdapter;
  private NewsVM newsVM;

  public MVVMFragment() {
    // Required empty public constructor
  }

  public static MVVMFragment newInstance() {
    MVVMFragment fragment = new MVVMFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    //View view =inflater.inflate(R.layout.fragment_mvvm, container, false);
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mvvm, container, false);
    initRecyclerView();
    newsVM = new NewsVM(this, newsAdapter);
    return binding.getRoot();
  }

  private void initRecyclerView() {
    binding.newsRv.setRefreshProgressStyle(ProgressStyle.BallClipRotate); //设置下拉刷新的样式
    binding.newsRv.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate); //设置上拉加载更多的样式
    binding.newsRv.setArrowImageView(R.mipmap.pull_down_arrow);
    binding.newsRv.setLoadingListener(this);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    binding.newsRv.setLayoutManager(layoutManager);
    newsAdapter = new NewsAdapter(getContext());
    binding.newsRv.setAdapter(newsAdapter);
  }

  @Override public void onRefresh() {
    //下拉刷新
    newsVM.loadRefreshData();
  }

  @Override public void onLoadMore() {
    //上拉加载更多
    newsVM.loadMoreData();
  }

  @Override public void loadStart(int loadType) {
    if (loadType == FIRST_LOAD) {
      DialogHelper.getInstance().show(getContext(), "加载中...");
    }
  }

  @Override public void loadComplete() {
    DialogHelper.getInstance().close();
    binding.newsRv.loadMoreComplete(); //结束加载
    binding.newsRv.refreshComplete(); //结束刷新
  }

  @Override public void loadFailure(String message) {
    DialogHelper.getInstance().close();
    binding.newsRv.loadMoreComplete(); //结束加载
    binding.newsRv.refreshComplete(); //结束刷新
    ToastUtils.show(getContext(), message);
  }
}
