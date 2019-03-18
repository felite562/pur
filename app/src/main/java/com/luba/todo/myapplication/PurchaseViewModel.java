package com.luba.todo.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.luba.todo.myapplication.DataBase.IPurchaseDataSource;
import com.luba.todo.myapplication.DataBase.PurchaseRepo;
import com.luba.todo.myapplication.Local.PurchaseDataSource;
import com.luba.todo.myapplication.Local.PurchaseDatabase;
import com.luba.todo.myapplication.Model.Purchase;

import java.util.List;

import io.reactivex.Flowable;

public class PurchaseViewModel extends AndroidViewModel {
    private PurchaseRepo repository;
    private Flowable<List<Purchase>> alPurchases;


    public PurchaseViewModel(@NonNull Application application) {
        super(application);
        repository=new PurchaseRepo((IPurchaseDataSource) application);
        alPurchases=repository.getAllPurchases();
    }
}
