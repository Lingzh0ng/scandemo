package com.wearapay.scandemo.dagger;

/**
 * Created by lyz on 2017/6/27.
 */

import com.wearapay.domain.dagger.UserDomainModule;
import com.wearapay.domain.dagger.UserRetrofitModule;
import com.wearapay.scandemo.App;
import com.wearapay.scandemo.MainActivity;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ApplicationModule.class, UserRetrofitModule.class, UserDomainModule.class
})
public interface ApplicationComponent {
    void inject(App application);

    void inject(MainActivity mainActivity);
}
