package com.luba.todo.myapplication.Local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.luba.todo.myapplication.Model.Purchase;

import static android.arch.persistence.room.Room.databaseBuilder;
import static com.luba.todo.myapplication.Local.PurchaseDatabase.DATABASE_VERSION;

@Database(entities= Purchase.class, version=DATABASE_VERSION, exportSchema = false)
public abstract class PurchaseDatabase extends RoomDatabase {


    public static final String DATABASE_NAME="myDB";
    public  static final int DATABASE_VERSION = 1;


    public abstract PurchaseDAO purchaseDAO();

    private static PurchaseDatabase mInstanse;

    public static PurchaseDatabase getInstance(Context context){
        if (mInstanse==null){
            mInstanse= databaseBuilder(context, PurchaseDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstanse;
    }
}
