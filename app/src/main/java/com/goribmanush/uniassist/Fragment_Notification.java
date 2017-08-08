package com.goribmanush.uniassist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//Finally working after Cleaning up the extra codes of Fragment classes!
public class Fragment_Notification extends Fragment {

    private RecyclerView eventList;
    private DatabaseReference databaseReferenceEvents;


    public Fragment_Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        databaseReferenceEvents = FirebaseDatabase.getInstance().getReference().child("Events");

        eventList = (RecyclerView) view.findViewById(R.id.eventRecyclerView);
        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<FetchEvents, EventsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FetchEvents, EventsViewHolder>(

                FetchEvents.class,
                R.layout.custom_view_events,
                EventsViewHolder.class,
                databaseReferenceEvents
        ) {
            @Override
            protected void populateViewHolder(EventsViewHolder viewHolder, FetchEvents model, int position) {

                viewHolder.setEventTitle(model.getTitle());
                viewHolder.setEventDescription(model.getDescription());

            }
        };

        eventList.setAdapter(firebaseRecyclerAdapter);



    }


    public static class EventsViewHolder extends RecyclerView.ViewHolder{
        View eventView;

        public EventsViewHolder(View itemView) {
            super(itemView);
            eventView = itemView;
        }

        public void setEventTitle(String title){
            TextView postEventTitle = (TextView) eventView.findViewById(R.id.textViewEventTitle);
            postEventTitle.setText(title);
        }
        public void setEventDescription(String description){
            TextView postEventDescription = (TextView) eventView.findViewById(R.id.textViewEventDescription);
            postEventDescription.setText(description);
        }


    }
}
