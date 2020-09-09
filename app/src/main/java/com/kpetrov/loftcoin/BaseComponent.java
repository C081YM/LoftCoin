package com.kpetrov.loftcoin;

import android.content.Context;

import com.kpetrov.loftcoin.data.CoinsRepo;
import com.kpetrov.loftcoin.data.CurrencyRepo;

public interface BaseComponent {

    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();

}
