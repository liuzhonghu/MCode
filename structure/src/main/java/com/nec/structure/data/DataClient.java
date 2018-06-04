package com.nec.structure.data;

import com.nec.structure.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * @author Elijah <a href="https://github.com/liuzhonghu">Contact me.</a>
 * @desc 模拟数据层
 * @since 2018/6/1
 */
public class DataClient {

  private static int[] coverArray = {
      R.mipmap.beauty22, R.mipmap.b11, R.mipmap.b22, R.mipmap.timg, R.mipmap.timg2, R.mipmap.timg4,
      R.mipmap.timg5, R.mipmap.timg7, R.mipmap.timg11, R.mipmap.timg15, R.mipmap.timg18,
      R.mipmap.timg19, R.mipmap.timg110, R.mipmap.timg111, R.mipmap.timg113, R.mipmap.timg114,
      R.mipmap.timg115, R.mipmap.timg117, R.mipmap.timg118, R.mipmap.timg122, R.mipmap.timg129,
      R.mipmap.timg1112,
  };

  private static String[] descArray = {
      "1号小姐姐好美", "2号小姐姐好美", "3号小姐姐好美", "4号小姐姐好美", "5号小姐姐好美", "6号小姐姐好美", "7号小姐姐好美", "8号小姐姐好美",
      "9号小姐姐好美", "10号小姐姐好美", "11号小姐姐好美", "12号小姐姐好美", "14号小姐姐好美", "14号小姐姐好美", "15号小姐姐好美", "16号小姐姐好美",
      "17号小姐姐好美", "18号小姐姐好美", "19号小姐姐好美", "20号小姐姐好美", "21号小姐姐好美", "22号小姐姐好美"
  };

  public static void getData(int code, DataCallback callback) {
    Observable.just(true)
        .subscribeOn(Schedulers.io())
        .delay(5000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Boolean>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(Boolean aBoolean) {
            StructureData data = new StructureData();
            int index = (int) (Math.random() * coverArray.length);
            data.coverResId = coverArray[index];
            index = (int) (Math.random() * descArray.length);
            data.desc = descArray[index];
            if (callback != null) {
              callback.onResult(data);
            }
          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onComplete() {

          }
        });
  }
}
