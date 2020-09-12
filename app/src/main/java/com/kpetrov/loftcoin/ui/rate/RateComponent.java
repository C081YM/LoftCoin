package com.kpetrov.loftcoin.ui.rate;

import androidx.lifecycle.ViewModelProvider;
import com.kpetrov.loftcoin.BaseComponent;
import com.kpetrov.loftcoin.util.ViewModelModule;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        RateModule.class,
        ViewModelModule.class
}, dependencies = {
        BaseComponent.class
})

abstract class RateComponent {
    abstract ViewModelProvider.Factory viewModelFactory();
    abstract RateAdapter rateAdapter();
}
