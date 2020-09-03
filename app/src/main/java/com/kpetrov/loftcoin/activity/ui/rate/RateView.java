package com.kpetrov.loftcoin.activity.ui.rate;

import androidx.annotation.NonNull;

import com.kpetrov.loftcoin.activity.data.Coin;

import java.util.List;

public interface RateView {

    void showCoins (@NonNull List<? extends Coin> coins);

    void showError (@NonNull String error);

}
