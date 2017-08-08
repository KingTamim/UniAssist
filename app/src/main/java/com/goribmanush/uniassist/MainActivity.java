package com.goribmanush.uniassist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button logInBtn;
    private Button regBtn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView textViewSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing the firebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Activity_Second.class));
            finish();
        }


        //Initializing progress dialog
        progressDialog = new ProgressDialog(this);


        //Initializing user
        logInBtn = (Button) findViewById(R.id.buttonLogIn);
        regBtn = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.etl_email);
        editTextPassword = (EditText) findViewById(R.id.etl_password);
        textViewSignIn = (TextView) findViewById(R.id.tvl_adminSignIn);



        //Initializing the Click listener Interface with the button object
        logInBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);



        textViewSignIn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adminLogIn();
                return false;
            }
        });


    }

    private void adminLogIn() {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){

            Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Making the progress Dialog to show the delay animation
        progressDialog.setMessage("Loading Admin.");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this, AdminPanel.class));
                            finish();

                        }else{
                            Toast.makeText(MainActivity.this, "Something went wrong, Try Again", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();

                    }
                });


    }


    @Override
    public void onClick(View v) {

        if (v == logInBtn){

            userLogIn();
            //startActivity(new Intent(this, Activity_Second.class));

        }

        if (v == regBtn){
            startActivity(new Intent(this, Email_Registration.class));
            finish();
        }

    }

    private void userLogIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){

            Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        //Making the progress Dialog to show the delay animation
        progressDialog.setMessage("Logging in.");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "LogIn successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, Activity_Second.class));
                            finish();


                        }else{
                            Toast.makeText(MainActivity.this, "Something went wrong, Try Again", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();

                    }
                });


    }
}