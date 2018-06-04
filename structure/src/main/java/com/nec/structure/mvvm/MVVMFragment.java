package com.nec.structure.mvvm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nec.structure.BaseFragment;
import com.nec.structure.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MVVMFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MVVMFragment extends BaseFragment {

  public MVVMFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   */
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
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_mvvm, container, false);
  }

}
