package com.kpetrov.loftcoin.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import com.kpetrov.loftcoin.ui.converter.CoinsSheet;
import com.kpetrov.loftcoin.ui.converter.ConverterFragment;
import com.kpetrov.loftcoin.ui.currency.CurrencyDialog;
import com.kpetrov.loftcoin.ui.rate.RateFragment;
import com.kpetrov.loftcoin.ui.wallets.WalletsFragment;
import com.kpetrov.loftcoin.util.LoftFragmentFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
abstract class MainModule {

    @Binds
    abstract FragmentFactory fragmentFactory(LoftFragmentFactory fragmentFactory);

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

    @Binds
    @IntoMap
    @ClassKey(CurrencyDialog.class)
    abstract Fragment currencyDialog(CurrencyDialog impl);

    @Binds
    @IntoMap
    @ClassKey(CoinsSheet.class)
    abstract Fragment coinsSheet(CoinsSheet impl);


}
