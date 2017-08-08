package com.goribmanush.uniassist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class Fragment_Profile extends Fragment {

    public Fragment_Profile() {
        // Required empty public constructor
    }

    private static TextView textViewName;
    private static TextView textViewID;
    private static TextView textViewCGPA;
    private ImageView profileImageView;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUser;
    private DatabaseReference databaseReferenceCources;
    private TextView tvsub1;
    private TextView tvsub2;
    private TextView tvsub3;
    private TextView tvsub4;
    private TextView tvsub5;






    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //getActivity().setTitle("Profile");



        //String id1 = databaseReferenceUser.push().getKey();
        //String id2 = databaseReferenceCources.push().getKey();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        //final String id = firebaseAuth.getCurrentUser().getUid();


        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser.getUid());
        databaseReferenceCources = FirebaseDatabase.getInstance().getReference().child("Cources").child(currentUser.getUid());



        textViewName =  (TextView) view.findViewById(R.id.ttv_userName);
        textViewID = (TextView) view.findViewById(R.id.ttv_userid);
        textViewCGPA = (TextView) view.findViewById(R.id.ttv_userCGPA);
        profileImageView = (ImageView) view.findViewById(R.id.imageView2);


        //Long way for ez work :(
        tvsub1 = (TextView) view.findViewById(R.id.tv_subject1);
        tvsub2 = (TextView) view.findViewById(R.id.tv_subject2);
        tvsub3 = (TextView) view.findViewById(R.id.tv_subject3);
        tvsub4 = (TextView) view.findViewById(R.id.tv_subject4);
        tvsub5 = (TextView) view.findViewById(R.id.tv_subject5);


        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if (dataSnapshot.hasChild("name")){
                    textViewName.setText(dataSnapshot.child("name").getValue().toString());
                    textViewID.setText(dataSnapshot.child("sid").getValue().toString());
                    textViewCGPA.setText(dataSnapshot.child("cgpa").getValue().toString());



                    //Context context = null;
                    String link =dataSnapshot.child("profile_picture").getValue().toString();
                    //String link2 = "https://firebasestorage.googleapis.com/v0/b/uniassist-3857e.appspot.com/o/profileImages%2Fcropped-1137247244.jpg?alt=media&token=3bc5b863-01ac-4dc6-86ab-d9a760bd6174";
                    Picasso.with(getActivity().getBaseContext()).load(link).into(profileImageView);



                }else{
                    Toast.makeText(getActivity().getBaseContext(), "You no saved Information!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity().getBaseContext(), "Input all your information first.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity().getBaseContext(),UserInfoRegistration.class));
                }

                    //Different style to fetch data
                    /*for (DataSnapshot userInfo : dataSnapshot.getChildren()) {

                        UserInformation userInformation = userInfo.getValue(UserInformation.class);

                        textViewName.setText(userInformation.getName());
                        textViewID.setText(userInformation.getSid());
                        textViewCGPA.setText(userInformation.getCgpa());

                    }*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReferenceCources.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    tvsub1.setText(dataSnapshot.child("subject1").getValue().toString());
                    tvsub2.setText(dataSnapshot.child("subject2").getValue().toString());
                    tvsub3.setText(dataSnapshot.child("subject3").getValue().toString());
                    tvsub4.setText(dataSnapshot.child("subject4").getValue().toString());
                    tvsub5.setText(dataSnapshot.child("subject5").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return view;

    }


}
