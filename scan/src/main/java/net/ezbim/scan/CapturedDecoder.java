package net.ezbim.scan;

import android.os.Handler;
import android.view.View;

/**
 * Created by hdk on 2016/11/29.
 */

public interface CapturedDecoder {
    void handleDecode(String result);

    View getScanCropView();

    View getScanContainer();

    boolean isNeedCapture();

    int getCropWidth();

    int getCropHeight();

    Handler getHandler();

    int getStatusBarHeight();

    int getX();

    int getY();
}
