package com.luba.todo.myapplication.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.luba.todo.myapplication.Model.Purchase;

import java.util.List;

import io.reactivex.Flowable;
@Dao
public interface PurchaseDAO {

    @Query("SELECT * FROM purchases WHERE id=:purchaseId")
    Flowable<Purchase> getPurchaseById (int purchaseId);

    @Query("SELECT * FROM purchases")
    Flowable<List<Purchase>> getAllPurchases();

    @Insert
    void insertPurchase(Purchase... purchase);

    @Update
    void updatePurchase(Purchase... purchase);


    @Delete
    void deletePurchase(Purchase... purchase);

    @Query("DELETE FROM purchases")
    void deleteAllPurchases();

}
