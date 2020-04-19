package com.stroud.hawkbay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stroud.hawkbay.model.Listing;

public class ListingCreateActivity extends AppCompatActivity {
    private EditText mNameField;
    private EditText mDescField;
    private EditText mPriceField;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_create);
        mFirestore = FirebaseFirestore.getInstance();

    }

    public void addListing(View view) {

        CollectionReference listings = mFirestore.collection("listings");

        mNameField = findViewById(R.id.name_field);
        mDescField = findViewById(R.id.description_field);
        mPriceField = findViewById(R.id.price_field);

        String name = mNameField.getText().toString();
        String desc = mDescField.getText().toString();
        String price = mPriceField.getText().toString();

        Listing listing = new Listing(name, desc, price);

        listings.add(listing);

        finish();
    }
}
