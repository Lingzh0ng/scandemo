package com.wearapay.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wearapay.data.DConstant.DefaultSP;
import static com.wearapay.data.DConstant.SP_login_status;
import static com.wearapay.data.DConstant.SP_user_deviceNo;
import static com.wearapay.data.DConstant.SP_user_taken;

/**
 * Created by lyz on 2017/10/23.
 */

public class LocalRepositoryImpl implements ILocalRepository {
  private Context context;
  private SharedPreferences sp;

  public LocalRepositoryImpl(Context context) {
    this.context = context;
    sp = context.getSharedPreferences(DefaultSP, Context.MODE_PRIVATE);
  }

  @Override public boolean getLoginStatus() {
    return sp.getBoolean(SP_login_status, false);
  }

  @Override public String getUserTaken() {
    return sp.getString(SP_user_taken, "");
  }

  @Override public void saveUserTaken(String taken) {
    SharedPreferences.Editor edit = sp.edit();
    edit.putString(SP_user_taken, taken);
    edit.apply();
  }

  @Override public void updateLoginStatus(boolean status) {
    SharedPreferences.Editor edit = sp.edit();
    edit.putBoolean(SP_login_status, status);
    edit.apply();
  }

  @Override public void logout() {
    SharedPreferences.Editor edit = sp.edit();
    edit.putString(SP_user_taken, "");
    edit.putBoolean(SP_login_status, false);
    edit.apply();
  }

  @Override public void login(String taken) {
    SharedPreferences.Editor edit = sp.edit();
    edit.putString(SP_user_taken, taken);
    edit.putBoolean(SP_login_status, true);
    edit.apply();
  }

  @Override public void saveDeviceNo(String deviceNo) {
    SharedPreferences.Editor edit = sp.edit();
    edit.putString(SP_user_deviceNo, deviceNo);
    edit.apply();
  }

  @Override public String getDeviceNo() {
    return sp.getString(SP_user_deviceNo, "");
  }
}
