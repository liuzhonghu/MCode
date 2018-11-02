package com.nec.myxycode.rxbinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.nec.baselib.pop.Pop;
import com.nec.myxycode.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

public class RxBindingActivity extends AppCompatActivity {

  public CompositeDisposable mCompositeDisposable;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rx_binding);
    mCompositeDisposable = new CompositeDisposable();
    addListener();
  }

  private void addListener() {
    shake();
    watcher();
    login();
  }

  private void login() {
    EditText editTextName = findViewById(R.id.et_name);
    EditText editTextPwd = findViewById(R.id.et_pwd);
    Button btnLogin = findViewById(R.id.btn_login);

    Observable<String> nameObservable =
        RxTextView.textChanges(editTextName).map(String::valueOf);
    Observable<String> pwdObservable =
        RxTextView.textChanges(editTextPwd).map(String::valueOf);

    Disposable disposable = Observable.combineLatest(nameObservable, pwdObservable,
        (name, password) -> isNameValid(name) && isPwdValid(password))
        .subscribe(aBoolean -> RxView.enabled(btnLogin).accept(aBoolean));
    mCompositeDisposable.add(disposable);
  }

  private boolean isNameValid(String name) {
    return "HuHuHu".equals(name);
  }

  private boolean isPwdValid(String pwd) {
    return "123456".equals(pwd);
  }

  private void watcher() {
    EditText editText = findViewById(R.id.et_watcher);
    TextView textView = findViewById(R.id.tv_watcher);

    Disposable disposable = RxTextView.textChanges(editText)
        .debounce(2000, TimeUnit.MILLISECONDS)
        .map(CharSequence::toString)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(s -> textView.setText("最终结果：" + s));
    mCompositeDisposable.add(disposable);
  }

  private void shake() {
    Button button = findViewById(R.id.btn_counter);
    TextView textView = findViewById(R.id.tv_counter);

    Disposable disposable = RxView.clicks(button).throttleFirst(5, TimeUnit.SECONDS)
        //.throttleWithTimeout(2,TimeUnit.SECONDS) 事件延迟两秒执行
        .subscribe(o -> {
          Pop.show(button);
          textView.setText(System.currentTimeMillis() + "");
        });
    mCompositeDisposable.add(disposable);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mCompositeDisposable != null) {
      mCompositeDisposable.clear();
    }
  }
}
