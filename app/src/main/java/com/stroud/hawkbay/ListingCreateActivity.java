package com.stroud.hawkbay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stroud.hawkbay.model.Listing;


public class ListingCreateActivity extends AppCompatActivity {
    public static final String EXTRA_EMAIL = "";
    private EditText mNameField;
    private EditText mDescField;
    private EditText mPriceField;
    private FirebaseFirestore mFirestore;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_create);
        mFirestore = FirebaseFirestore.getInstance();
        mNameField = findViewById(R.id.name_field);
        mDescField = findViewById(R.id.description_field);
        mPriceField = findViewById(R.id.price_field);
        mButton = findViewById(R.id.button);


    }

    public void addListing(View view) {
        if (!validateForm()) {
            Toast toast=Toast.makeText(getApplicationContext(),"All fields are required!",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Toast toast=Toast.makeText(getApplicationContext(),"Post Successful!",Toast.LENGTH_SHORT);

        String userEmail = getIntent().getExtras().getString(EXTRA_EMAIL);

        CollectionReference listings = mFirestore.collection("listings");

        mNameField = findViewById(R.id.name_field);
        mDescField = findViewById(R.id.description_field);
        mPriceField = findViewById(R.id.price_field);

        String name = mNameField.getText().toString();
        String desc = mDescField.getText().toString();
        String price = mPriceField.getText().toString();

        Listing listing = new Listing(name, desc, price, userEmail);

        listings.add(listing);
        toast.show();

        finish();
    }
    private boolean validateForm() {

        boolean valid = true;

        String name = mNameField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameField.setError("Required.");
            valid = false;
        } else {
            mNameField.setError(null);
        }

        String desc = mDescField.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            mDescField.setError("Required.");
            valid = false;
        } else {
            mDescField.setError(null);
        }

        String price = mPriceField.getText().toString();
        if (TextUtils.isEmpty(price)) {
            mPriceField.setError("Required.");
            valid = false;
        } else {
            mPriceField.setError(null);
        }

        return valid;
    }
}
