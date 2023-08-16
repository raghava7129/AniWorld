package com.example.raghavateam;

import static com.example.raghavateam.sharespref.Img;
import static com.example.raghavateam.sharespref.shared;
import static com.example.raghavateam.sharespref.userName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    String fuN,eM,mN;
    TextView fuNd,EMD,MND;
    Button b;
    ImageButton G;
    CircleImageView iv;
    private final int  IMAGE_REQUEST_ID=1;
    private Uri u;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser fuser;
    private StorageTask uploadTask;
    String mUri;
    private ProgressBar imageLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeViews();

        fuser=FirebaseAuth.getInstance().getCurrentUser();

        loadImage();


        ActionBar ab1=getSupportActionBar();
        if(ab1!=null){
            ab1.setTitle("My Profile");
            ab1.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        }

        getLoginData();
        sharespref.set_is_loggedIn(profile.this,true);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile.this,Home_page.class);
                startActivity(i);
            }
        });

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent il=new Intent();
                il.setType("image/*");
                il.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(il,"Select Picture"),IMAGE_REQUEST_ID);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST_ID && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            u=data.getData();
            if(uploadTask !=null && uploadTask.isInProgress()){
                Toast.makeText(profile.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImg();
                imageLoadingProgress.setVisibility(View.GONE);
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=this.getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImg() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if (u != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + '.' + getFileExtension(u));
            uploadTask = fileReference.putFile(u);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        mUri = downloadUri.toString();
                        DatabaseReference reference;
                        reference = FirebaseDatabase.getInstance().getReference("User_Data").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imgURL", mUri);
                        reference.updateChildren(map);
                        try {
                            Glide.with(profile.this).load(mUri).into(iv);
                        } catch (Exception e) {
                            Log.e("GLIDE_ERROR", "Error loading image with Glide", e);
                        }
                        progressDialog.dismiss();
                        imageLoadingProgress.setVisibility(View.VISIBLE);
                    } else {
                        iv.setImageResource(R.drawable.ic_launcher_background);
                        Toast.makeText(profile.this, "Failed !", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        imageLoadingProgress.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    iv.setImageResource(R.drawable.ic_launcher_background);
                    Toast.makeText(profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    imageLoadingProgress.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(profile.this, "No image selected", Toast.LENGTH_SHORT).show();
            iv.setImageResource(R.drawable.ic_launcher_background);
            progressDialog.dismiss();
            imageLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    private void loadImage() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_Data").child(userID);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String imageUrl = dataSnapshot.child("imgURL").getValue(String.class);
                    if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                        Glide.with(profile.this).load(imageUrl).into(iv);
                    }
                    else{
                        iv.setImageResource(R.drawable.ic_launcher_background);
                    }
                    imageLoadingProgress.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error here
                    iv.setImageResource(R.drawable.ic_launcher_background);
                    Log.e("FIREBASE_ERROR", databaseError.getMessage());
                }
            });
        }
    }

    public void initializeViews(){
        fuNd=findViewById(R.id.Naam);
        EMD=findViewById(R.id.mai);
        MND=findViewById(R.id.MOBN);
        b=findViewById(R.id.button4);
        G=findViewById(R.id.gal);
        iv=findViewById(R.id.setimg);
        imageLoadingProgress = findViewById(R.id.imageLoadingProgress);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference("uploads");
    }

    private void getLoginData(){
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = current_user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_Data").child(user_id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_name=snapshot.child("username").getValue(String.class);
                String phno=snapshot.child("Phone_no").getValue(String.class);
                String email=snapshot.child("email").getValue(String.class);
                EMD.setText(email);
                MND.setText(phno);
                fuNd.setText(user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}