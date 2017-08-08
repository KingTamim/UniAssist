package com.goribmanush.uniassist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Email_Registration extends AppCompatActivity implements View.OnClickListener {


    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email__registration);


        //Initializing firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        //Initializing the progressDialog for the internet data store delay.
        progressDialog = new ProgressDialog(this);

        //Initializing the layout resources with objects.
        buttonRegister = (Button) findViewById(R.id.btn_register);
        editTextEmail = (EditText) findViewById(R.id.etr_email);
        editTextPassword = (EditText) findViewById(R.id.etr_password);


        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister ){

            registerUser();
            Snackbar.make(v,"Registration Successful",Snackbar.LENGTH_LONG);


        }

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){

            Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
            return;

        }

        //When email and password both are entered showing the progress
        progressDialog.setMessage("Registering user data.");
        progressDialog.show();

        //function that actually registers user data.
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(Email_Registration.this, "Registration successful", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(Email_Registration.this, UserInfoRegistration.class));



                        }else{
                            Toast.makeText(Email_Registration.this, "Something went wrong, Try Again", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Email_Registration.this, "Check if you are already registered", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();



                    }
                });


    }
}
