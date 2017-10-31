package com.wearapay.domain.user;

import com.wearapay.data.repository.ILocalRepository;
import com.wearapay.data.repository.IUserRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by lyz on 2017/10/12.
 */
@Module @Singleton public class UserModule {

  @Provides @Singleton IUserMgmt provideIUserRepository(ILocalRepository iLocalRepository,
      IUserRepository iUserRepository) {
    return new UserMgmtImpl(iLocalRepository, iUserRepository);
  }
}
