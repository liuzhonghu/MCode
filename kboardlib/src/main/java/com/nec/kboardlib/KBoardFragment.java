package com.nec.kboardlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.nec.kboardlib.model.SketchData;
import com.nec.kboardlib.model.StrokeRecord;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.nec.kboardlib.model.StrokeRecord.STROKE_TYPE_CIRCLE;
import static com.nec.kboardlib.model.StrokeRecord.STROKE_TYPE_DRAW;
import static com.nec.kboardlib.model.StrokeRecord.STROKE_TYPE_ERASER;
import static com.nec.kboardlib.model.StrokeRecord.STROKE_TYPE_LINE;
import static com.nec.kboardlib.model.StrokeRecord.STROKE_TYPE_RECTANGLE;
import static com.nec.kboardlib.model.StrokeRecord.STROKE_TYPE_TEXT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KBoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KBoardFragment extends Fragment
    implements View.OnClickListener, KSketchView.OnDrawChangedListener {
  private OnFragmentInteractionListener mListener;

  final String TAG = getClass().getSimpleName();

  public interface SendBtnCallback {
    void onSendBtnClick(File filePath);
  }

  static final int COLOR_BLACK = Color.parseColor("#ff000000");
  static final int COLOR_RED = Color.parseColor("#ffff4444");
  static final int COLOR_GREEN = Color.parseColor("#ff99cc00");
  static final int COLOR_ORANGE = Color.parseColor("#ffffbb33");
  static final int COLOR_BLUE = Color.parseColor("#ff33b5e5");
  public static final int REQUEST_IMAGE = 2;
  public static final int REQUEST_BACKGROUND = 3;

  private static final float BTN_ALPHA = 0.4f;

  //文件保存目录
  public static final String TEMP_FILE_PATH =
      Environment.getExternalStorageDirectory().toString() + "/YingHe/temp/";
  public static final String FILE_PATH =
      Environment.getExternalStorageDirectory().toString() + "/YingHe/sketchPhoto/";
  public static final String TEMP_FILE_NAME = "temp_";

  int keyboardHeight;
  int textOffX;
  int textOffY;

  KSketchView mSketchView;//画板

  View controlLayout;//控制布局

  ImageView btn_add;//添加画板
  ImageView btn_stroke;//画笔
  ImageView btn_eraser;//橡皮擦
  ImageView btn_undo;//撤销
  ImageView btn_redo;//取消撤销
  ImageView btn_photo;//加载图片
  ImageView btn_background;//背景图片
  ImageView btn_drag;//拖拽
  ImageView btn_save;//保存
  ImageView btn_empty;//清空
  ImageView btn_send;//推送
  ImageView btn_send_space;//推送按钮间隔

  RadioGroup strokeTypeRG, strokeColorRG;

  Activity activity;//上下文

  int strokeMode;//模式
  int strokeType;//模式

  EditText saveET;
  AlertDialog saveDialog;
  GridView sketchGV;
  SketchDataGridAdapter sketchGVAdapter;

  int pupWindowsDPWidth = 300;//弹窗宽度，单位DP
  int strokePupWindowsDPHeight = 275;//画笔弹窗高度，单位DP
  int eraserPupWindowsDPHeight = 90;//橡皮擦弹窗高度，单位DP

  SendBtnCallback sendBtnCallback;
  boolean isTeacher;
  PopupWindow strokePopupWindow, eraserPopupWindow, textPopupWindow;//画笔、橡皮擦参数设置弹窗实例
  private View popupStrokeLayout, popupEraserLayout, popupTextLayout;//画笔、橡皮擦弹窗布局
  private SeekBar strokeSeekBar, strokeAlphaSeekBar, eraserSeekBar;
  private ImageView strokeImageView, strokeAlphaImage, eraserImageView;//画笔宽度，画笔不透明度，橡皮擦宽度IV
  private EditText strokeET;//绘制文字的内容
  private int size;
  private AlertDialog dialog;
  private ArrayList<String> mSelectPath;

  private List<SketchData> sketchDataList = new ArrayList<>();
  //    //    private SketchData curSketchData;
  //    private List<String> sketchPathList = new ArrayList<>();
  //    private int dataPosition;

  //
  public static int sketchViewHeight;
  public static int sketchViewWidth;
  public static int sketchViewRight;
  public static int sketchViewBottom;
  public static int decorHeight;
  public static int decorWidth;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment KBoardFragment.
   */
  public static KBoardFragment newInstance() {
    KBoardFragment fragment = new KBoardFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = getActivity();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View rootView = inflater.inflate(R.layout.fragment_kboard, container, false);
    rootView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            //下面的代码主要是为了解决软键盘弹出后遮挡住文字录入PopWindow的问题
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);//获取rootView的可视区域
            int screenHeight = rootView.getHeight();//获取rootView的高度
            keyboardHeight =
                screenHeight - (r.bottom - r.top);//用rootView的高度减去rootView的可视区域高度得到软键盘高度
            if (textOffY > (sketchViewHeight - keyboardHeight)) {//如果输入焦点出现在软键盘显示的范围内则进行布局上移操作
              rootView.setTop(-keyboardHeight);//rootView整体上移软键盘高度
              //更新PopupWindow的位置
              int x = textOffX;
              int y = textOffY - mSketchView.getHeight();
              textPopupWindow.update(mSketchView, x, y, WindowManager.LayoutParams.WRAP_CONTENT,
                  WindowManager.LayoutParams.WRAP_CONTENT);
            }
          }
        });
    initView(rootView);//载入所有的按钮实例
    initDrawParams();//初始化绘画参数
    initPopupWindows();//初始化弹框
    initSaveDialog();
    initData();
    initSketchGV();
    return rootView;
  }

  private void initView(View view) {

    sketchGV = (GridView) view.findViewById(R.id.sketch_data_gv);

    //画板整体布局
    mSketchView = (KSketchView) view.findViewById(R.id.sketch_view);

    controlLayout = view.findViewById(R.id.controlLayout);

    btn_add = (ImageView) view.findViewById(R.id.btn_add);
    btn_stroke = (ImageView) view.findViewById(R.id.btn_stroke);
    btn_eraser = (ImageView) view.findViewById(R.id.btn_eraser);
    btn_undo = (ImageView) view.findViewById(R.id.btn_undo);
    btn_redo = (ImageView) view.findViewById(R.id.btn_redo);
    btn_photo = (ImageView) view.findViewById(R.id.btn_photo);
    btn_background = (ImageView) view.findViewById(R.id.btn_background);
    btn_drag = (ImageView) view.findViewById(R.id.btn_drag);
    btn_save = (ImageView) view.findViewById(R.id.btn_save);
    btn_empty = (ImageView) view.findViewById(R.id.btn_empty);
    btn_send = (ImageView) view.findViewById(R.id.btn_send);
    btn_send_space = (ImageView) view.findViewById(R.id.btn_send_space);
    if (isTeacher) {
      btn_send.setVisibility(View.VISIBLE);
      btn_send_space.setVisibility(View.VISIBLE);
    }

    //设置点击监听
    mSketchView.setOnDrawChangedListener(this);//设置撤销动作监听器
    btn_add.setOnClickListener(this);
    btn_stroke.setOnClickListener(this);
    btn_eraser.setOnClickListener(this);
    btn_undo.setOnClickListener(this);
    btn_redo.setOnClickListener(this);
    btn_empty.setOnClickListener(this);
    btn_save.setOnClickListener(this);
    btn_photo.setOnClickListener(this);
    btn_background.setOnClickListener(this);
    btn_drag.setOnClickListener(this);
    btn_send.setOnClickListener(this);
    mSketchView.setTextWindowCallback(new KSketchView.TextWindowCallback() {
      @Override public void onText(View anchor, StrokeRecord record) {
        textOffX = record.textOffX;
        textOffY = record.textOffY;
        showTextPopupWindow(anchor, record);
      }
    });

    // popupWindow布局
    LayoutInflater inflater =
        (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    //画笔弹窗布局
    popupStrokeLayout = inflater.inflate(R.layout.popup_sketch_stroke, null);
    strokeImageView = (ImageView) popupStrokeLayout.findViewById(R.id.stroke_circle);
    strokeAlphaImage = (ImageView) popupStrokeLayout.findViewById(R.id.stroke_alpha_circle);
    strokeSeekBar = (SeekBar) (popupStrokeLayout.findViewById(R.id.stroke_seekbar));
    strokeAlphaSeekBar = (SeekBar) (popupStrokeLayout.findViewById(R.id.stroke_alpha_seekbar));
    //画笔颜色
    strokeTypeRG = (RadioGroup) popupStrokeLayout.findViewById(R.id.stroke_type_radio_group);
    strokeColorRG = (RadioGroup) popupStrokeLayout.findViewById(R.id.stroke_color_radio_group);

    //橡皮擦弹窗布局
    popupEraserLayout = inflater.inflate(R.layout.popup_sketch_eraser, null);
    eraserImageView = (ImageView) popupEraserLayout.findViewById(R.id.stroke_circle);
    eraserSeekBar = (SeekBar) (popupEraserLayout.findViewById(R.id.stroke_seekbar));
    //文本录入弹窗布局
    popupTextLayout = inflater.inflate(R.layout.popup_sketch_text, null);
    strokeET = (EditText) popupTextLayout.findViewById(R.id.text_pupwindow_et);
    getSketchSize();//计算选择图片弹窗的高宽
  }

  private void initData() {
    SketchData newSketchData = new SketchData();
    sketchDataList.add(newSketchData);
    mSketchView.setSketchData(newSketchData);
  }

  private void initSketchGV() {
    sketchGVAdapter = new SketchDataGridAdapter(activity, sketchDataList,
        new SketchDataGridAdapter.OnActionCallback() {
          @Override public void onDeleteCallback(int position) {
            sketchDataList.remove(position);
            sketchGVAdapter.notifyDataSetChanged();
          }

          @Override public void onAddCallback() {
            SketchData newSketchData = new SketchData();
            sketchDataList.add(newSketchData);
            mSketchView.updateSketchData(newSketchData);
            mSketchView.setEditMode(KSketchView.EDIT_STROKE);//切换笔画编辑模式
            showSketchView(true);
          }

          @Override public void onSelectCallback(SketchData sketchData) {
            mSketchView.updateSketchData(sketchData);
            mSketchView.setEditMode(KSketchView.EDIT_PHOTO);//切换图片编辑模式
            showSketchView(true);
          }
        });
    sketchGV.setAdapter(sketchGVAdapter);
  }

  private void showSketchView(boolean b) {
    mSketchView.setVisibility(b ? View.VISIBLE : View.GONE);
    sketchGV.setVisibility(!b ? View.VISIBLE : View.GONE);
  }

  private void initSaveDialog() {
    saveET = new EditText(activity);
    saveET.setHint("新文件名");
    saveET.setGravity(Gravity.CENTER);
    saveET.setSingleLine();
    saveET.setInputType(EditorInfo.TYPE_CLASS_TEXT);
    saveET.setImeOptions(EditorInfo.IME_ACTION_DONE);
    saveET.setSelectAllOnFocus(true);
    saveET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          ScreenUtils.hideInput(saveDialog.getCurrentFocus());
          saveDialog.dismiss();
          String input = saveET.getText().toString();
          saveInUI(input + ".png");
        }
        return true;
      }
    });
    saveDialog = new AlertDialog.Builder(getActivity()).setTitle("请输入保存文件名")
        .setMessage("")
        .setView(saveET)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            ScreenUtils.hideInput(saveDialog.getCurrentFocus());
            String input = saveET.getText().toString();
            saveInUI(input + ".png");
          }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            ScreenUtils.hideInput(saveDialog.getCurrentFocus());
          }
        })
        .setCancelable(false)
        .create();
  }

  private void initDrawParams() {
    //默认为画笔模式
    strokeMode = STROKE_TYPE_DRAW;

    //画笔宽度缩放基准参数
    Drawable circleDrawable = getResources().getDrawable(R.drawable.circle);
    assert circleDrawable != null;
    size = circleDrawable.getIntrinsicWidth();
  }

  private void initPopupWindows() {
    initStrokePop();
    initEraserPop();
    initTextPop();
  }

  private void initTextPop() {
    textPopupWindow = new PopupWindow(activity);
    textPopupWindow.setContentView(popupTextLayout);
    textPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);//宽度200dp
    textPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);//高度自适应
    textPopupWindow.setFocusable(true);
    textPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    textPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
    textPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override public void onDismiss() {
        if (!strokeET.getText().toString().equals("")) {
          StrokeRecord record = new StrokeRecord(strokeType);
          record.text = strokeET.getText().toString();
        }
      }
    });
  }

  private void initEraserPop() {
    //橡皮擦弹窗
    eraserPopupWindow = new PopupWindow(activity);
    eraserPopupWindow.setContentView(popupEraserLayout);//设置主体布局
    eraserPopupWindow.setWidth(ScreenUtils.dip2px(getActivity(), pupWindowsDPWidth));//宽度200dp
    //        eraserPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);//高度自适应
    eraserPopupWindow.setHeight(ScreenUtils.dip2px(getActivity(), eraserPupWindowsDPHeight));//高度自适应
    eraserPopupWindow.setFocusable(true);
    eraserPopupWindow.setBackgroundDrawable(new BitmapDrawable());//设置空白背景
    eraserPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//动画
    //橡皮擦宽度拖动条
    eraserSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onStopTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setSeekBarProgress(progress, STROKE_TYPE_ERASER);
      }
    });
    eraserSeekBar.setProgress(KSketchView.DEFAULT_ERASER_SIZE);
  }

  private void initStrokePop() {
    //画笔弹窗
    strokePopupWindow = new PopupWindow(activity);
    strokePopupWindow.setContentView(popupStrokeLayout);//设置主体布局
    strokePopupWindow.setWidth(ScreenUtils.dip2px(getActivity(), pupWindowsDPWidth));//宽度
    //        strokePopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);//高度自适应
    strokePopupWindow.setHeight(ScreenUtils.dip2px(getActivity(), strokePupWindowsDPHeight));//高度
    strokePopupWindow.setFocusable(true);
    strokePopupWindow.setBackgroundDrawable(new BitmapDrawable());//设置空白背景
    strokePopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//动画
    strokeTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
        int resId = R.drawable.stroke_type_rbtn_draw_checked;
        if (checkedId == R.id.stroke_type_rbtn_draw) {
          strokeType = STROKE_TYPE_DRAW;
        } else if (checkedId == R.id.stroke_type_rbtn_line) {
          strokeType = STROKE_TYPE_LINE;
          resId = R.drawable.stroke_type_rbtn_line_checked;
        } else if (checkedId == R.id.stroke_type_rbtn_circle) {
          strokeType = STROKE_TYPE_CIRCLE;
          resId = R.drawable.stroke_type_rbtn_circle_checked;
        } else if (checkedId == R.id.stroke_type_rbtn_rectangle) {
          strokeType = STROKE_TYPE_RECTANGLE;
          resId = R.drawable.stroke_type_rbtn_rectangle_checked;
        } else if (checkedId == R.id.stroke_type_rbtn_text) {
          strokeType = STROKE_TYPE_TEXT;
          resId = R.drawable.stroke_type_rbtn_text_checked;
        }
        btn_stroke.setImageResource(resId);
        mSketchView.setStrokeType(strokeType);
        strokePopupWindow.dismiss();//切换画笔后隐藏
      }
    });
    strokeColorRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
        int color = COLOR_BLACK;
        if (checkedId == R.id.stroke_color_black) {
          color = COLOR_BLACK;
        } else if (checkedId == R.id.stroke_color_red) {
          color = COLOR_RED;
        } else if (checkedId == R.id.stroke_color_green) {
          color = COLOR_GREEN;
        } else if (checkedId == R.id.stroke_color_orange) {
          color = COLOR_ORANGE;
        } else if (checkedId == R.id.stroke_color_blue) {
          color = COLOR_BLUE;
        }
        mSketchView.setStrokeColor(color);
      }
    });
    //画笔宽度拖动条
    strokeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onStopTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setSeekBarProgress(progress, STROKE_TYPE_DRAW);
      }
    });
    strokeSeekBar.setProgress(KSketchView.DEFAULT_STROKE_SIZE);
    //        strokeColorRG.check(R.id.stroke_color_black);

    //画笔不透明度拖动条
    strokeAlphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onStopTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int alpha = (progress * 255) / 100;//百分比转换成256级透明度
        mSketchView.setStrokeAlpha(alpha);
        strokeAlphaImage.setAlpha(alpha);
      }
    });
    strokeAlphaSeekBar.setProgress(KSketchView.DEFAULT_STROKE_ALPHA);
  }

  private void getSketchSize() {
    ViewTreeObserver vto = mSketchView.getViewTreeObserver();
    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      public boolean onPreDraw() {
        if (sketchViewHeight == 0 && sketchViewWidth == 0) {
          int height = mSketchView.getMeasuredHeight();
          int width = mSketchView.getMeasuredWidth();
          sketchViewHeight = height;
          sketchViewWidth = width;
          sketchViewRight = mSketchView.getRight();
          sketchViewBottom = mSketchView.getBottom();
          Log.i("onPreDraw", sketchViewHeight + "  " + sketchViewWidth);
          decorHeight = getActivity().getWindow().getDecorView().getMeasuredHeight();
          decorWidth = getActivity().getWindow().getDecorView().getMeasuredWidth();
          Log.i("onPreDraw", "decor height:" + decorHeight + "   width:" + decorHeight);
          int height3 = controlLayout.getMeasuredHeight();
          int width3 = controlLayout.getMeasuredWidth();
          Log.i("onPreDraw", "controlLayout  height:" + height3 + "   width:" + width3);
        }
        return true;
      }
    });
    Log.i("getSketchSize", sketchViewHeight + "  " + sketchViewWidth);
  }

  protected void setSeekBarProgress(int progress, int drawMode) {
    int calcProgress = progress > 1 ? progress : 1;
    int newSize = Math.round((size / 100f) * calcProgress);
    int offset = Math.round((size - newSize) / 2);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(newSize, newSize);
    lp.setMargins(offset, offset, offset, offset);
    if (drawMode == STROKE_TYPE_DRAW) {
      strokeImageView.setLayoutParams(lp);
    } else {
      eraserImageView.setLayoutParams(lp);
    }
    mSketchView.setSize(newSize, drawMode);
  }

  @Override public void onDrawChanged() {
    // Undo
    if (mSketchView.getStrokeRecordCount() > 0) {
      btn_undo.setAlpha(1f);
    } else {
      btn_undo.setAlpha(0.4f);
    }
    // Redo
    if (mSketchView.getRedoCount() > 0) {
      btn_redo.setAlpha(1f);
    } else {
      btn_redo.setAlpha(0.4f);
    }
  }

  private void updateGV() {
    sketchGVAdapter.notifyDataSetChanged();
  }

  @SuppressLint("WrongConstant")
  private void showTextPopupWindow(View anchor, final StrokeRecord record) {
    strokeET.requestFocus();
    textPopupWindow.showAsDropDown(anchor, record.textOffX,
        record.textOffY - mSketchView.getHeight());
    textPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    textPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override public void onDismiss() {
        if (!strokeET.getText().toString().equals("")) {
          record.text = strokeET.getText().toString();
          record.textPaint.setTextSize(strokeET.getTextSize());
          record.textWidth = strokeET.getMaxWidth();
          mSketchView.addStrokeRecord(record);
        }
      }
    });
  }

  private void saveInUI(final String imgName) {
    new saveToFileTask().execute(imgName);
  }

  /**
   * show 手动保存当前画板到文件，耗时操作
   *
   * @param filePath 保存的文件路径
   * @param imgName 保存的文件名
   * @return 返回保存后的文件路径
   * @author TangentLu
   * create at 16/6/21 下午3:46
   */
  public File saveInOI(String filePath, String imgName) {
    return saveInOI(filePath, imgName, 80);
  }

  /**
   * show 保存图片到本地文件，耗时操作
   *
   * @param filePath 文件保存路径
   * @param imgName 文件名
   * @param compress 压缩百分比1-100
   * @return 返回保存的图片文件
   * @author TangentLu
   * create at 16/6/17 上午11:18
   */
  public File saveInOI(String filePath, String imgName, int compress) {
    if (!imgName.contains(".png")) {
      imgName += ".png";
    }
    Log.e(TAG, "saveInOI: " + System.currentTimeMillis());
    Bitmap newBM = mSketchView.getResultBitmap();
    Log.e(TAG, "saveInOI: " + System.currentTimeMillis());

    try {
      File dir = new File(filePath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      File f = new File(filePath, imgName);
      if (!f.exists()) {
        f.createNewFile();
      } else {
        f.delete();
      }
      FileOutputStream out = new FileOutputStream(f);
      Log.e(TAG, "saveInOI: " + System.currentTimeMillis());

      if (compress >= 1 && compress <= 100) {
        newBM.compress(Bitmap.CompressFormat.PNG, compress, out);
      } else {
        newBM.compress(Bitmap.CompressFormat.PNG, 80, out);
      }
      Log.e(TAG, "saveInOI: " + System.currentTimeMillis());

      out.close();
      newBM.recycle();
      newBM = null;
      return f;
    } catch (Exception e) {
      return null;
    }
  }

  @Override public void onClick(View v) {

  }

  class saveToFileTask extends AsyncTask<String, Void, File> {
    @Override protected void onPreExecute() {
      super.onPreExecute();
      dialog = new AlertDialog.Builder(activity).setTitle("保存画板").setMessage("保存中...").show();
    }

    @Override protected File doInBackground(String... photoName) {
      return saveInOI(FILE_PATH, photoName[0]);
    }

    @Override protected void onPostExecute(File file) {
      super.onPostExecute(file);
      if (file.exists()) {
        Toast.makeText(getActivity(), file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT).show();
      }
      dialog.dismiss();
    }
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(
          context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction();
  }
}
