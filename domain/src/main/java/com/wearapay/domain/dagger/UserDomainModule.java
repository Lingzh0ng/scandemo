package com.wearapay.domain.dagger;

import android.content.Context;
import com.wearapay.domain.repository.ILocalRepository;
import com.wearapay.domain.repository.IUserRepository;
import com.wearapay.domain.repository.LocalRepositoryImpl;
import com.wearapay.domain.repository.UserNetRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit2.Retrofit;

import static com.wearapay.domain.DConstant.BASE_URL_1;

/**
 * Created by lyz on 2017/10/12.
 */
@Module @Singleton public class UserDomainModule {

  @Provides @Singleton IUserRepository provideIUserRepository(@Named(BASE_URL_1) Retrofit retrofit,
      Context context) {
    return new UserNetRepositoryImpl(context, retrofit);
  }

  @Provides @Singleton ILocalRepository provideILocalRepository(Context context) {
    return new LocalRepositoryImpl(context);
  }
}
