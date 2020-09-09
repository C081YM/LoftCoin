package com.kpetrov.loftcoin;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Context context(Application app) {
        return app.getApplicationContext();
    }
}
