package com.nec.structure;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.nec.baselib.pop.Pop;
import com.nec.structure.mvc.MVCFragment;
import com.nec.structure.mvp.MVPFragment;
import com.nec.structure.mvvm.MVVMFragment;

public class StructureActivity extends AppCompatActivity {

  FragmentManager fragmentManager;
  Fragment mvcFragment, mvpFragment, mvvmFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_structure);

    fragmentManager = getSupportFragmentManager();
  }

  public void mvc(View view) {
    Pop.show(findViewById(R.id.mvc));
    if (mvcFragment == null) {
      mvcFragment = MVCFragment.newInstance();
    }

    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.container, mvcFragment, "mvc").commit();
  }

  public void mvp(View view) {
    Pop.show(findViewById(R.id.mvp));
    if (mvpFragment == null) {
      mvpFragment = MVPFragment.newInstance();
    }

    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.container, mvpFragment, "mvp").commit();
  }

  public void mvvm(View view) {
    Pop.show(findViewById(R.id.mvvm));
    if (mvvmFragment == null) {
      mvvmFragment = MVVMFragment.newInstance();
    }

    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.container, mvvmFragment, "mvvm").commit();
  }

  @Override public void onBackPressed() {
    if (mvcFragment != null && mvcFragment.isVisible()) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.remove(mvcFragment).commit();
      return;
    }
    if (mvpFragment != null && mvpFragment.isVisible()) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.remove(mvpFragment).commit();
      return;
    }
    if (mvvmFragment != null && mvvmFragment.isVisible()) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.remove(mvvmFragment).commit();
      return;
    }
    finish();
  }
}
