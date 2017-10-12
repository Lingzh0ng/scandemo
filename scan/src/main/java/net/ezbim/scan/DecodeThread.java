package net.ezbim.scan;


import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

/**
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

	CapturedDecoder decoder;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(CapturedDecoder decoder) {
		this.decoder = decoder;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(decoder);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
