package com.luba.todo.myapplication.DataBase;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.luba.todo.myapplication.Model.Purchase;

import java.util.List;

import io.reactivex.Flowable;

public interface IPurchaseDataSource {
    Flowable<Purchase> getPurchaseById(int purchaseId);
    Flowable<List<Purchase>> getAllPurchases();
        void insertPurchase (Purchase... purchase);
        void updatePurchase(Purchase... purchase);
        void deletePurchase(Purchase... purchase);
        void deleteAllPurchases();
}
