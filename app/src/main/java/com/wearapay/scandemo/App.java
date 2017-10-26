package com.wearapay.scandemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.wearapay.scandemo.dagger.ApplicationComponent;
import com.wearapay.scandemo.dagger.ApplicationModule;
import com.wearapay.scandemo.dagger.DaggerApplicationComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/6/27.
 */
public class App extends Application {

  private ApplicationComponent component;

  public static App app;

  public boolean isExit() {
    return isExit;
  }

  private boolean isExit = false;

  private List<Activity> activityList = new ArrayList<>();
  private int activityStatus = 0;

  @Override public void onCreate() {
    super.onCreate();
    app = this;
    component =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    component.inject(this);
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityList.add(activity);
        //System.out.println("onActivityCreated:" + activity.getClass().getSimpleName());
      }

      @Override public void onActivityStarted(Activity activity) {
        activityStatus++;
        //System.out.println("onActivityStarted:" + activity.getClass().getSimpleName());
      }

      @Override public void onActivityResumed(Activity activity) {

      }

      @Override public void onActivityPaused(Activity activity) {

      }

      @Override public void onActivityStopped(Activity activity) {
        activityStatus--;
        //System.out.println("onActivityStopped:" + activity.getClass().getSimpleName());
      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //System.out.println("onActivitySaveInstanceState:" + activity.getClass().getSimpleName());
      }

      @Override public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);
        //System.out.println("onActivityDestroyed:" + activity.getClass().getSimpleName());
      }
    });

  }

  public ApplicationComponent getComponent() {
    return component;
  }

  public List<Activity> getActivityList() {
    return activityList;
  }

  public Activity getCurrentActivity() {
    return activityList.get(activityList.size() - 1);
  }

  public boolean isAppHide() {
    return activityStatus == 1;
  }

  public void exitApp() {
    isExit = true;
    for (int i = 0; i < activityList.size(); i++) {
      activityList.get(i).finish();
    }
  }
}