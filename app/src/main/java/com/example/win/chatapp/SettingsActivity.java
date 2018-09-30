package com.example.win.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity {



    private final static int Gallery_pick = 1;
    private CircleImageView settingDisplayImage;
    private TextView settingDisplayName;
    private TextView settingDisplayStatus;
    private Button settingChangeImage_button;
    private Button settingChangeStatus_button;
    private StorageReference storeProfileImageStorageRef;

    private FirebaseAuth mAuth;
    private DatabaseReference getUserDataReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        String online_user = mAuth.getCurrentUser().getUid();
        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user);
        storeProfileImageStorageRef = FirebaseStorage.getInstance().getReference().child("profile_images");

        settingDisplayImage = (CircleImageView)findViewById(R.id.settings_profile_image);
        settingDisplayName = (TextView)findViewById(R.id.settings_username);
        settingDisplayStatus = (TextView)findViewById(R.id.settings_userstatus);
        settingChangeImage_button = (Button)findViewById(R.id.settings_changeimage_button);
        settingChangeStatus_button = (Button)findViewById(R.id.settings_changestatus_button);

        getUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("User_Name").getValue().toString();
                String status = dataSnapshot.child("User_Status").getValue().toString();
                String image = dataSnapshot.child("User_Image").getValue().toString();
                String thumb_image = dataSnapshot.child("User_thumb_image").getValue().toString();

                settingDisplayName.setText(name);
                settingDisplayStatus.setText(status);
                Picasso.get().load(image).into(settingDisplayImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        settingChangeImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_pick);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_pick && resultCode == RESULT_OK && data != null)
        {
            Uri imageUri = data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String user_id = mAuth.getCurrentUser().getUid();
                StorageReference filepath = storeProfileImageStorageRef.child(user_id+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SettingsActivity.this, "save your profile image to firedata base", Toast.LENGTH_LONG).show();
                            String downloadurl = task.getResult().getDownloadUrl().toString();
                            getUserDataReference.child("User_Image").setValue(downloadurl).
                               addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       Toast.makeText(SettingsActivity.this, "upload image successfully", Toast.LENGTH_LONG).show();

                                   }
                               });
                        }
                        else
                        {
                            Toast.makeText(SettingsActivity.this, "Error occuered, while upload profile image", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
