package com.nec.structure.mvp;

import com.nec.baselib.MvpView;
import com.nec.baselib.NormalItem;
import java.util.List;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/6/6
 */
public interface ITestView extends MvpView {

  void onAnimalResult(List<NormalItem> animals);
}
