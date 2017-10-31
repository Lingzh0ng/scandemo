package com.wearapay.data.dagger;

import android.content.Context;
import com.wearapay.data.repository.ILocalRepository;
import com.wearapay.data.repository.IUserRepository;
import com.wearapay.data.repository.LocalRepositoryImpl;
import com.wearapay.data.repository.UserNetRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit2.Retrofit;

import static com.wearapay.data.DConstant.BASE_URL_1;

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
