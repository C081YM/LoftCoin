package com.kpetrov.loftcoin.ui.rate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.kpetrov.loftcoin.data.Coin;
import com.kpetrov.loftcoin.data.CoinsRepo;
import com.kpetrov.loftcoin.data.CurrencyRepo;
import com.kpetrov.loftcoin.data.Sorting;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;

public class RateViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();
    private final MutableLiveData<AtomicBoolean> forceRefresh = new MutableLiveData<>(new AtomicBoolean(true));
    private final LiveData<List<Coin>> coins;
    private final MutableLiveData<Sorting> sorting = new MutableLiveData<>(Sorting.PRICE_DESK);
    private int sortingIndex = 1;

    @Inject
    RateViewModel(CoinsRepo coinsRepo, CurrencyRepo currencyRepo) {

        final LiveData<CoinsRepo.Query> query = Transformations.switchMap(forceRefresh, (r) -> {
            return Transformations.switchMap(currencyRepo.currency(), (c) -> {
                r.set(true);
                isRefreshing.postValue(true);
                return Transformations.map(sorting, (s) -> {
                    return CoinsRepo.Query.builder()
                             .currency(c.code())
                             .forceUpdate(r.getAndSet(false))
                             .sorting(s)
                             .build();
                    });
                });
        });

        final LiveData<List<Coin>> coins = Transformations.switchMap(query, coinsRepo::listings);

        this.coins = Transformations.map(coins, (c) -> {
            isRefreshing.postValue(false);
            return c;
        });
    }

    @NonNull
    LiveData<List<Coin>> coins() {
        return coins;
    }

    @NonNull
    LiveData<Boolean> isRefreshing() {
        return isRefreshing;
    }

    final void refresh() {
       forceRefresh.postValue(new AtomicBoolean(true));
    }

    void switchSortingOrder() {
        sorting.postValue(Sorting.values()[sortingIndex++ % Sorting.values().length]);
    }
}