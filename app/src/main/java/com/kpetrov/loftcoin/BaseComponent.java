package com.kpetrov.loftcoin;

import android.content.Context;
import com.kpetrov.loftcoin.data.CoinsRepo;
import com.kpetrov.loftcoin.data.CurrencyRepo;
import com.kpetrov.loftcoin.util.LoaderImages;
import com.kpetrov.loftcoin.util.RxSchedulers;

public interface BaseComponent {
    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
    LoaderImages loaderImages();
    RxSchedulers schedulers();
}
