package net.ezbim.scan.simple;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import net.ezbim.scan.CameraManager;
import net.ezbim.scan.CapturedDecoder;
import net.ezbim.scan.CapturedDecoderHandler;
import net.ezbim.scan.InactivityTimer;
import net.ezbim.scan.R;

/**
 * Description:
 * Created by xk on 16/12/20.13.56
 */

public class SimpleScanActivity extends AppCompatActivity
    implements SurfaceHolder.Callback, CapturedDecoder, View.OnClickListener {

  public static final int SIMPLE_SCAN_REQUEST = 2000;
  public static final String SIMPLE_SCAN_RESULT = "SIMPLE_SCAN_RESULT";
  private static final float BEEP_VOLUME = 0.50f;
  private static final long VIBRATE_DURATION = 200L;
  private SurfaceView capturePreview;
  private ImageView captureScanLine;
  private RelativeLayout captureCropLayout;
  private RelativeLayout captureContainter;
  private boolean hasSurface;
  private boolean playBeep;
  private boolean vibrate;
  private boolean isNeedCapture = false;
  private int x = 0;
  private int y = 0;
  private int cropWidth = 0;
  private int cropHeight = 0;
  private boolean lightFlag = false;
  private InactivityTimer inactivityTimer;
  private MediaPlayer mediaPlayer;
  private CapturedDecoderHandler handler;
  private ArrayList<String> scanList;
  private Button bntBack;
  private Button bntLight;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    setContentView(R.layout.activity_simple_scan);
    //getSupportActionBar().setTitle("扫一扫");
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //getSupportActionBar().setDisplayUseLogoEnabled(false);
    bntBack = (Button) findViewById(R.id.bntBack);
    bntLight = (Button) findViewById(R.id.bntLight);
    capturePreview = (SurfaceView) findViewById(R.id.capture_preview);
    captureScanLine = (ImageView) findViewById(R.id.capture_scan_line);
    captureCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);
    captureContainter = (RelativeLayout) findViewById(R.id.capture_containter);

    bntBack.setOnClickListener(this);
    bntLight.setOnClickListener(this);
    CameraManager.init(getApplication());
    hasSurface = false;
    inactivityTimer = new InactivityTimer(this);
    initQRLine();
  }

  private void initQRLine() {
    TranslateAnimation mAnimation =
        new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT,
            0.95f);
    mAnimation.setDuration(1500);
    mAnimation.setRepeatCount(-1);
    mAnimation.setRepeatMode(Animation.REVERSE);
    mAnimation.setInterpolator(new LinearInterpolator());
    captureScanLine.setAnimation(mAnimation);
  }

  @Override protected void onResume() {
    super.onResume();
    SurfaceHolder surfaceHolder = capturePreview.getHolder();
    if (hasSurface) {
      initCamera(surfaceHolder);
    } else {
      surfaceHolder.addCallback(this);
      surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    playBeep = true;
    AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
    if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
      playBeep = false;
    }
    initBeepSound();
    vibrate = true;
  }

  @Override protected void onPause() {
    super.onPause();
    if (handler != null) {
      handler.quitSynchronously();
      handler = null;
    }
    CameraManager.get().closeDriver();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.aty_qr_code_scan, menu);
    if (lightFlag) {
      menu.getItem(0).setVisible(false);
      menu.getItem(1).setVisible(true);
    } else {
      menu.getItem(1).setVisible(false);
      menu.getItem(0).setVisible(true);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    } else if (item.getItemId() == R.id.menu_off_light) {
      closeLight();
      invalidateOptionsMenu();
    } else if (item.getItemId() == R.id.menu_open_light) {
      openLight();
      invalidateOptionsMenu();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    handler = null;
    inactivityTimer.shutdown();
    super.onDestroy();
  }

  private void initCamera(SurfaceHolder surfaceHolder) {
    try {
      CameraManager.get().openDriver(surfaceHolder);

      Point point = CameraManager.get().getCameraResolution();
      int width = point.y;
      int height = point.x;

      int x = captureCropLayout.getLeft() * width / captureContainter.getWidth();
      int y = captureCropLayout.getTop() * height / captureContainter.getHeight();

      int cropWidth = captureCropLayout.getWidth() * width / captureContainter.getWidth();
      int cropHeight = captureCropLayout.getHeight() * height / captureContainter.getHeight();

      this.x = x;
      this.y = y;
      this.cropWidth = cropWidth;
      this.cropHeight = cropHeight;
      // 设置是否需要截图
      this.isNeedCapture = false;
    } catch (IOException ioe) {
      return;
    } catch (RuntimeException e) {
      return;
    }
    if (handler == null) {
      handler = new CapturedDecoderHandler(SimpleScanActivity.this);
    }
  }

  private void initBeepSound() {
    if (playBeep && mediaPlayer == null) {
      setVolumeControlStream(AudioManager.STREAM_MUSIC);
      mediaPlayer = new MediaPlayer();
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      mediaPlayer.setOnCompletionListener(beepListener);

      AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
      try {
        mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
            file.getLength());
        file.close();
        mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
        mediaPlayer.prepare();
      } catch (IOException e) {
        mediaPlayer = null;
      }
    }
  }

  private void playBeepSoundAndVibrate() {
    if (playBeep && mediaPlayer != null) {
      mediaPlayer.start();
    }
    if (vibrate) {
      Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
      vibrator.vibrate(VIBRATE_DURATION);
    }
  }

  private final MediaPlayer.OnCompletionListener beepListener =
      new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
          mediaPlayer.seekTo(0);
        }
      };

  private void openLight() {
    CameraManager.get().openLight();
    lightFlag = true;
    bntLight.setText("close");
  }

  private void closeLight() {
    CameraManager.get().offLight();
    lightFlag = false;
    bntLight.setText("open");
  }

  private void recoverLight() {
    if (lightFlag) {
      CameraManager.get().openLight();
    } else {
      CameraManager.get().offLight();
    }
  }

  @Override public void surfaceCreated(SurfaceHolder holder) {
    if (!hasSurface) {
      hasSurface = true;
      initCamera(holder);
    }
  }

  @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override public void surfaceDestroyed(SurfaceHolder holder) {
    hasSurface = false;
  }

  @Override public void handleDecode(String result) {
    inactivityTimer.onActivity();
    playBeepSoundAndVibrate();
    if (!TextUtils.isEmpty(result)) {
      Intent data = new Intent();
      data.putExtra(SIMPLE_SCAN_RESULT, result);
      setResult(RESULT_OK, data);
      finish();
    } else {
      Toast.makeText(this, "该二维码无法解析", Toast.LENGTH_SHORT).show();
    }
    //        if (scanList.contains(result)) {
    //            showToastMessage(R.string.scan_has_bean);
    //        } else {
    //            scanList.add(result);
    //            tvCount.setText(getResources().getString(R.string.scan_count,scanList.size()));
    //        }
    // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描，这里设置1秒延时
    //        handler.sendEmptyMessageDelayed(ZBarState.ZBAR_RESTART_PREVIEW.value(), 1000);
  }

  @Override public View getScanCropView() {
    return captureCropLayout;
  }

  @Override public View getScanContainer() {
    return captureContainter;
  }

  @Override public boolean isNeedCapture() {
    return isNeedCapture;
  }

  @Override public int getCropWidth() {
    return cropWidth;
  }

  @Override public int getCropHeight() {
    return cropHeight;
  }

  @Override public Handler getHandler() {
    return handler;
  }

  @Override public int getStatusBarHeight() {
    return 0;
  }

  @Override public int getX() {
    return x;
  }

  @Override public int getY() {
    return y;
  }

  @Override public void onClick(View v) {
    if (v == bntBack) {
      finish();
    } else if (v == bntLight) {
      if (lightFlag) {
        closeLight();
      } else {
        openLight();
      }
    }
  }
}
