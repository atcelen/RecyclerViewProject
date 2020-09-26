package com.atacelen.recyclerview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
    Documentation:
    https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter
 */
public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemHolder> {

    //Attributes
    private ArrayList<String> foodNameList;
    private ArrayList<Integer> priceList;
    private ArrayList<Bitmap> foodImageList;
    private ArrayList<Integer> backgroundImageList;

    //Constructor
    public MenuRecyclerAdapter(ArrayList<String> foodNameList, ArrayList<Integer> priceList, ArrayList<Bitmap> foodImageList, ArrayList<Integer> backgroundImageList) {
        this.foodNameList = foodNameList;
        this.priceList = priceList;
        this.foodImageList = foodImageList;
        this.backgroundImageList = backgroundImageList;
    }

    @NonNull
    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
            LayoutInflaters are used to instantiate xml files into their corresponding View Objects.
            Inflating means adding a view to an activity during runtime
            In this example we inflate a view for the recycler_row xml file during the runtime of this program.

            Documentation:
            https://developer.android.com/reference/android/view/LayoutInflater
         */

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R.layout.recycler_row, parent, false);
        return new MenuItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {

        // We set the texts and the image of our MenuItemHolder object
        holder.foodNameText.setText(foodNameList.get(position));
        holder.priceText.setText(priceList.get(position).toString() + " CHF");
        holder.imageView.setImageBitmap(foodImageList.get(position));
        holder.linearLayout.setBackgroundResource(backgroundImageList.get(position));

    }

    @Override
    public int getItemCount() {
        return foodNameList.size();
    }

    /*
        "A ViewHolder describes an item view and metadata about its place within the RecyclerView."

        https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ViewHolder
     */
    class MenuItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView foodNameText, priceText;
        LinearLayout linearLayout;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);

            // links the attributes with the recycler_row items
            imageView = itemView.findViewById(R.id.recycler_row_imageView);
            foodNameText = itemView.findViewById(R.id.recycler_row_foodNameText);
            priceText = itemView.findViewById(R.id.recycler_row_priceText);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}
