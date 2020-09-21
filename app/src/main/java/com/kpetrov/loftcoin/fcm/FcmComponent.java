package com.kpetrov.loftcoin.fcm;

import com.kpetrov.loftcoin.BaseComponent;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        FcmModule.class
}, dependencies = {
        BaseComponent.class
})

abstract class FcmComponent {

    abstract void inject(FcmService service);
}
