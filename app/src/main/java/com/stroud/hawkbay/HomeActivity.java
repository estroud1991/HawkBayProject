package com.stroud.hawkbay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.stroud.hawkbay.adapter.ItemAdapter;

public class HomeActivity extends AppCompatActivity implements
        ItemAdapter.OnItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private static final String EXTRA_USERS = "" ;

    private RecyclerView mListingsRecycler;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String userEmail = getIntent().getExtras().getString(EXTRA_USERS);

        Toast toast=Toast.makeText(getApplicationContext(),"Welcome back " + userEmail + "!",Toast.LENGTH_SHORT);
        toast.show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mListingsRecycler = findViewById(R.id.recycler_listings);

        FirebaseFirestore.setLoggingEnabled(true);

        mFirestore = FirebaseFirestore.getInstance();

        mQuery = mFirestore.collection("listings")
                .orderBy("name", Query.Direction.DESCENDING);

        mAdapter = new ItemAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                    mListingsRecycler.setVisibility(View.VISIBLE);
                }


            @Override
            protected void onError(FirebaseFirestoreException e) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        mListingsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mListingsRecycler.setAdapter(mAdapter);


    }
    @Override
    public void onStart() {
        super.onStart();

        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public void onListingSelected(DocumentSnapshot listing) {
        Intent intent = new Intent(this, ListingActivity.class);
        intent.putExtra(ListingActivity.LISTING_ID, listing.getId());

        startActivity(intent);
    }

    public void newListing(View view) {
        String userEmail = getIntent().getExtras().getString(EXTRA_USERS);
        Intent intent = new Intent(HomeActivity.this, ListingCreateActivity.class);
        intent.putExtra(ListingCreateActivity.EXTRA_EMAIL, userEmail);
        startActivity(intent);
    }



}
