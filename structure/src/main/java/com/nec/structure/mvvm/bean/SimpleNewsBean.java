package com.nec.structure.mvvm.bean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;


public class SimpleNewsBean {
  public ObservableInt color = new ObservableInt();
  public ObservableField<String> thumbnail = new ObservableField<>();
  public ObservableField<String> description = new ObservableField<>();
  public ObservableInt id = new ObservableInt();
  public ObservableField<String> name = new ObservableField<>();
  public ObservableBoolean isGood = new ObservableBoolean(); //是否点赞
}
