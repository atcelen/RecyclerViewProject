package com.atacelen.recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.Permission;


public class AddItemActivity extends AppCompatActivity {

    Bitmap selectedImage;
    ImageView imageView;
    TextView foodName, foodPrice;
    Button button;

    //Called when the class is loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        imageView = findViewById(R.id.imageView);
        foodName = findViewById(R.id.foodName);
        foodPrice = findViewById(R.id.foodPrice);
        button = findViewById(R.id.button);
    }

    /*
        This method will be called when the user clicks on the screen to select an Image from the user's gallery.
        Due to privacy reasons, we should however first ask the user for permission to access its storage (as shown in the if-clause)
        If this permission is given once, we don't have to ask the user for permission again and can call an intent with a picking action to reach the gallery and pick an image.
        To be able to access the external storage at all, the line "<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>" needs to be added to the Android Manifest!!
        Check the "AndroidManifest.xml" file under manifests
        The requestCodes are used to distinguish between the method calls.
     */
    public void selectImage(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }

    /*
        The "onRequestPermissionResult" method is called for the result of the "requestPermissions" method
        If the permission is given, we call the same code block as the "else" code block from the "selectImage" method.
        It should be checked that the grantResults array is not empty in order to prevent a reference to a null object and also that the permission is given.
        The requestCode has to be checked as well because in larger scale projects there might be multiple permission requests with different requestCodes.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*
        The "onActivityResult" method is called for the result of the "startActivityForResult" method
        The "getBitmap" method in the "else" block of the method has deprecated with API 29, however many devices still use lower APIs.
        Therefore we adjust the code in the way that it calls certain methods depending on the API level of the device.
        The newer way of turning the URI into a bitmap is by creating a "ImageDecoder.Source" object and decoding it into a bitmap

        "A Uniform Resource Identifier (URI) is a string of characters that unambiguously identifies a particular resource."
        https://en.wikipedia.org/wiki/Uniform_Resource_Identifier

        For more Information on "onActivityResult":
        https://developer.android.com/training/basics/intents/result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            try {
                if(Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    imageView.setImageBitmap(selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
        The "add" method is called , when the button is clicked on (check the OnClick method for the "add" button)
        The food name and price information are put into the intent, which is then used to go back to the Main Activity.
        The information inserted into the intent with "putExtra" is extracted in the Main Activity
        We carry the images using the Singleton object
     */

    public void add(View view) {
        Intent intentToMainActivity = new Intent (AddItemActivity.this, MainActivity.class);
        intentToMainActivity.putExtra("intentID", "intent_from_AddItemActivity");
        intentToMainActivity.putExtra("addedFoodName", foodName.getText().toString());
        intentToMainActivity.putExtra("addedFoodPrice", Integer.parseInt(foodPrice.getText().toString()));

        Singleton singleton = Singleton.getInstance();
        singleton.setChosenFoodImage(((BitmapDrawable) imageView.getDrawable()).getBitmap());

        // The "setFlags" method with "Intent.FLAG_ACTIVITY_CLEAR_TOP" closes all the previous activities
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToMainActivity);


    }
}