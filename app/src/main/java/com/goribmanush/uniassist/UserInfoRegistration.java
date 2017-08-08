package com.goribmanush.uniassist;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UserInfoRegistration extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName, editTextId, editTextBatch;
    private Spinner spinnerDepartment, spinnerSemester;
    private Button buttonSubmit;
    private DatabaseReference databaseReferance;
    private DatabaseReference databaseAdditionReferance;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storeProfileImage;
    private StorageReference profileImageStorage;
    private String Cgpa;
    private ImageButton imageButtonProfileImage;
    private Uri resultUri = null;
    private static final int GALLERY_REQUEST = 2;
    FirebaseUser firebaseUser;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_registration);

        //Initializing databasereferance object;
        databaseReferance = FirebaseDatabase.getInstance().getReference().child("Students");
        databaseAdditionReferance = FirebaseDatabase.getInstance().getReference().child("Cources");
        profileImageStorage = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();



        //Initializing the layout resources with objects
        editTextName = (EditText) findViewById(R.id.eti_name);
        editTextId = (EditText) findViewById(R.id.eti_sid);
        editTextBatch = (EditText) findViewById(R.id.eti_batch);
        spinnerDepartment = (Spinner) findViewById(R.id.spinner_department);
        spinnerSemester = (Spinner) findViewById(R.id.spinner_semester);
        buttonSubmit = (Button) findViewById(R.id.btni_submit);
        imageButtonProfileImage = (ImageButton) findViewById(R.id.imageButtonProfile);


        //Initializing buttonObject with listener
        buttonSubmit.setOnClickListener(this);

        imageButtonProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });




    }

    public void submitRegistration(){

        //Setting Course Objects
        String sub_1 ="Pending...";
        String sub_2 ="Pending...";
        String sub_3 ="Pending...";
        String sub_4 ="Pending...";
        String sub_5 ="Pending...";


        String name = editTextName.getText().toString().trim();
        String sid = editTextId.getText().toString().trim();
        String department = spinnerDepartment.getSelectedItem().toString().trim();
        String semester = spinnerSemester.getSelectedItem().toString().trim();
        Integer batch = Integer.valueOf(editTextBatch.getText().toString().trim());

        final UserInformation userInformation = new UserInformation(name,sid,department,semester,batch);
        final CourcesData courcesData = new CourcesData(sub_1,sub_2,sub_3,sub_4,sub_5);


        final FirebaseUser user = firebaseAuth.getCurrentUser();


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sid) && batch != null && resultUri !=null ){

            StorageReference imageStorage = profileImageStorage.child("profileImages").child(resultUri.getLastPathSegment());

            imageStorage.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    databaseReferance.child(user.getUid()).setValue(userInformation);
                    databaseAdditionReferance.child(user.getUid()).setValue(courcesData);

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    databaseReferance.child(user.getUid()).child("profile_picture").setValue(downloadUrl.toString());

                    Toast.makeText(UserInfoRegistration.this, "Information Saved.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(UserInfoRegistration.this, "Your Marks and Other info will update soon.", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(UserInfoRegistration.this,MainActivity.class));
                    finish();



                }
            });

        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                imageButtonProfileImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }

    @Override
    public void onClick(View v) {

        if (v == buttonSubmit) {
            submitRegistration();

        }

    }
}
