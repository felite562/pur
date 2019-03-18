package com.luba.todo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luba.todo.myapplication.Model.Purchase;
import java.util.ArrayList;
import java.util.List;

public class PurAdapter extends RecyclerView.Adapter<PurAdapter.ViewHolder> {
   List<Purchase> purchaseList=new ArrayList<>();

  public PurAdapter(List<Purchase> purchaseList) {
       this.purchaseList = purchaseList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_new,viewGroup,false);
        itemView.setClickable(true);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Purchase currentPurchase =purchaseList.get(position);

        holder.myName.setText(currentPurchase.getName());
        holder.myPrice.setText(currentPurchase.getName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                setPosition(holder.getAdapterPosition());

                return false;
            }
        });
    }
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }
    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }



public Purchase getPurchaseAt(int position){
return purchaseList.get(position);
}

    public Purchase getItem(int position) {
        return purchaseList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        private TextView myName;
        private TextView myPrice;

        public ViewHolder(View view) {
            super(view);
            myName = view
                    .findViewById(R.id.tvName);
            myPrice = view
                    .findViewById(R.id.tvPrice);
            view.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.setHeaderTitle("select action: ");
            menu.add(Menu.NONE, 0, Menu.NONE, "Update");
            menu.add(Menu.NONE, 1, Menu.NONE, "Delete");
        }


    }  }