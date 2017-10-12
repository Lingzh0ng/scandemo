package net.ezbim.scan;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;


/**
 * 描述: 扫描消息转发
 */
public final class CapturedDecoderHandler extends Handler {

    DecodeThread decodeThread = null;
    WeakReference<CapturedDecoder> weakReferencedecoder = null;
    private State state;

    private enum State {
        PREVIEW, SUCCESS, DONE
    }

    public CapturedDecoderHandler(CapturedDecoder decoder) {
        this.weakReferencedecoder = new WeakReference<CapturedDecoder>(decoder);
        decodeThread = new DecodeThread(decoder);
        decodeThread.start();
        state = State.SUCCESS;
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            //ZBAR_AUTO_FOCUS
            case 1:
                if (state == State.PREVIEW) {
                    CameraManager.get().requestAutoFocus(this, ZBarState.ZBAR_AUTO_FOCUS.value());
                }
                break;
            //ZBAR_RESTART_PREVIEW
            case 2:
                restartPreviewAndDecode();
                break;
            //ZBAR_DECODE_SUCCEEDED
            case 3:
                state = State.SUCCESS;
                if(weakReferencedecoder!=null) {
                    CapturedDecoder  decoder = weakReferencedecoder.get();
                    if(decoder!=null) {
                        decoder.handleDecode((String) message.obj);// 解析成功，回调
                    }
                }
                break;
            //ZBAR_DECODE_FAILED
            case 4:
                state = State.PREVIEW;
                CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), ZBarState.ZBAR_DECODE.value());
                break;
        }

    }

    public void quitSynchronously() {
        state = State.DONE;
        CameraManager.get().stopPreview();
        removeMessages(ZBarState.ZBAR_DECODE_SUCCEEDED.value());
        removeMessages(ZBarState.ZBAR_DECODE_FAILED.value());
        removeMessages(ZBarState.ZBAR_DECODE.value());
        removeMessages(ZBarState.ZBAR_AUTO_FOCUS.value());
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), ZBarState.ZBAR_DECODE.value());
            CameraManager.get().requestAutoFocus(this, ZBarState.ZBAR_AUTO_FOCUS.value());
        }
    }

}
