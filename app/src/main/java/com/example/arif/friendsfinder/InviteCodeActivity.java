package com.example.arif.friendsfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InviteCodeActivity extends AppCompatActivity {

    String email, name,password,date,code,isSharing;
    Uri ImageUri;
    ProgressDialog progressDialog;

    TextView textView;


    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    String UserID;
    String download_img_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        Intent intent = getIntent();
        progressDialog = new ProgressDialog(this);
        textView = findViewById(R.id.textView);
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_images");



        if(intent!=null){
            password = intent.getStringExtra("password");
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            date= intent.getStringExtra("date");
            isSharing = intent.getStringExtra("isSharing");
            code  = intent.getStringExtra("code");
            ImageUri  = intent.getParcelableExtra("imageUri");
        }
        textView.setText(code);
    }


    public void registerUser(View V){
        progressDialog.setMessage("Registering Please Wait");
        progressDialog.show();


        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //insert data

                    CreateUser createUser= new CreateUser(name,email,password,code,"false","na","na","na");
                    firebaseUser = auth.getCurrentUser();
                    UserID = firebaseUser.getUid();

                    databaseReference.child(UserID).setValue(createUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                final StorageReference sr = storageReference.child((UserID) + ".jpg");

                                sr.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> pic_task) {
                                        if(pic_task.isSuccessful()){



                                            sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    download_img_path = uri.toString();
                                                    Log.i("img", download_img_path);  // this logi shows na original value
                                                }
                                            });


                                            databaseReference.child(UserID).child("imgUri").setValue(download_img_path) /// if i use my name it works , dont work with storage link


                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                progressDialog.dismiss();
                                                                Toast.makeText(InviteCodeActivity.this, "User Registred Successfully", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(InviteCodeActivity.this, MyNavigation.class));
                                                            }
                                                            else{
                                                                progressDialog.dismiss();
                                                                Toast.makeText(InviteCodeActivity.this, "Could not register user", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });


                            }

                        }
                    });

                }
            }
        });

    }

}
