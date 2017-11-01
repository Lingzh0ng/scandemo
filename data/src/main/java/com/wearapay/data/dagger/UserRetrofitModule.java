package com.wearapay.data.dagger;

import android.content.Context;

import com.wearapay.data.DConstant;
import com.wearapay.net.RetrofitManager;
import com.wearapay.net.handler.DefaultHandle;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Retrofit;

import static com.wearapay.data.DConstant.BASE_URL;
import static com.wearapay.data.DConstant.BASE_URL_1;

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
        Retrofit retrofit = RetrofitManager.get().getRetrofit(context, handle, BASE_URL);
        return retrofit;
    }

    @Provides
    @Singleton
    @Named(BASE_URL_1)
    Retrofit provideIWeatherRest2Service(Context context, @Named(DConstant.DefaultHandle) DefaultHandle handle) {
        Retrofit retrofit = RetrofitManager.get().getRetrofit(context, handle, BASE_URL_1);
        return retrofit;
    }

    @Provides
    @Singleton
    @Named(DConstant.DefaultHandle)
    DefaultHandle provideDefaultHandle() {
        return new DefaultHandle();
    }
}
