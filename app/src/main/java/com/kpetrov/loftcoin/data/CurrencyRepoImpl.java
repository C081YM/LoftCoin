package com.kpetrov.loftcoin.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.kpetrov.loftcoin.R;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class CurrencyRepoImpl implements CurrencyRepo {

    private final Context context;

    @Inject
    CurrencyRepoImpl(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LiveData<List<Currency>> availableCurrencies() {
        return new AllCurrenciesLiveData(context);
    }

    @NonNull
    @Override
    public LiveData<Currency> currency() {
        return null;
    }

    @Override
    public void updateCurrency(@NonNull Currency currency) {

    }

    private static class AllCurrenciesLiveData extends LiveData<List<Currency>> {

        private final Context context;

        AllCurrenciesLiveData(Context context) {
            this.context = context;

        }

        @Override
        protected void onActive() {
            List<Currency> currencies = new ArrayList<>();
            currencies.add(Currency.create("$", "USD", context.getString(R.string.usd)));
            currencies.add(Currency.create("€", "EUR", context.getString(R.string.eur)));
            currencies.add(Currency.create("₽", "RUB", context.getString(R.string.rub)));
            setValue(currencies);
        }
    }
}