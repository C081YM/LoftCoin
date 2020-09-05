package com.kpetrov.loftcoin.data;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import com.kpetrov.loftcoin.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyRepoImpl implements CurrencyRepo {

    private final Context context;

    public CurrencyRepoImpl(@NonNull Context context) {
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