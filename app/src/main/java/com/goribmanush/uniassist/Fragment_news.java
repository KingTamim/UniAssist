package com.goribmanush.uniassist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Fragment_news extends Fragment {


    private RecyclerView newsList;
    private DatabaseReference databaseReference;




    public Fragment_news() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        //getActivity().setTitle("News");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("News");
        //databaseReference.keepSynced(true);

        newsList = (RecyclerView) view.findViewById(R.id.newsRecyclerView);
        newsList.setHasFixedSize(true);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<FetchNews, NewsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FetchNews, NewsViewHolder>(

                FetchNews.class,
                R.layout.custom_view_news,
                NewsViewHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(NewsViewHolder viewHolder, FetchNews model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(getActivity().getApplicationContext(),model.getImage());

            }
        };

        newsList.setAdapter(firebaseRecyclerAdapter);

    }

    //Ending onStart

    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        View newsView;


        public NewsViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
        }
        public void setTitle (String title){

            TextView postTitle = (TextView) newsView.findViewById(R.id.postTitle);
            postTitle.setText(title);

        }
        public void setDescription (String description){

            TextView postDescription = (TextView) newsView.findViewById(R.id.postDescription);
            postDescription.setText(description);

        }
        public void setImage (Context context, String link){

            ImageView postImage = (ImageView) newsView.findViewById(R.id.postImage);
            Picasso.with(context).load(link).into(postImage);


        }


    }




}
