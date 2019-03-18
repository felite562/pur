package com.luba.todo.myapplication.DataBase;

import com.luba.todo.myapplication.Model.Purchase;

import java.util.List;

import io.reactivex.Flowable;

public class PurchaseRepo implements IPurchaseDataSource {
    private IPurchaseDataSource mLocDataSource;
    private  static PurchaseRepo mInstanse;
    public static PurchaseRepo getmInstanse(IPurchaseDataSource mLocDataSource) {

        if(mInstanse==null){
            mInstanse=new PurchaseRepo(mLocDataSource);
        }

        return mInstanse;
    }



    public PurchaseRepo(IPurchaseDataSource locDataSource) {
        mLocDataSource = locDataSource;
    }

    @Override
    public Flowable<Purchase> getPurchaseById(int purchaseId) {
        return mLocDataSource.getPurchaseById(purchaseId);
    }

    @Override
    public Flowable<List<Purchase>> getAllPurchases() {
        return mLocDataSource.getAllPurchases();
    }

    @Override
    public void insertPurchase(Purchase... purchases) {
    mLocDataSource.insertPurchase(purchases);
    }

    @Override
    public void updatePurchase(Purchase... purchases) {
        mLocDataSource.updatePurchase(purchases);
    }

    @Override
    public void deletePurchase(Purchase... purchases) {
        mLocDataSource.deletePurchase(purchases);
    }

    @Override
    public void deleteAllPurchases() {
        mLocDataSource.deletePurchase();
    }
}
