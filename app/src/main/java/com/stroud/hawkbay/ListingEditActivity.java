package com.stroud.hawkbay;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.stroud.hawkbay.model.Listing;


public class ListingEditActivity extends AppCompatActivity implements
        EventListener<DocumentSnapshot>
    {

    public static final String LISTING_ID = "";
    private static final String TAG = "";
    private EditText mNameField;
    private EditText mDescField;
    private EditText mPriceField;
    private FirebaseFirestore mFirestore;
    private DocumentReference mListingRef;
    private ListenerRegistration mListingRegistration;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_edit);
        mFirestore = FirebaseFirestore.getInstance();
        mNameField = findViewById(R.id.name_edit_field);
        mDescField = findViewById(R.id.description_edit_field);
        mPriceField = findViewById(R.id.price_edit_field);


        String listingId = getIntent().getExtras().getString(LISTING_ID);
        if (listingId == null) {
            throw new IllegalArgumentException("Must pass extra " + LISTING_ID);
        }

        mFirestore = FirebaseFirestore.getInstance();

        mListingRef = mFirestore.collection("listings").document(listingId);

    }

    public void updateListing(View view) {
        if (!validateForm()) {
            Toast toast=Toast.makeText(getApplicationContext(),"All fields are required!",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Toast toast=Toast.makeText(getApplicationContext(),"Update Successful!",Toast.LENGTH_SHORT);


        CollectionReference listings = mFirestore.collection("listings");

        mNameField = findViewById(R.id.name_edit_field);
        mDescField = findViewById(R.id.description_edit_field);
        mPriceField = findViewById(R.id.price_edit_field);

        String name = mNameField.getText().toString();
        String desc = mDescField.getText().toString();
        String price = mPriceField.getText().toString();

        mListingRef.update("name", name);
        mListingRef.update("price", price);
        mListingRef.update("description", desc);
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
    @Override
    public void onStart() {
        super.onStart();
        mListingRegistration = mListingRef.addSnapshotListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();

        if (mListingRegistration != null) {
            mListingRegistration.remove();
            mListingRegistration = null;
        }
    }


    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "listing:onEvent", e);
            return;
        }

        onListingLoaded(snapshot.toObject(Listing.class));
    }

    private void onListingLoaded(Listing listing) {
        mNameField.setText(listing.getName());
        mDescField.setText(listing.getDescription());
        mPriceField.setText(listing.getPrice());


    }

}

