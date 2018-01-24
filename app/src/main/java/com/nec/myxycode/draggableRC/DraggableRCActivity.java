package com.nec.myxycode.draggableRC;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.nec.myxycode.R;
import com.nec.myxycode.draggableRC.fragment.DragGridFragment;
import com.nec.myxycode.draggableRC.fragment.DragListFragment;

public class DraggableRCActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_draggable_rc);

    findViewById(R.id.list_ver).setOnClickListener(this);
    findViewById(R.id.list_hori).setOnClickListener(this);
    findViewById(R.id.grid).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    Fragment fragment = null;
    switch (v.getId()) {
      case R.id.list_ver:
        fragment = DragListFragment.newInstance(true);
        break;
      case R.id.list_hori:
        fragment = DragListFragment.newInstance(false);
        break;
      case R.id.grid:
        fragment = DragGridFragment.newInstance();
        break;
    }
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment, fragment)
        .addToBackStack(null)
        .commit();
  }
}
