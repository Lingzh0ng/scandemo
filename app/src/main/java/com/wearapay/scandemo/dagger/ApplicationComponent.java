package com.wearapay.scandemo.dagger;

/**
 * Created by lyz on 2017/6/27.
 */

import com.wearapay.data.dagger.UserDomainModule;
import com.wearapay.data.dagger.UserRetrofitModule;
import com.wearapay.domain.UserModule;
import com.wearapay.scandemo.App;
import com.wearapay.scandemo.MainActivity;
import com.wearapay.scandemo.module.device.AddDeviceFragment;
import com.wearapay.scandemo.module.device.ChargeFragment;
import com.wearapay.scandemo.module.device.DeviceHistoryFragment;
import com.wearapay.scandemo.module.device.DeviceManagerFragment;
import com.wearapay.scandemo.module.home.HomeFragment;
import com.wearapay.scandemo.module.login.LoginFragment;
import com.wearapay.scandemo.module.login.RegistFragment;
import com.wearapay.scandemo.module.user.ChangePswFragment;
import com.wearapay.scandemo.module.user.UserInfoFragment;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ApplicationModule.class, UserRetrofitModule.class, UserDomainModule.class, UserModule.class
}) public interface ApplicationComponent {
  void inject(App application);

  void inject(MainActivity mainActivity);

  void inject(LoginFragment loginFragment);

  void inject(RegistFragment registFragment);

  void inject(HomeFragment homeFragment);

  void inject(AddDeviceFragment addDeviceFragment);

  void inject(ChargeFragment chargeFragment);

  void inject(DeviceHistoryFragment deviceHistoryFragment);

  void inject(DeviceManagerFragment deviceManagerFragment);

  void inject(ChangePswFragment changePswFragment);

  void inject(UserInfoFragment userInfoFragment);
}
