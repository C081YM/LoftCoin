package com.kpetrov.loftcoin.ui.wallets;


import androidx.lifecycle.ViewModelProvider;
import com.kpetrov.loftcoin.BaseComponent;
import com.kpetrov.loftcoin.util.ViewModelModule;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        WalletsModule.class,
        ViewModelModule.class
}, dependencies = {
        BaseComponent.class
})

abstract class WalletsComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

    abstract WalletsAdapter walletsAdapter();

    abstract TransactionsAdapter transactionsAdapter();
}
