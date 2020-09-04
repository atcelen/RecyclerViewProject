package com.atacelen.recyclerview;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemHolder> {

    private ArrayList<String> foodNameList;
    private ArrayList<Integer> priceList;
    private ArrayList<Bitmap> foodImageList;

    public MenuRecyclerAdapter(ArrayList<String> foodNameList, ArrayList<Integer> priceList, ArrayList<Bitmap> foodImageList) {
        this.foodNameList = foodNameList;
        this.priceList = priceList;
        this.foodImageList = foodImageList;
    }

    @NonNull
    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new MenuItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {
        holder.foodNameText.setText(foodNameList.get(position));
        holder.priceText.setText(priceList.get(position).toString() + " CHF");
        holder.imageView.setImageBitmap(foodImageList.get(position));

    }

    @Override
    public int getItemCount() {
        return foodNameList.size();
    }

    class MenuItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView foodNameText, priceText;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recycler_row_imageView);
            foodNameText = itemView.findViewById(R.id.recycler_row_foodNameText);
            priceText = itemView.findViewById(R.id.recycler_row_priceText);
        }
    }

}
