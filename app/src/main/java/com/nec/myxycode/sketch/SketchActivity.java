package com.nec.myxycode.sketch;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.nec.kboardlib.KBoardFragment;
import com.nec.myxycode.R;

public class SketchActivity extends AppCompatActivity
    implements KBoardFragment.OnFragmentInteractionListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sketch);

    FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
    KBoardFragment boardFragment = KBoardFragment.newInstance();
    ts.add(R.id.container, boardFragment, "kb").commit();
  }

  @Override public void onFragmentInteraction() {

  }
}
