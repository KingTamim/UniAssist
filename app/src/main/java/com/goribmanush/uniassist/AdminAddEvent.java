package com.goribmanush.uniassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddEvent extends AppCompatActivity {

    private EditText editTextAddEventTitle;
    private EditText editTextAddEventDescription;
    private Button buttonSubmitEvent;
    DatabaseReference databaseReferanceEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_event);


        databaseReferanceEvents = FirebaseDatabase.getInstance().getReference().child("Events");


        editTextAddEventTitle = (EditText) findViewById(R.id.et_eventTitle);
        editTextAddEventDescription = (EditText) findViewById(R.id.et_eventDescription);
        buttonSubmitEvent = (Button) findViewById(R.id.buttonEventSubmit);



        buttonSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvent();
            }
        });


    }

    private void submitEvent() {

        String eventTitle = editTextAddEventTitle.getText().toString().trim();
        String eventDescription = editTextAddEventDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(eventTitle) && !TextUtils.isEmpty(eventDescription)){

            DatabaseReference newEvent = databaseReferanceEvents.push();
            newEvent.child("title").setValue(eventTitle);
            newEvent.child("description").setValue(eventDescription);
        }else{
            Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Event Uploaded and Published Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,AdminPanel.class));
        finish();




    }
}
