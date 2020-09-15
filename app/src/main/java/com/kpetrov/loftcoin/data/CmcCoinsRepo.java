package com.kpetrov.loftcoin.data;

import androidx.annotation.NonNull;
import com.kpetrov.loftcoin.util.RxSchedulers;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Observable;

@Singleton
class CmcCoinsRepo implements CoinsRepo {

    private final CmcApi api;
    private final LoftDatabase db;
    private final RxSchedulers schedulers;

    @Inject
    public CmcCoinsRepo(CmcApi api, LoftDatabase db, RxSchedulers schedulers) {
        this.api = api;
        this.db = db;
        this.schedulers = schedulers;
    }

    @NonNull
    @Override
    public Observable<List<Coin>> listings(@NonNull Query query) {
         return Observable
            .fromCallable(() -> query.forceUpdate() || db.coins().coinsCount() == 0)
            .switchMap((f) -> f ? api.listings(query.currency()) : Observable.empty())
            .map((listings) ->  mapToRoomCoins(query, listings.data()))
            .doOnNext((coins) -> db.coins().insert(coins))
            .switchMap((coins) -> fetchFromDb(query))
            .switchIfEmpty(fetchFromDb(query))
            .<List<Coin>>map(ArrayList::new)
            .subscribeOn(schedulers.io())
        ;
    }

    private Observable<List<RoomCoin>> fetchFromDb(Query query) {
        if (query.sorting() == Sorting.PRICE_DESK) {
            return db.coins().fetchAllSortByPriceDesk();
        } else {
            return db.coins().fetchAllSortByPriceAsc();
        }
    }

    private List<RoomCoin> mapToRoomCoins (Query query, List<? extends Coin> data) {
        List<RoomCoin> roomCoins = new ArrayList<>(data.size());
        for (Coin coin : data) {
            roomCoins.add(RoomCoin.create(
                    coin.name(),
                    coin.symbol(),
                    coin.rank(),
                    coin.price(),
                    coin.change24h(),
                    query.currency(),
                    coin.id()
            ));
        }
        return roomCoins;
    }
}