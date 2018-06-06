package com.nec.structure.mvc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nec.baselib.pop.Pop;
import com.nec.structure.BaseFragment;
import com.nec.structure.R;

/**
 * MVC:
 * 视图层(View)
 * 一般采用XML文件进行界面的描述，这些XML可以理解为AndroidApp的View
 *
 * 控制层(Controller)
 * Android的控制层的重任通常落在了众多的Activity(或者Fragment)的肩上。这句话也就暗含了不要在Activity中写代码，
 * 要通过Activity交割Model业务逻辑层处理，这样做的另外一个原因是Android中的Actiivity的响应时间是5s，
 * 如果耗时的操作放在这里，程序就很容易被回收掉。
 *
 * 模型层(Model)
 * 我们针对业务模型，建立的数据结构和相关的类，就可以理解为AndroidApp的Model，Model是与View无关，
 * 而与业务相关的。对数据库的操作、对网络等的操作都应该在Model里面处理，当然对业务计算等操作也是必须放在的该层
 *
 * PS:常见的MVC包含这三层,也有Controller负责中间调度和数据准备,即Controller与Model结合了
 *
 * 缺点:在Android开发中，Activity并不是一个标准的MVC模式中的Controller，它的首要职责是加载应用的布局和初始化
 * 用户界面，并接受并处理来自用户的操作请求，进而作出响应。随着界面及其逻辑的复杂度不断提升，Activity类的职责不断增加，以致变得庞大臃肿。
 */
public class MVCFragment extends BaseFragment {

  /**
   * View
   */
  EditText luckyNumEt;
  Button goBtn;
  ImageView luckyCover;
  TextView luckyDesc;

  /**
   * Model
   */
  DataModel dataModel;

  /**
   * Controller
   */
  public MVCFragment() {
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment MVCFragment.
   */
  public static MVCFragment newInstance() {
    MVCFragment fragment = new MVCFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_mvc, container, false);
    initView(view);
    dataModel = new DataModel();
    return view;
  }

  private void initView(View root) {
    luckyNumEt = root.findViewById(R.id.et_lucky_num);
    goBtn = root.findViewById(R.id.btn_go);
    luckyCover = root.findViewById(R.id.lucky_cover);
    luckyDesc = root.findViewById(R.id.lucky_desc);

    goBtn.setOnClickListener(v -> {
      Pop.show(goBtn);
      getLucky();
    });
  }

  private void getLucky() {
    String content = luckyNumEt.getText().toString();
    if (TextUtils.isEmpty(content)) {
      Toast.makeText(getContext(), "请输入幸运数", Toast.LENGTH_SHORT).show();
      return;
    }
    dataModel.getData(Integer.valueOf(content), data -> {
      if (data != null) {
        luckyCover.setImageResource(data.coverResId);
        luckyDesc.setText(data.desc);
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
