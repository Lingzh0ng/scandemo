package net.ezbim.scan;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

/**
 * 描述: 接受消息后解码
 */
final class DecodeHandler extends Handler {

  private WeakReference<CapturedDecoder> decoder = null;
  private Rect mCropRect = null;

  DecodeHandler(CapturedDecoder activity) {
    decoder = new WeakReference<CapturedDecoder>(activity);
  }

  @Override public void handleMessage(Message message) {
    switch (message.what) {
      //ZBAR_DECODE
      case 5:
        initCrop(message.arg1, message.arg2);
        if (mCropRect != null) decode((byte[]) message.obj, message.arg1, message.arg2, mCropRect);
        break;
      //ZBAR_QUIT
      case 6:
        Looper.myLooper().quit();
        break;
    }
  }

  private void initCrop(int cameraWidth, int cameraHeight) {
    CapturedDecoder capturedDecoder = decoder.get();

    if (capturedDecoder != null) {
      /** 获取布局中扫描框的位置信息 */
      int[] location = new int[2];
      //        RelativeLayout scanCropView = (RelativeLayout) activity.findViewById(R.id.capture_crop_layout);
      //        RelativeLayout scanContainer = (RelativeLayout) activity.findViewById(R.id.capture_containter);

      View scanCropView = capturedDecoder.getScanCropView();
      View scanContainer = capturedDecoder.getScanContainer();
      scanCropView.getLocationInWindow(location);

      int cropLeft = location[0];
      int cropTop = location[1] - capturedDecoder.getStatusBarHeight();

      int cropWidth = scanCropView.getWidth();
      int cropHeight = scanCropView.getHeight();

      /** 获取布局容器的宽高 */
      int containerWidth = scanContainer.getWidth();
      int containerHeight = scanContainer.getHeight();

      /** 计算最终截取的矩形的左上角顶点x坐标 */
      //        int x = cropLeft * cameraWidth / containerWidth;
      int x = cropLeft;
      /** 计算最终截取的矩形的左上角顶点y坐标 */
      //        int y = cropTop * cameraHeight / containerHeight;
      int y = cropLeft;

      /** 计算最终截取的矩形的宽度 */
      //        int width = cropWidth * cameraWidth / containerWidth;
      int width = cropWidth;
      /** 计算最终截取的矩形的高度 */
      //        int height = cropHeight * cameraHeight / containerHeight;
      int height = cropHeight;

      /** 生成最终的截取的矩形 */
      //        mCropRect = new Rect(x, y, width + x, height + y);
      mCropRect = new Rect(x, y, width + x, height + y);
    } else {
      mCropRect = new Rect(0, 0, 0, 0);
    }
  }

  private void decode(byte[] data, int width, int height, Rect rect) {
    byte[] rotatedData = new byte[data.length];
    //for (int y = 0; y < height; y++) {
    //  for (int x = 0; x < width; x++)
    //    rotatedData[x * height + height - y - 1] = data[x + y * width];
    //}
    //int tmp = width;// Here we are swapping, that's the difference to #11
    //width = height;
    //height = tmp;
    ImageScanner mImageScanner = new ImageScanner();
    mImageScanner.setConfig(0, Config.X_DENSITY, 3);
    mImageScanner.setConfig(0, Config.Y_DENSITY, 3);
    Camera camera = CameraManager.get().getCamera();
    Image barcode = null;
    if (camera != null) {
      Camera.Size size = camera.getParameters().getPreviewSize();
      barcode = new Image(size.width, size.height, "Y800");
    } else {
      barcode = new Image(width, height, "Y800");
    }
    barcode.setData(data);
    barcode.setCrop(rect.left, rect.top, rect.width(), rect.height());

    int result = mImageScanner.scanImage(barcode);
    String resultStr = null;

    if (result != 0) {
      SymbolSet syms = mImageScanner.getResults();
      for (Symbol sym : syms) {
        System.out.println("sym type : " + sym.getType() + " = " + sym.getData());
        if (sym.getType() == Symbol.QRCODE
            || sym.getType() == Symbol.EAN8
            || sym.getType() == Symbol.EAN13
            || sym.getType() == Symbol.CODABAR
            || sym.getType() == Symbol.CODE39
            || sym.getType() == Symbol.CODE93
            || sym.getType() == Symbol.CODE128
            || sym.getType() == Symbol.I25
            || sym.getType() == Symbol.UPCA
            || sym.getType() == Symbol.ISBN10
            || sym.getType() == Symbol.ISBN13
            || sym.getType() == Symbol.UPCE
            || sym.getType() == Symbol.PDF417) {
          resultStr = sym.getData();
          System.out.println(resultStr);
        }
      }
    }
    //ZBarDecoder manager = new ZBarDecoder();
    //String result = manager.decodeCrop(rotatedData, width, height, rect.left, rect.top, rect.width(), rect.height());
    CapturedDecoder capturedDecoder = decoder.get();
    if (capturedDecoder != null) {
      if (resultStr != null) {
        if (capturedDecoder.isNeedCapture()) {
          // 生成bitmap
          PlanarYUVLuminanceSource source =
              new PlanarYUVLuminanceSource(rotatedData, width, height, capturedDecoder.getX(),
                  capturedDecoder.getY(), capturedDecoder.getCropWidth(),
                  capturedDecoder.getCropHeight(), false);
          int[] pixels = source.renderThumbnail();
          int w = source.getThumbnailWidth();
          int h = source.getThumbnailHeight();
          Bitmap bitmap = Bitmap.createBitmap(pixels, 0, w, w, h, Bitmap.Config.ARGB_8888);
          try {
            String rootPath =
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/Qrcode/";
            File root = new File(rootPath);
            if (!root.exists()) {
              root.mkdirs();
            }
            File f = new File(rootPath + "Qrcode.jpg");
            if (f.exists()) {
              f.delete();
            }
            f.createNewFile();

            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        if (null != capturedDecoder.getHandler()) {
          boolean b = resultStr.contains("#") && resultStr.contains("*");
          Message msg = new Message();
          if (b) {
            msg.obj = resultStr;
            msg.what = ZBarState.ZBAR_DECODE_SUCCEEDED.value();
          } else {
            //不是规范的二维码
            System.out.println("不是规范的二维码");
            msg.what = ZBarState.ZBAR_RESTART_PREVIEW.value();
          }
          capturedDecoder.getHandler().sendMessage(msg);
        }
      } else {
        if (null != capturedDecoder.getHandler()) {
          capturedDecoder.getHandler().sendEmptyMessage(ZBarState.ZBAR_DECODE_FAILED.value());
        }
      }
    }
  }
}
