/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.stroud.hawkbay;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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

    private ImageView mImageView;
    private TextView mNameView;
    private TextView mDescView;
    private TextView mPriceView;
    private TextView mEmailView;

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

}
