package com.luba.todo.myapplication.Local;

import com.luba.todo.myapplication.DataBase.IPurchaseDataSource;
import com.luba.todo.myapplication.Model.Purchase;

import java.util.List;

import io.reactivex.Flowable;

public class PurchaseDataSource implements IPurchaseDataSource {

   private PurchaseDAO purchaseDAO;
   private static PurchaseDataSource mInstance;

    public PurchaseDataSource(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }

    public static PurchaseDataSource getInstance(PurchaseDAO purchaseDAO) {

        if(mInstance==null){
            mInstance=new PurchaseDataSource(purchaseDAO);
        }

        return mInstance;
    }

    @Override
    public Flowable<Purchase> getPurchaseById(int purchaseId) {
        return purchaseDAO.getPurchaseById(purchaseId);
    }

    @Override
    public Flowable<List<Purchase>> getAllPurchases() {
        return purchaseDAO.getAllPurchases();
    }

    @Override
    public void insertPurchase(Purchase... purchases) {
    purchaseDAO.insertPurchase(purchases);
    }

    @Override
    public void updatePurchase(Purchase... purchases) {
        purchaseDAO.updatePurchase(purchases);
    }

    @Override
    public void deletePurchase(Purchase... purchases) {
    purchaseDAO.deletePurchase(purchases);
    }

    @Override
    public void deleteAllPurchases() {
    purchaseDAO.deleteAllPurchases();
    }
}
