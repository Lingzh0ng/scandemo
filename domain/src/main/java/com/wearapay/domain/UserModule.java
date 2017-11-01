package com.wearapay.domain;

import com.wearapay.data.repository.IDeviceRepository;
import com.wearapay.data.repository.ILocalRepository;
import com.wearapay.data.repository.IUserRepository;
import com.wearapay.domain.devices.DeviceMgmtImpl;
import com.wearapay.domain.devices.IDeviceMgmt;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.domain.user.UserMgmtImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by lyz on 2017/10/31.
 */
@Module @Singleton public class UserModule {

  @Provides @Singleton IUserMgmt provideIUserMgnt(ILocalRepository iLocalRepository,
      IUserRepository iUserRepository) {
    return new UserMgmtImpl(iLocalRepository, iUserRepository);
  }

  @Provides @Singleton IDeviceMgmt provideIDeviceMgmt(ILocalRepository iLocalRepository,
      IDeviceRepository iDeviceRepository) {
    return new DeviceMgmtImpl(iLocalRepository, iDeviceRepository);
  }
}
