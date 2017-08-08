package com.goribmanush.uniassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAddNews;
    private  Button buttonAddEvent;
    private Button buttonLogout;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        firebaseAuth = FirebaseAuth.getInstance();


        buttonAddNews = (Button) findViewById(R.id.adminButtonNews);
         buttonAddEvent = (Button) findViewById(R.id.adminButtonEvent);
        buttonLogout = (Button) findViewById(R.id.adminButtonLogout);

        buttonAddNews.setOnClickListener(this);
        buttonAddEvent.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if(v == buttonAddNews ){
            startActivity(new Intent(AdminPanel.this, AdminAddNews.class));

        }

        if(v == buttonAddEvent ){
            startActivity(new Intent(AdminPanel.this, AdminAddEvent.class));

        }
        if(v == buttonLogout ){

            firebaseAuth.signOut();
            startActivity(new Intent(AdminPanel.this, MainActivity.class));

        }



    }
}
