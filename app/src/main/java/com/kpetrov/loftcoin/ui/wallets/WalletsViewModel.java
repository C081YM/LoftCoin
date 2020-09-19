package com.kpetrov.loftcoin.ui.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.kpetrov.loftcoin.data.Coin;
import com.kpetrov.loftcoin.data.CurrencyRepo;
import com.kpetrov.loftcoin.data.Transaction;
import com.kpetrov.loftcoin.data.Wallet;
import com.kpetrov.loftcoin.data.WalletsRepo;
import com.kpetrov.loftcoin.util.RxSchedulers;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

class WalletsViewModel extends ViewModel {

    private final Subject<Integer> walletPosition = BehaviorSubject.createDefault(0);

    private final Observable<List<Wallet>> wallets;

    private  final Observable<List<Transaction>> transactions;

    private final WalletsRepo walletsRepo;

    private final CurrencyRepo currencyRepo;

    private final RxSchedulers schedulers;

    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.walletsRepo = walletsRepo;
        this.currencyRepo = currencyRepo;
        this.schedulers = schedulers;

        wallets = currencyRepo.currency()
                .switchMap(walletsRepo::wallets)
                .replay(1)
                .autoConnect();

        transactions = wallets
                .switchMap((wallets) -> walletPosition
                        .map(wallets::get)
                )
                .switchMap(walletsRepo::transactions)
                .replay(1)
                .autoConnect();
    }

    @NonNull
    Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers.main());
    }

    @NonNull
    Observable<List<Transaction>> transactions() {
        return transactions.observeOn(schedulers.main());
    }

    @NonNull
    Completable addWallet() {
        return wallets
                .switchMapSingle((list) -> Observable
                        .fromIterable(list)
                        .map(Wallet::coin)
                        .map(Coin::id)
                        .toList()
                )
                .switchMapCompletable((ids) -> currencyRepo
                        .currency()
                        .firstOrError()
                        .map((currency) -> walletsRepo.addWallet(currency, ids))
                        .ignoreElement()
                )
                .observeOn(schedulers.main());
    }

    void changeWallet(int position) {
        walletPosition.onNext(position);
    }

}