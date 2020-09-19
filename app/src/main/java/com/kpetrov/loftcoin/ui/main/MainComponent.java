package com.kpetrov.loftcoin.ui.main;

import com.kpetrov.loftcoin.BaseComponent;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        MainModule.class
}, dependencies = {
        BaseComponent.class
})

abstract class MainComponent {
    abstract void inject(MainActivity activity);
}
