package com.kpetrov.loftcoin.ui.rate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.kpetrov.loftcoin.data.Coin;
import com.kpetrov.loftcoin.data.CoinsRepo;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;

public class RateViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Boolean> forceRefresh = new MutableLiveData<>(false);
    private final LiveData<List<Coin>> coins;

    @Inject
    public RateViewModel(CoinsRepo coinsRepo) {
        final LiveData<CoinsRepo.Query> query = Transformations
            .map(forceRefresh, (r) -> {
                isRefreshing.postValue(true);
                return CoinsRepo.Query
                    .builder()
                    .forceUpdate(r)
                    .currency("USD")
                    .build();
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
       forceRefresh.postValue(true);
    }
}