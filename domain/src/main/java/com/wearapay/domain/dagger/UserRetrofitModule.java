package com.wearapay.domain.dagger;

import android.content.Context;

import com.wearapay.domain.DConstant;
import com.wearapay.net.RetrofitManager;
import com.wearapay.net.handler.DefaultHandle;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Retrofit;

import static com.wearapay.domain.DConstant.BASE_URL;
import static com.wearapay.domain.DConstant.BASE_URL_1;

/**
 * Created by lyz on 2017/10/12.
 */

@Module
@Singleton
public class UserRetrofitModule {
    @Provides
    @Singleton
    @Named(BASE_URL)
    Retrofit provideIWeatherRestService(Context context, @Named(DConstant.DefaultHandle) DefaultHandle handle) {
        System.out.println("provideIWeatherRest2Service");
        Retrofit retrofit = RetrofitManager.get().getRetrofit(context, handle, BASE_URL);
        System.out.println("retrofit : " + (retrofit == null) + BASE_URL_1);
        return retrofit;
    }

    @Provides
    @Singleton
    @Named(BASE_URL_1)
    Retrofit provideIWeatherRest2Service(Context context, @Named(DConstant.DefaultHandle) DefaultHandle handle) {
        System.out.println("provideIWeatherRest2Service");
        Retrofit retrofit = RetrofitManager.get().getRetrofit(context, handle, BASE_URL_1);
        System.out.println("retrofit : " + (retrofit == null) + BASE_URL_1);
        return retrofit;
    }

    @Provides
    @Singleton
    @Named(DConstant.DefaultHandle)
    DefaultHandle provideDefaultHandle() {
        return new DefaultHandle();
    }
}
