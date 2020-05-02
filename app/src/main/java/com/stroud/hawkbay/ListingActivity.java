package com.stroud.hawkbay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.stroud.hawkbay.model.Listing;


public class ListingActivity extends AppCompatActivity implements
        EventListener<DocumentSnapshot>{

    public static final String LISTING_ID = "";
    private static final String TAG = "ListingDetail";
    private static final String EXTRA_EMAIL = "";

    private ImageView mImageView;
    private TextView mNameView;
    private TextView mDescView;
    private TextView mPriceView;
    private TextView mEmailView;
    private boolean delete = false;

    private FirebaseFirestore mFirestore;
    private DocumentReference mListingRef;
    private ListenerRegistration mListingRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        mImageView = findViewById(R.id.listing_image);
        mNameView = findViewById(R.id.listing_name);
        mPriceView = findViewById(R.id.listing_price);
        mDescView = findViewById(R.id.listing_desc);
        mEmailView = findViewById(R.id.seller_email);

        String listingId = getIntent().getExtras().getString(LISTING_ID);
        if (listingId == null) {
            throw new IllegalArgumentException("Must pass extra " + LISTING_ID);
        }

        mFirestore = FirebaseFirestore.getInstance();

        mListingRef = mFirestore.collection("listings").document(listingId);


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


        mNameView.setText(listing.getName());
        mEmailView.setText(listing.getSellerEmail());
        mDescView.setText(listing.getDescription());
        mPriceView.setText("$"+listing.getPrice());
        mEmailView.setText(listing.getSellerEmail());

    }
    public void editStart(View view){
        String userEmail = getIntent().getExtras().getString(EXTRA_EMAIL);
        Intent intent = new Intent(this, ListingEditActivity.class);
        intent.putExtra(ListingActivity.LISTING_ID, mListingRef.getId());
        intent.putExtra(ListingActivity.EXTRA_EMAIL, userEmail);
        startActivity(intent);
    }

    public void deleteListing(View view){
        Toast toast=Toast.makeText(getApplicationContext(),"Listing Deleted!",Toast.LENGTH_SHORT);
        delete = true;
        toast.show();
        mListingRef.delete();
    }

}
