package com.nec.structure.mvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;
import com.nec.structure.BR;
import com.nec.structure.R;
import com.nec.structure.mvvm.base.BaseAdapter;
import com.nec.structure.mvvm.base.BaseViewHolder;
import com.nec.structure.mvvm.bean.SimpleNewsBean;
import com.nec.structure.mvvm.utils.ToastUtils;

public class NewsAdapter extends BaseAdapter<SimpleNewsBean, BaseViewHolder> {

  public NewsAdapter(Context context) {
    super(context);
  }

  @Override public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
    ViewDataBinding dataBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_news, parent, false);
    return new BaseViewHolder(dataBinding);
  }

  @Override public void onBindVH(BaseViewHolder baseViewHolder, int position) {
    ViewDataBinding binding = baseViewHolder.getBinding();
    binding.setVariable(BR.simpleNewsBean, mList.get(position));
    binding.setVariable(BR.position, position);
    binding.setVariable(BR.adapter, this);
    binding.executePendingBindings();
  }

  /**
   * 点赞
   */
  public void clickDianZan(SimpleNewsBean simpleNewsBean, int position) {
    if (simpleNewsBean.isGood.get()) {
      simpleNewsBean.isGood.set(false);
      ToastUtils.show(mContext, "取消点赞 position=" + position);
    } else {
      simpleNewsBean.isGood.set(true);
      ToastUtils.show(mContext, "点赞成功 position=" + position);
    }
  }
}
