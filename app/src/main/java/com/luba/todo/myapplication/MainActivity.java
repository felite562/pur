package com.luba.todo.myapplication;

import android.R.layout;
import android.arch.lifecycle.ViewModelProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.luba.todo.myapplication.DataBase.PurchaseRepo;
import com.luba.todo.myapplication.Local.PurchaseDataSource;
import com.luba.todo.myapplication.Local.PurchaseDatabase;
import com.luba.todo.myapplication.Model.Purchase;
import com.luba.todo.myapplication.PurAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private CompositeDisposable mCompositeDisposable;
    private PurchaseRepo mPurchaseRepo;
private PurchaseDataSource purDataSource;
   List<Purchase> purchaseList;
private PurchaseViewModel purViewModel;


    private RecyclerView mListView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompositeDisposable=new CompositeDisposable();
        mListView=(RecyclerView) findViewById(R.id.listPurchases);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setHasFixedSize(true);
       PurAdapter mAdapter=new PurAdapter(purchaseList);;
        mListView.setAdapter(mAdapter);

        registerForContextMenu(mListView);
        fab=findViewById(R.id.fab);

//db
        PurchaseDatabase purchaseDatabase=PurchaseDatabase.getInstance(this);
        mPurchaseRepo=PurchaseRepo.getmInstanse(PurchaseDataSource.getInstance(purchaseDatabase.purchaseDAO()));
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disposable disposable= Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Purchase purchase=new Purchase("test", 0);

                        mPurchaseRepo.insertPurchase(purchase);
                        e.onComplete();
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                                       @Override
                                       public void accept(Object o) throws Exception {
                                           Toast.makeText(MainActivity.this,"purchase added!",Toast.LENGTH_SHORT).show();
                                       }
                                   }, new Consumer<Throwable>(){
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           Toast.makeText(MainActivity.this,""+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                       }
                                   },
                                new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        loadData();
                                    }
                                });
            }
        });


   }
    private void loadData() {
        //rx
        Disposable disposable=mPurchaseRepo.getAllPurchases()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Purchase>>() {
                    @Override
                    public void accept(List<Purchase> purchaseList) throws Exception {
                        PurAdapter pur = new PurAdapter(purchaseList);
                        mListView.setAdapter(pur);

                       }
                }, new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this,""+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {


      int position = -1; String position1;




        try { position = ((PurAdapter) mListView.getAdapter()).getPosition();
         //   AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
         //   Purchase purchase=purchaseList.get(info.position);
     //


          //  long itemId=((PurAdapter) mListView.getAdapter()).getItemId(position);
          //  final Purchase purchase=PurAdapter.ViewHolder.getPurchaseAt(position);
            //purchase= purchaseList.get(position);
           position1=position+"";

    // Toast.makeText(MainActivity.this,position1,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Exception",Toast.LENGTH_SHORT).show();
            return super.onContextItemSelected(item);
        }



        switch (item.getItemId()) {
            case 0: { if (position!= -1){

                PurAdapter mAdapter=new PurAdapter(purchaseList);;
                mListView.setAdapter(mAdapter);
                updatePurchase(mAdapter.getItem(position));
                 }



              Toast.makeText(MainActivity.this,position1,Toast.LENGTH_SHORT).show();

               /* final Purchase purchase=new Purchase();
                updatePurchase(purchase);

                purchase.setName("luba");



                final EditText edtName = new EditText(MainActivity.this);

               edtName.setText(purchase.getName());
                edtName.setHint("Enter title");
                new AlertDialog.Builder((MainActivity.this))
                        .setTitle("Edit")
                        .setMessage("Edit title")
                        .setView(edtName)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (TextUtils.isEmpty(edtName.getText().toString()))
                                    return;
                                else {
                                  //  purchase.setName(edtName.getText().toString());
                                   // updatePurchase(purchase);
                                }

                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();*/

            }
            break;
          case 1: {
             /*   new AlertDialog.Builder((MainActivity.this))

                        .setMessage("Do you want to delete " + purchase.toString() + "?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletePurchase(purchase);

                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();  */
            }
            break;
        }

        return true;
    }

    private void updatePurchase(final Purchase purchase) {
        Disposable disposable= Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                mPurchaseRepo.updatePurchase(purchase);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {

                               }
                           }, new Consumer<Throwable>(){
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainActivity.this,""+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData();
                            }
                        });
        mCompositeDisposable.add(disposable);
    }
    public void  deletePurchase(final Purchase purchase){
        Disposable disposable= Observable.create(new ObservableOnSubscribe<Object>() {
        @Override
        public void subscribe(ObservableEmitter<Object> e) throws Exception {
            mPurchaseRepo.deletePurchase(purchase);
            e.onComplete();
        }
    })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Consumer() {
                           @Override
                           public void accept(Object o) throws Exception {

                           }
                       }, new Consumer<Throwable>(){
                           @Override
                           public void accept(Throwable throwable) throws Exception {
                               Toast.makeText(MainActivity.this,""+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                           }
                       },
                    new Action() {
                        @Override
                        public void run() throws Exception {
                            loadData();
                        }
                    });
        mCompositeDisposable.add(disposable);
                }


@Override
public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        }


@Override
protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        }
 }
