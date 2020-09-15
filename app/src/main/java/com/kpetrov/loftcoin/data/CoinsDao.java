package com.kpetrov.loftcoin.data;

import androidx.annotation.WorkerThread;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import io.reactivex.Observable;

@Dao
abstract class CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    abstract Observable<List<RoomCoin>> fetchAll();

    @Query("SELECT * FROM RoomCoin ORDER BY price DESC")
    abstract Observable<List<RoomCoin>> fetchAllSortByPriceDesk();

    @Query("SELECT * FROM RoomCoin ORDER BY price ASC")
    abstract Observable<List<RoomCoin>> fetchAllSortByPriceAsc();

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC")
    abstract Observable<List<RoomCoin>> fetchAllSortByRank();

    @WorkerThread
    @Query("SELECT COUNT (id) FROM RoomCoin")
    abstract int coinsCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(List<RoomCoin> coins);
}
