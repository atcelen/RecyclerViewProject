package com.atacelen.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> foodNameList;
    ArrayList<Integer> priceList;
    ArrayList<Bitmap> foodImageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodNameList = new ArrayList<>();
        priceList = new ArrayList<>();
        foodImageList = new ArrayList<>();

        foodNameList.add("Quattro Formaggi");
        foodNameList.add("Ravioli");
        foodNameList.add("Paella");
        foodNameList.add("Fondue");
        foodNameList.add("Sushi");

        priceList.add(20);
        priceList.add(17);
        priceList.add(23);
        priceList.add(25);
        priceList.add(15);

        foodImageList.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.quattroformaggi));
        foodImageList.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ravioli));
        foodImageList.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.paella));
        foodImageList.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.fondue));
        foodImageList.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sushi));


        // We define the recyclerView object
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        /*
        "A LayoutManager is responsible for measuring and positioning item views within a RecyclerView as well as determining the policy for when to recycle item views that are no longer visible to the user.
        By changing the LayoutManager a RecyclerView can be used to implement a standard vertically scrolling list, a uniform grid, staggered grids, horizontally scrolling collections and more.
        Several stock layout managers are provided for general use."

        https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.LayoutManager
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //See "MenuRecyclerAdapter.java" for details
        MenuRecyclerAdapter menuRecyclerAdapter = new MenuRecyclerAdapter(foodNameList, priceList, foodImageList);
        recyclerView.setAdapter(menuRecyclerAdapter);

        /*
            If we come to the Main Activity from the Add Item Activity(we check that by putting in an intent ID),
            we extract the data put into the intent and add them to the ArrayLists.
            In the end, we notify the adapter of the data set change so that the recycler view can update itself.
         */
        Intent intent = getIntent();
        if(intent != null && intent.getStringExtra("intentID") != null) {
            foodNameList.add(intent.getStringExtra("addedFoodName"));
            priceList.add(intent.getIntExtra("addedFoodPrice", 0));

            Singleton singleton = Singleton.getInstance();
            foodImageList.add(singleton.getChosenFoodImage());

            menuRecyclerAdapter.notifyDataSetChanged();

        }



    }

    //To specify the options menu for an activity, we override "onCreateOptionsMenu()" and "onOptionsItemSelected()"

    /*
        In the onCreateOptionsMenu() method we inflate our menu resource "add_item" into the "menu" which is provided as a parameter
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item, menu);

        return super.onCreateOptionsMenu(menu);
    }
    /*
        The onOptionsItemSelected() method is called when an item from the dropdown menu is selected by the user.
        In this implementation, we take the user from the Main Activity to the Add Item Activity via an intent
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_food_item) {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}