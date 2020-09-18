package com.kpetrov.loftcoin.ui.rate;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.kpetrov.loftcoin.data.Coin;
import com.kpetrov.loftcoin.data.CoinsRepo;
import com.kpetrov.loftcoin.data.CurrencyRepo;
import com.kpetrov.loftcoin.data.Sorting;
import com.kpetrov.loftcoin.util.RxSchedulers;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class RateViewModel extends ViewModel {

    private final Subject<Boolean> isRefreshing = BehaviorSubject.create();
    private final Subject<Class<?>> pullToRefresh = BehaviorSubject.createDefault(Void.TYPE);
    private final Subject<Sorting> sorting = BehaviorSubject.createDefault(Sorting.RANK);
    private final AtomicBoolean forceUpdate = new AtomicBoolean();
    private final Observable<List<Coin>> coins;
    private final RxSchedulers schedulers;
    private int sortingIndex = 1;

    @Inject
    RateViewModel(CoinsRepo coinsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.schedulers = schedulers;
        this.coins = pullToRefresh
            .map((ptr) -> CoinsRepo.Query.builder())
            .switchMap((qb) -> currencyRepo.currency()
            .map((c) -> qb.currency(c.code())))
            .doOnNext((qb) -> forceUpdate.set(true))
            .doOnNext((qb) -> isRefreshing.onNext(true))
            .switchMap((qb) -> sorting.map(qb::sorting))
            .map((qb) -> qb.forceUpdate(forceUpdate.getAndSet(false)))
            .map(CoinsRepo.Query.Builder::build)
            .switchMap(coinsRepo::listings)
            .doOnEach((ntf) -> isRefreshing.onNext(false));
    }

    @NonNull
    Observable<List<Coin>> coins() {
        return coins.observeOn(schedulers.main());
    }

    @NonNull
    Observable<Boolean> isRefreshing() {
        return isRefreshing.observeOn(schedulers.main());
    }

    final void refresh() {
       pullToRefresh.onNext(Void.TYPE);
    }

    void switchSortingOrder() {
        sorting.onNext(Sorting.values()[sortingIndex++ % Sorting.values().length]);
    }
}