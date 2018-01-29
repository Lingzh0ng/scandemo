package com.wearapay.net;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.wearapay.net.api.TestNetService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.net.URLEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class) public class ExampleInstrumentedTest {
  @Test public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("com.wearapay.net.test", appContext.getPackageName());

    ApiManager.get().init(appContext);

    TestNetService netService = ApiManager.get().getTestNetRepositoryModel();
    String act = "{\"version_upd_ type\": \"APP01.\",\"client_versi on_no\": \"1.0.0\"}";
    String encode = URLEncoder.encode(act, "UTF-8");
    netService.checkVersion(encode).subscribe(new Observer<String>() {
      @Override public void onSubscribe(Disposable d) {

      }

      @Override public void onNext(String s) {
        System.out.println(s);
      }

      @Override public void onError(Throwable e) {
        System.out.println(e);
      }

      @Override public void onComplete() {

      }
    });
  }
}
