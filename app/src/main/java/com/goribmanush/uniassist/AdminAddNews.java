package com.goribmanush.uniassist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AdminAddNews extends AppCompatActivity {

    private ImageButton buttonNewsAddImage;
    private Button buttonNewsSubmit;
    private EditText editTextNewsTitle;
    private EditText editTextNewsDescription;
    private StorageReference newsStorage;
    private DatabaseReference newsDatabaseReference;
    private static final int GALLERY_REQUEST= 2;
    private Uri imageUri;
    private Uri resultUri = null;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_news);

        newsStorage = FirebaseStorage.getInstance().getReference();
        newsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("News");



        buttonNewsAddImage = (ImageButton) findViewById(R.id.btn_newsAddImage);
        buttonNewsSubmit = (Button)findViewById(R.id.btn_newsSubmit);
        editTextNewsTitle = (EditText) findViewById(R.id.et_newsAddTitle);
        editTextNewsDescription = (EditText) findViewById(R.id.et_newsAddDescription);



        progressDialog = new ProgressDialog(this);



        buttonNewsAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, GALLERY_REQUEST);


            }
        });


        buttonNewsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Posting the News Contents");
                progressDialog.show();
                postNews();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK ){

            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(3,2)
                    .start(this);

           /* StorageReference filepath = newsStorage.child("Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(AdminAddNews.this, "Image uploaded...", Toast.LENGTH_SHORT).show();

                }
            });*/

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                buttonNewsAddImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }

    }

    private void postNews() {


        final String titleNews = editTextNewsTitle.getText().toString().trim();
        final String descriptionNews = editTextNewsDescription.getText().toString().trim();

        if (titleNews.isEmpty()){
            Toast.makeText(this, "A Title is required.", Toast.LENGTH_SHORT).show();
        }
        if (descriptionNews.isEmpty()){
            Toast.makeText(this, "Please add some Description.", Toast.LENGTH_SHORT).show();
        }
        if (!TextUtils.isEmpty(titleNews) && !TextUtils.isEmpty(descriptionNews) && resultUri!=null ){

            StorageReference filepath = newsStorage.child("NewsImages").child(resultUri.getLastPathSegment());

            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newNews = newsDatabaseReference.push();
                    newNews.child("title").setValue(titleNews);
                    newNews.child("description").setValue(descriptionNews);
                    newNews.child("Image").setValue(downloadUrl.toString());
                    Toast.makeText(AdminAddNews.this, "News Published Successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminAddNews.this,AdminPanel.class));
                    finish();
                    progressDialog.dismiss();

                }
            });



        }






    }




}
