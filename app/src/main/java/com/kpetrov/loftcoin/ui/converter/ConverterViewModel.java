package com.kpetrov.loftcoin.ui.converter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.kpetrov.loftcoin.data.Coin;
import com.kpetrov.loftcoin.data.CoinsRepo;
import com.kpetrov.loftcoin.data.CurrencyRepo;
import com.kpetrov.loftcoin.util.RxSchedulers;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

class ConverterViewModel extends ViewModel {

    private final Subject<Coin> startCoin = BehaviorSubject.create();

    private final Subject<Coin> endCoin = BehaviorSubject.create();

    private final Subject<String> startCoinValue = BehaviorSubject.create();

    private final Subject<String> endCoinValue = BehaviorSubject.create();

    private final Observable<List<Coin>> topCoins;

    private final RxSchedulers schedulers;

    private final Observable<Double> factorStartEnd;

    private final Observable<Double> factorEndStart;

    @Inject
    ConverterViewModel(CurrencyRepo currencyRepo, CoinsRepo coinsRepo, RxSchedulers schedulers) {
        this.schedulers = schedulers;

        topCoins = currencyRepo
                .currency()
                .switchMap(coinsRepo::topCoins)
                .doOnNext(coins -> startCoin.onNext(coins.get(0)))
                .doOnNext(coins -> endCoin.onNext(coins.get(1)))
                .replay(1)
                .autoConnect();

        factorStartEnd = startCoin
                .flatMap((sc) -> endCoin
                        .map((ec) -> sc.price() / ec.price()))
                .replay(1)
                .autoConnect();

        factorEndStart = endCoin
                .flatMap((dv) -> startCoin
                        .map((rb) -> dv.price() / rb.price()))
                .replay(1)
                .autoConnect();
    }

    @NonNull
    Observable<List<Coin>> topCoins() {
        return topCoins.observeOn(schedulers.main());
    }

    @NonNull
    Observable<Coin> startCoin() {
        return startCoin.observeOn(schedulers.main());
    }

    @NonNull
    Observable<Coin> endCoin() {
        return endCoin.observeOn(schedulers.main());
    }

    @NonNull
    Observable<String> startCoinValue() {
        return endCoinValue
                .observeOn(schedulers.cmp())
                .map((s) -> s.isEmpty() ? "0.0" : s)
                .map(Double::parseDouble)
                .flatMap((value) -> factorStartEnd.map((f) -> value * f))
                .map(v -> String.format(Locale.US, "%.2f", v))
                .map((v) -> "0.0".equals(v) ? "" : v)
                .observeOn(schedulers.main());
    }

    @NonNull
    Observable<String> endCoinValue() {
        return startCoinValue
                .observeOn(schedulers.cmp())
                .map((s) -> s.isEmpty() ? "0.0" : s)
                .map(Double::parseDouble)
                .flatMap((value) -> factorEndStart.map((f) -> value * f))
                .map(vv -> String.format(Locale.US, "%.2f", vv))
                .map((vv) -> "0.0".equals(vv) ? "" : vv)
                .observeOn(schedulers.main());
    }

    void startCoin(Coin coin) {
        startCoin.onNext(coin);
    }

    void endCoin(Coin coin) {
        endCoin.onNext(coin);
    }

    void startCoinValue(CharSequence text) {
        startCoinValue.onNext(text.toString());
    }

    void endCoinValue(CharSequence text) {
        endCoinValue.onNext(text.toString());
    }

}