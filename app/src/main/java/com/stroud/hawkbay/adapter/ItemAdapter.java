
package com.stroud.hawkbay.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.stroud.hawkbay.R;
import com.stroud.hawkbay.model.Listing;


public class ItemAdapter extends FirestoreAdapter<ItemAdapter.ViewHolder> {

    public interface OnItemSelectedListener {

        void onListingSelected(DocumentSnapshot listing);

    }

    private OnItemSelectedListener mListener;

    public ItemAdapter(Query query, OnItemSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_listing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
        TextView priceView;
        TextView descView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameView = itemView.findViewById(R.id.item_name);
            priceView = itemView.findViewById(R.id.item_price);
            descView = itemView.findViewById(R.id.item_description);

        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnItemSelectedListener listener) {

            Listing listing = snapshot.toObject(Listing.class);
            Resources resources = itemView.getResources();

            nameView.setText(listing.getName());
            priceView.setText("$" + listing.getPrice());
            descView.setText(listing.getDescription());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (listener != null) {
                                                    listener.onListingSelected(snapshot);
                                                }
                                            }
                                        }
            );
        }

    }
}