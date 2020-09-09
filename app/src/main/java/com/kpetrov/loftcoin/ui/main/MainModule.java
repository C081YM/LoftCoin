package com.kpetrov.loftcoin.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.kpetrov.loftcoin.ui.converter.ConverterFragment;
import com.kpetrov.loftcoin.ui.rate.RateFragment;
import com.kpetrov.loftcoin.ui.wallets.WalletsAdapter;
import com.kpetrov.loftcoin.ui.wallets.WalletsFragment;
import com.kpetrov.loftcoin.util.LoftFragmentFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
abstract class MainModule {

    @Binds
    abstract FragmentFactory fragmentFactory(LoftFragmentFactory impl);

    @Binds
    @IntoMap
    @ClassKey(RateFragment.class)
    abstract Fragment rateFragment(RateFragment impl);

    @Binds
    @IntoMap
    @ClassKey(WalletsFragment.class)
    abstract Fragment walletsFragment(WalletsFragment impl);

    @Binds
    @IntoMap
    @ClassKey(ConverterFragment.class)
    abstract Fragment converterFragment(ConverterFragment impl);

}
