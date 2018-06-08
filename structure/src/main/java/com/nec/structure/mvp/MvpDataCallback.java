package com.nec.structure.mvp;

import com.nec.baselib.NormalItem;
import java.util.List;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/6/8
 */
public interface MvpDataCallback {
  void onResult(List<NormalItem> list);
}
