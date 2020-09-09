package com.kpetrov.loftcoin.ui.rate;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
abstract class RateModule {

    @Binds
    @IntoMap
    @ClassKey(RateViewModel.class)
    abstract ViewModel rateViewModel(RateViewModel impl);



}
