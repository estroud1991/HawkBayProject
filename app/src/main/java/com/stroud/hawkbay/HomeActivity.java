package com.stroud.hawkbay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.stroud.hawkbay.adapter.ItemAdapter;

public class HomeActivity extends AppCompatActivity implements
        ItemAdapter.OnItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private RecyclerView mListingsRecycler;
    private ViewGroup mEmptyView;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                // Show/hide content if the query returns empty.
                    mListingsRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }


            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        mListingsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mListingsRecycler.setAdapter(mAdapter);

    }

    public void newListing(View view) {
        Intent intent = new Intent(HomeActivity.this, ListingCreateActivity.class);
        startActivity(intent);
    }

    private void initFirestore() {

    }


    @Override
    public void onItemSelected(DocumentSnapshot item) {

    }
}
