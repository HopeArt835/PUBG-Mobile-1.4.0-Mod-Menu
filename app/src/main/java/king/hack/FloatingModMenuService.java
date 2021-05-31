package king.hack;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import android.view.Gravity;
import android.graphics.PixelFormat;
import android.view.ViewConfiguration;
import android.widget.CheckBox;

import java.io.File;

import android.os.MemoryFile;

import java.io.IOException;

import android.os.FileUriExposedException;
import android.os.FileUtils;
import android.view.Surface;

import java.io.DataOutputStream;

public class FloatingModMenuService
        extends Service {
    public static String TITLE;
    int GLITCH_DELAY = 175;
    int GLITCH_LEN = 2;
    float density;
    int dpi;
    WindowManager.LayoutParams g_layoutParams;
    int height;
    RelativeLayout iconLayout;
    LinearLayout itemsLayout;
    RelativeLayout relativeLayout;
    ScrollView scrollView;
    TextView textTitle;
    int type;
    int width;
    WindowManager windowManager;

    public native void stringFromJNI();

    ESPView espLayout;
    static Context ctx;

    public static native void Size(int num);

    public static native void Size(int num, float size);

    int bones[] = {5, 4, 1, 12, 33, 13, 34, 14, 35, 53, 57, 54, 58, 55, 59};

    private native String Icon();

    private String assetFilename = "";
    private String assetSavePath = "";

    static {
        TITLE = "KING CHAET ";
    }

    public void _CopyFromAssets(final String _filen, final String _topathh, final String _sucm, final String _failm) {
        if (FileUtil.isDirectory(_topathh)) {
            FileUtil.writeFile("Dont remove", "its For storage Permission");
            assetFilename = _filen;
            assetSavePath = _topathh;
            try {
                int count;
                java.io.InputStream input = this.getAssets().open(assetFilename);
                java.io.OutputStream output = new java.io.FileOutputStream(assetSavePath + "/" + assetFilename);
                byte data[] = new byte[1024];
                while ((count = input.read(data)) > 0) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

                SketchwareUtil.showMessage(getApplicationContext(), _sucm);
            } catch (Exception e) {

                SketchwareUtil.showMessage(getApplicationContext(), _failm);
            }
        } else {

        }
    }

    private void addSeekbar(int max, int def, final SeekBar.OnSeekBarChangeListener listener) {
        SeekBar sb = new SeekBar(this);
        sb.setMax(max);
        sb.setProgress(def);
        sb.setLayoutParams(setParams());
        sb.setOnSeekBarChangeListener(listener);
        itemsLayout.addView(sb);
    }

    private LinearLayout.LayoutParams setParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        return params;
    }

    private TextView addText(String text) {
        TextView tv = new TextView(this);
        tv.setTypeface(null, 1);
        tv.setPadding(convertSizeToDp(5.0f), convertSizeToDp(5.0f), convertSizeToDp(5.0f), convertSizeToDp(5.0f));

        tv.setText(text);
        tv.setTextSize(1, 15.0f);
        tv.setTextColor(Color.argb(255, 230, 0, 0));
        tv.setLayoutParams(setParams());
        itemsLayout.addView(tv);
        return tv;
    }

    private void AddButton(String string, View.OnClickListener onClickListener) {
        Button button = new Button((Context) this);
        button.setText((CharSequence) string);
        button.setTextSize(1, 13f);
        button.setOnClickListener(onClickListener);
        button.setBackgroundColor(Color.parseColor("#3E2723"));
        button.setTextColor(-1);
        this.itemsLayout.addView((View) button);
    }

    private void AddTextDivide(String string) {
        TextView textView = new TextView((Context) this);
        textView.setText((CharSequence) string);
        textView.setTextSize(1, 15.0f);
        textView.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-2, -2));
        textView.setTypeface(null, 1);
        textView.setPadding(this.convertSizeToDp(5.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(5.0f));
        textView.setTextColor(Color.argb(255, 230, 0, 0));
        this.itemsLayout.addView((View) textView);
    }

    private void AddToggle(String string, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        Switch switch_ = new Switch((Context) this);
        switch_.setText((CharSequence) string);
        switch_.setPadding(this.convertSizeToDp(10.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(10.0f), this.convertSizeToDp(5.0f));
        switch_.setTextSize(1, 12.5f);
        switch_.setTextColor(-1);
        switch_.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            }
        });
        switch_.setOnCheckedChangeListener(onCheckedChangeListener);
        switch_.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-1, -2));
        this.itemsLayout.addView((View) switch_);
    }

    private void AddToggleDefTrue(String string, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        Switch switch_ = new Switch((Context) this);
        switch_.setText((CharSequence) string);
        switch_.setChecked(true);
        switch_.setPadding(this.convertSizeToDp(10.0f), this.convertSizeToDp(5.0f), this.convertSizeToDp(10.0f), this.convertSizeToDp(5.0f));
        switch_.setTextSize(1, 12.5f);
        switch_.setTextColor(-1);
        switch_.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            }
        });
        switch_.setOnCheckedChangeListener(onCheckedChangeListener);
        switch_.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-1, -2));
        this.itemsLayout.addView((View) switch_);
    }

    static native void Switch(int i, boolean k);

    static native void Switch2(int i);

    static native void TextSize(int var0);

    private int getLayoutType() {
        if (Build.VERSION.SDK_INT >= 26) {
            return 2038;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return 2002;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return 2005;
        }
        return 2003;
    }

    public static native void DrawOn(ESPView espView, Canvas canvas);

    public void CreateCanvas() {
        ESPView eSPView;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, this.type, 56, -3);
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.gravity = 51;
        this.espLayout = eSPView = new ESPView((Context) this);
        this.windowManager.addView((View) eSPView, (ViewGroup.LayoutParams) layoutParams);
    }

    public int convertSizeToDp(float f) {
        return Math.round((float) TypedValue.applyDimension(1, f, this.getResources().getDisplayMetrics()));
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        final RelativeLayout relativeLayout;
        TextView textView;
        ScrollView scrollView;
        super.onCreate();
        System.loadLibrary("FuCkYou");
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.relativeLayout = new RelativeLayout((Context) this);
        this.type = this.getLayoutType();
        Display display = this.windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        this.width = point.x;
        this.height = point.y;
        this.dpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        this.density = Resources.getSystem().getDisplayMetrics().density;
        this.itemsLayout = new LinearLayout((Context) this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        this.itemsLayout.setLayoutParams((ViewGroup.LayoutParams) layoutParams);
        this.relativeLayout.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-1, -1));
        this.scrollView = scrollView = new ScrollView((Context) this);
        scrollView.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-1, -2));
        this.scrollView.setPadding(0, (int) ((float) this.dpi / 5.5f), 0, 0);
        int n = this.convertSizeToDp(250.0f);
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(n, this.convertSizeToDp(300.0f), this.type, 8, -3);
        this.relativeLayout.setBackgroundColor(Color.argb(255, 13, 13, 13));
        layoutParams2.x = 0;
        layoutParams2.y = 0;
        layoutParams2.gravity = 51;
        this.g_layoutParams = layoutParams2;
        FrameLayout frameLayout = new FrameLayout((Context) this);
        frameLayout.setClickable(true);
        frameLayout.setFocusable(true);
        frameLayout.setFocusableInTouchMode(true);
        frameLayout.setBackgroundColor(Color.argb(255, 13, 13, 13));
        frameLayout.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(n, (int) ((float) this.dpi / 4f)));
        Button button = new Button((Context) this);
        button.setText(" X");
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setTextSize(16.0f);
        button.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
        button.setGravity(Gravity.VERTICAL_GRAVITY_MASK);
        button.setBackgroundColor(Color.parseColor("#000000"));
        button.setX((float) (n - (int) ((float) this.dpi / 3.2f)));
        frameLayout.addView((View) button);
        this.textTitle = textView = new TextView((Context) this);
        textView.setText((CharSequence) TITLE);
        this.textTitle.setGravity(19);
        this.textTitle.setTextColor(-1);
        this.textTitle.setTypeface(null, 1);
        this.textTitle.setPadding(this.convertSizeToDp(8.0f), 0, 0, 0);
        this.textTitle.setTextSize(1, 15.0f);
        frameLayout.addView((View) this.textTitle);
        this.relativeLayout.addView((View) this.scrollView);
        this.relativeLayout.addView((View) frameLayout);
        this.relativeLayout.setAlpha(0.7f);
        this.itemsLayout.setOrientation(1);
        this.scrollView.addView((View) this.itemsLayout);
        this.iconLayout = relativeLayout = new RelativeLayout((Context) this);
        relativeLayout.setLayoutParams((ViewGroup.LayoutParams) new RelativeLayout.LayoutParams(-2, -2));
        ImageView imageView = new ImageView((Context) this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(this.convertSizeToDp(55.0f), this.convertSizeToDp(55.0f)));
        relativeLayout.addView((View) imageView);
        byte[] arrby = Base64.decode(Icon(), 0);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(arrby, 0, arrby.length));
        final WindowManager.LayoutParams layoutParams4 = new WindowManager.LayoutParams(-2, -2, this.type, 8, -3);
        layoutParams4.gravity = 51;
        layoutParams4.x = 0;
        layoutParams4.y = 0;
        this.windowManager.addView((View) relativeLayout, (ViewGroup.LayoutParams) layoutParams4);
        this.windowManager.addView((View) this.relativeLayout, (ViewGroup.LayoutParams) layoutParams2);
        this.relativeLayout.setVisibility(8);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            float deltaX;
            float deltaY;
            float maxX;
            float maxY;
            float newX;
            float newY;
            float pressedX;
            float pressedY;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int n = motionEvent.getActionMasked();
                if (n == 0) {
                    this.deltaX = (float) FloatingModMenuService.this.g_layoutParams.x - motionEvent.getRawX();
                    this.deltaY = (float) FloatingModMenuService.this.g_layoutParams.y - motionEvent.getRawY();
                    this.pressedX = motionEvent.getRawX();
                    this.pressedY = motionEvent.getRawY();
                    FloatingModMenuService.this.scrollView.requestDisallowInterceptTouchEvent(true);
                    return false;
                }
                if (n == 1) {
                    float f;
                    float f2;
                    float f3;
                    float f4;
                    this.maxX = FloatingModMenuService.this.width - FloatingModMenuService.this.relativeLayout.getWidth();
                    this.maxY = FloatingModMenuService.this.height - FloatingModMenuService.this.relativeLayout.getHeight();
                    if (this.newX < 0.0f) {
                        this.newX = 0.0f;
                    }
                    if ((f = this.newX) > (f3 = this.maxX)) {
                        this.newX = (int) f3;
                    }
                    if (this.newY < 0.0f) {
                        this.newY = 0.0f;
                    }
                    if ((f4 = this.newY) > (f2 = this.maxY)) {
                        this.newY = (int) f2;
                    }
                    FloatingModMenuService.this.g_layoutParams.x = (int) this.newX;
                    FloatingModMenuService.this.g_layoutParams.y = (int) this.newY;
                    FloatingModMenuService.this.windowManager.updateViewLayout((View) FloatingModMenuService.this.relativeLayout, (ViewGroup.LayoutParams) FloatingModMenuService.this.g_layoutParams);
                    FloatingModMenuService.this.relativeLayout.setAlpha(0.7f);
                    FloatingModMenuService.this.scrollView.requestDisallowInterceptTouchEvent(false);
                    return true;
                }
                if (n == 2) {
                    float f;
                    float f5;
                    this.newX = motionEvent.getRawX() + this.deltaX;
                    this.newY = motionEvent.getRawY() + this.deltaY;
                    this.maxX = FloatingModMenuService.this.width - FloatingModMenuService.this.relativeLayout.getWidth();
                    this.maxY = f5 = (float) (FloatingModMenuService.this.height - FloatingModMenuService.this.relativeLayout.getHeight());
                    float f6 = this.newX;
                    if (f6 >= 0.0f && f6 <= this.maxX && (f = this.newY) >= 0.0f && f <= f5) {
                        FloatingModMenuService.this.relativeLayout.setAlpha(0.7f);
                        FloatingModMenuService.this.g_layoutParams.x = (int) this.newX;
                        FloatingModMenuService.this.g_layoutParams.y = (int) this.newY;
                        FloatingModMenuService.this.windowManager.updateViewLayout((View) FloatingModMenuService.this.relativeLayout, (ViewGroup.LayoutParams) FloatingModMenuService.this.g_layoutParams);
                    }
                    FloatingModMenuService.this.relativeLayout.setAlpha(0.5f);
                    FloatingModMenuService.this.g_layoutParams.x = (int) this.newX;
                    FloatingModMenuService.this.g_layoutParams.y = (int) this.newY;
                    FloatingModMenuService.this.windowManager.updateViewLayout((View) FloatingModMenuService.this.relativeLayout, (ViewGroup.LayoutParams) FloatingModMenuService.this.g_layoutParams);
                }
                return false;
            }
        });
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            float deltaX;
            float deltaY;
            float newX;
            float newY;
            float pressedX;
            float pressedY;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int n = motionEvent.getActionMasked();
                if (n == 0) {
                    this.deltaX = (float) layoutParams4.x - motionEvent.getRawX();
                    this.deltaY = (float) layoutParams4.y - motionEvent.getRawY();
                    this.pressedX = motionEvent.getRawX();
                    this.pressedY = motionEvent.getRawY();
                    return false;
                }
                if (n == 1) {
                    int n2 = (int) (motionEvent.getRawX() - this.pressedX);
                    int n3 = (int) (motionEvent.getRawY() - this.pressedY);
                    if (n2 == 0 && n3 == 0) {
                        FloatingModMenuService.this.relativeLayout.setVisibility(0);
                        relativeLayout.setVisibility(8);
                    }
                    return true;
                }
                if (n == 2) {
                    this.newX = motionEvent.getRawX() + this.deltaX;
                    this.newY = motionEvent.getRawY() + this.deltaY;
                    float f = FloatingModMenuService.this.width - view.getWidth();
                    float f2 = FloatingModMenuService.this.height - view.getHeight();
                    if (this.newX < 0.0f) {
                        this.newX = 0.0f;
                    }
                    if (this.newX > f) {
                        this.newX = (int) f;
                    }
                    if (this.newY < 0.0f) {
                        this.newY = 0.0f;
                    }
                    if (this.newY > f2) {
                        this.newY = (int) f2;
                    }
                    layoutParams4.x = (int) this.newX;
                    layoutParams4.y = (int) this.newY;
                    FloatingModMenuService.this.windowManager.updateViewLayout((View) relativeLayout, (ViewGroup.LayoutParams) layoutParams4);
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                FloatingModMenuService.this.relativeLayout.setVisibility(8);
                relativeLayout.setVisibility(0);
            }
        });
        CreateCanvas();
        this.AddTextDivide("ᴀɴᴛɪ-ᴄʜᴇᴀᴛ ᴍᴇɴᴜ");
        Button button5 = new Button((Context) this);
        button5.setText("ANTI CHEAT (LOBBY)");
        button5.setTextColor(Color.parseColor("#ffffff"));
        button5.setTextSize(16.0f);
        button5.setBackgroundColor(Color.parseColor("#000000"));
        itemsLayout.addView((View) button5);
        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AlertDialog.Builder builde = new AlertDialog.Builder((Context) FloatingModMenuService.this, 5);
                builde.setMessage("Active It In Lobby Only Eech Time");
                builde.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int n) {
//Non-Root Tracks
                        FileUtil.deleteFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/LightData");
                        FileUtil.deleteFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/MMKV");
                        FileUtil.deleteFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Pandora");
                        FileUtil.deleteFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/TableDatas");
                        FileUtil.deleteFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Logs");
                        FileUtil.deleteFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Intermediate");
//Rewrite In Files Non-Root
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/StatEventReportedFlag", "StatEventType::APP_LAUNCH=false\nStatEventType::LOADING_COMPLETED=false\n");
//Make New Files Non-Root
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Logs", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/LightData", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Pandora", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferEifs0", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferEifs1", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/PufferTmpDir", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/TableDatas", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/TGPA", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/cache", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
                        FileUtil.writeFile("/storage/emulated/0/Android/data/com.tencent.ig/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/RoleInfo", "TELEGRAM CHANNEL: https://t.me/BM_MOD_HACK");
//Block Ports
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I INPUT -s 49.51.235.24 -j DROP &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I OUTPUT -s 49.51.235.24 -j DROP &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I INPUT -s 49.51.235.24 -j REJECT &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I OUTPUT -s 49.51.235.24 -j REJECT &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I INPUT -s 49.51.0.0 -j DROP &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I OUTPUT -s 49.51.0.0 -j DROP &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I INPUT -s 49.51.0.0 -j REJECT &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I OUTPUT -s 49.51.0.0 -j REJECT &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I INPUT -s 49.51.255.255 -j DROP &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I OUTPUT -s 49.51.255.255 -j DROP &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I INPUT -s 49.51.255.255 -j REJECT &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(Runtime.getRuntime().exec("sh").getOutputStream());
                            dataOutputStream.writeBytes("iptables -I OUTPUT -s 49.51.255.255 -j REJECT &>/dev/null \n");
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog alertDialo = builde.create();
                if (Build.VERSION.SDK_INT >= 26) {
                    Window window = alertDialo.getWindow();
                    if (window != null) {
                        window.setType(2038);
                    }
                }
                alertDialo.show();
            }
        });
        AddToggle("ANTI CRASH (LOBBY)", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(145, bl);
                final String Box = "ANTI CRASH";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("ESP MENU :-");
        AddToggleDefTrue("ACTIVE ESP", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                ESPView eSPView = espLayout;
                int n = bl ? 0 : 4;
                eSPView.setVisibility(n);
            }
        });
        AddToggle("ESP BOX", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(1, bl);
                final String Box = "ESP BOX";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("ESP LINE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(2, bl);
                final String Box = "ESP LINE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("ESP DISTANCE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(3, bl);
                final String Box = "ESP DISTANCE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("ESP HEALTH", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(4, bl);
                final String Box = "ESP HEALTH";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("PLAYER NAME", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(5, bl);
                final String Box = "ESP NAME";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("ESP HEAD", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(6, bl);
                final String Box = "ESP HEAD";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("WARNING 360°", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(7, bl);
                final String Box = "WARNING 360°";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SKELETON", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(8, bl);
                final String Box = "ESP SKELETON";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("GRENADE WARNING", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(9, bl);
                final String Box = "GRENADE WARNING";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("ENEMY COUNT", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(14, bl);
                final String Box = "ENEMY COUNT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("DRAW CIRCLE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(147, bl);
                final String Box = "DRAW CIRCLE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddToggle("AIM FOV", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(150, bl);
                final String Box = "AIM FOV";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("Vehicles :-");
        this.AddToggle("BUGGY", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(15, bl);
                final String Box = "BUGGY";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("UAZ", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(16, bl);
                final String Box = "UAZ";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("TRIKE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(17, bl);
                final String Box = "TRIKE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BIKE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(18, bl);
                final String Box = "BIKE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("DACIA", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(19, bl);
                final String Box = "DACIA";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("JET", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(20, bl);
                final String Box = "JET";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BOAT", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(21, bl);
                final String Box = "BOAT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BUS", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(22, bl);
                final String Box = "BUS";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("MIRADO", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(23, bl);
                final String Box = "MIRADO";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SCOOTER", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(24, bl);
                final String Box = "SCOOTER";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("RONY", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(25, bl);
                final String Box = "RONY";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SNOWBIKE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(26, bl);
                final String Box = "SNOWBIKE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SNOWMOBILE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(27, bl);
                final String Box = "SNOWMOBILE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("TEMPO", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(28, bl);
                final String Box = "TEMPO";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("TRUCK", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(29, bl);
                final String Box = "TRUCK";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BRDM", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(30, bl);
                final String Box = "BRDM";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("LADANIVA", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(31, bl);
                final String Box = "LADANIVA";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("MONSTERTRUCK", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(32, bl);
                final String Box = "MONSTERTRUCK";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("Scopes :-");
        this.AddToggle("Red Dot", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(148, bl);
                final String Box = "RED DOT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("2x", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(200, bl);
                final String Box = "SCOOP 2X";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("3x", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(149, bl);
                final String Box = "SCOOP 3X";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("4x", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(38, bl);
                final String Box = "SCOOP 4X";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("6x", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(39, bl);
                final String Box = "SCOOP 6X";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("8x", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(40, bl);
                final String Box = "SCOOP 8X";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("AR:-");
        this.AddToggle("AUG", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(41, bl);
                final String Box = "AUG";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("M762", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(42, bl);
                final String Box = "M762";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("Scar-L", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(43, bl);
                final String Box = "SCAR-L";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("M416", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(44, bl);
                final String Box = "M416";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("M164-A", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(45, bl);
                final String Box = "M164-A";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("MK47 MUTANT", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(46, bl);
                final String Box = "MK47 MUTANT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("G36C", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(47, bl);
                final String Box = "G36C";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("QBZ", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(48, bl);
                final String Box = "QBZ";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("AKM", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(49, bl);
                final String Box = "AKM";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("Groza", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(50, bl);
                final String Box = "GROZA";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("DP28", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(57, bl);
                final String Box = "DP28";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("M249", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(58, bl);
                final String Box = "M249";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("KAR98K", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(203, bl);
                final String Box = "KAR98K";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("UMP45", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(204, bl);
                final String Box = "UMP45";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("AWM", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(59, bl);
                final String Box = "AWM";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("THOMPSON", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(202, bl);
                final String Box = "THOMPASON";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddTextDivide("OTHER :- ");
        this.AddToggle("PAN", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(201, bl);
                final String Box = "PAN";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("Ammo :-");
        this.AddToggle("7.62", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(86, bl);
                final String Box = "AMMO 7.62";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("5.56", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(88, bl);
                final String Box = "AMMO 5.56";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("Bag Helmet Vest :-");
        this.AddToggle("BAG LVL 1", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(93, bl);
                final String Box = "BAG LVL 1";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BAG LVL 2", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(94, bl);
                final String Box = "BAG LVL 2";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BAG LVL 3", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(95, bl);
                final String Box = "BAG LVL 3";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("HELMET LVL 1", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(96, bl);
                final String Box = "HELMET LVL 1";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("HELMET LVL 2", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(97, bl);
                final String Box = "HELMET LVL 2";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("HELMET LVL 3", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(98, bl);
                final String Box = "HELMET LVL 3";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("VEST LVL 1", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(99, bl);
                final String Box = "VEST LVL 1";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("VEST LVL 2", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(100, bl);
                final String Box = "VEST LVL 2";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("VEST LVL 3", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(101, bl);
                final String Box = "VEST LVL 3";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("Health Kits :-");
        this.AddToggle("BIG NEDKIT", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(105, bl);
                final String Box = "BIG MEDKIT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("MEDKIT", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(107, bl);
                final String Box = "MEDKIT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("THROWABLES :-");
        this.AddToggle("GRENADE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(109, bl);
                final String Box = "GRENADE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SMOKE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(110, bl);
                final String Box = "SMOKE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("MOLOTOVE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(111, bl);
                final String Box = "MOLOTOVE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddTextDivide("SPECIAL ITEMS :-");
        this.AddToggle("FLARE GUN", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(130, bl);
                final String Box = "FLARE GUN";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("AIR DROP PLANE", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(134, bl);
                final String Box = "AIR DROP PLANE";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("AIR DROP", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(135, bl);
                final String Box = "AIR DROP";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("MAGIC MAGIC", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(136, bl);
                final String Box = "MAGIC BULLET";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("NO RECOIL", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(137, bl);
                final String Box = "NO RECOIL";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SMALL CROSSHAIR", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(139, bl);
                final String Box = "SMALL CROSSHAIR";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("INSTANT HIT", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(140, bl);
                final String Box = "INSTANT HIT";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        addSeekbar(5, 0, new SeekBar.OnSeekBarChangeListener() {
            TextView sizetext = addText("HIGHT VIEW: 0");

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int pindex = (progress + 0);
                float psize = (progress + 4.0f);
                String tsize = "HIGHT VIEW: " + pindex;
                sizetext.setText(tsize);
                Size(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.AddToggle("NO FOG", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(142, bl);
                final String Box = "NO FOG";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("SPEED PLAYER", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(144, bl);
                final String Box = "SPEED PLAYER";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        this.AddToggle("BLACK BODY", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                Switch(143, bl);
                final String Box = "WHITE PLAYER";
                SketchwareUtil.showMessage(getApplicationContext(), Box);
            }
        });
        AddButton("EXIT OF MENU", new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FloatingModMenuService.this, 5);
                builder.setMessage("Are you sure, You want close this menu?");
                builder.setPositiveButton("Yas", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int n) {
                        stopSelf();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int n) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                if (Build.VERSION.SDK_INT >= 26) {
                    Window window = alertDialog.getWindow();
                    if (window != null) {
                        window.setType(2038);
                    }
                }
                //To fix crash on older Android versions (Unable to add window -- token null is not for an application)
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                else
                    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();
            }

        });
    }

    public void onDestroy() {
        this.windowManager.removeViewImmediate((View) this.espLayout);
        this.windowManager.removeViewImmediate((View) this.iconLayout);
        this.windowManager.removeViewImmediate((View) this.relativeLayout);
        super.onDestroy();
    }

    public static interface OnListChoosedListener {
        public void onChoosed(int var1);
    }

}



