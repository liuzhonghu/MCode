package com.nec.structure.mvp;

import com.nec.baselib.NormalItem;
import com.nec.structure.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc
 * @since 2018/6/8
 */
public class MvpViewModel {

  public MvpViewModel() {
  }

  public void getAnimalData(MvpDataCallback callback) {
    Observable.create(new ObservableOnSubscribe<List<NormalItem>>() {
      @Override public void subscribe(ObservableEmitter<List<NormalItem>> e) throws Exception {
        List<NormalItem> animals = createAnimals();
        e.onNext(animals);
      }
    })
        .subscribeOn(Schedulers.io())
        .delay(3000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<NormalItem>>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(List<NormalItem> list) {
            if (callback != null) {
              callback.onResult(list);
            }
          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onComplete() {

          }
        });
  }

  private List<NormalItem> createAnimals() {
    List<NormalItem> dataList = new ArrayList<>();
    dataList.add(new NormalItem(7, "Brown_cow", R.drawable.brown_cow));
    dataList.add(new NormalItem(7 + 1, "Butterfly", R.drawable.butterfly));
    dataList.add(new NormalItem(7 + 2, "Camel", R.drawable.camel));
    dataList.add(new NormalItem(7 + 3, "Crocodile", R.drawable.crocodile));
    dataList.add(new NormalItem(7 + 4, "Froggy", R.drawable.froggy));
    dataList.add(new NormalItem(7 + 5, "Giraffe", R.drawable.giraffe));
    dataList.add(new NormalItem(7 + 6, "Kitten", R.drawable.kitten));
    dataList.add(new NormalItem(7 + 7, "Lion", R.drawable.lion));
    dataList.add(new NormalItem(7 + 8, "Monkey", R.drawable.monkey));
    dataList.add(new NormalItem(7 + 9, "Panda", R.drawable.panda));
    dataList.add(new NormalItem(7 + 10, "Tiger", R.drawable.tiger));
    dataList.add(new NormalItem(7 + 12, "Turtle", R.drawable.turtle));
    dataList.add(new NormalItem(8, "Brown_cow", R.drawable.brown_cow));
    dataList.add(new NormalItem(8 + 1, "Butterfly", R.drawable.butterfly));
    dataList.add(new NormalItem(8 + 2, "Camel", R.drawable.camel));
    dataList.add(new NormalItem(8 + 3, "Crocodile", R.drawable.crocodile));
    dataList.add(new NormalItem(8 + 4, "Froggy", R.drawable.froggy));
    dataList.add(new NormalItem(8 + 5, "Giraffe", R.drawable.giraffe));
    dataList.add(new NormalItem(8 + 6, "Kitten", R.drawable.kitten));
    dataList.add(new NormalItem(8 + 7, "Lion", R.drawable.lion));
    dataList.add(new NormalItem(8 + 8, "Monkey", R.drawable.monkey));
    dataList.add(new NormalItem(8 + 9, "Panda", R.drawable.panda));
    dataList.add(new NormalItem(8 + 10, "Tiger", R.drawable.tiger));
    dataList.add(new NormalItem(8 + 12, "Turtle", R.drawable.turtle));
    return dataList;
  }
}
