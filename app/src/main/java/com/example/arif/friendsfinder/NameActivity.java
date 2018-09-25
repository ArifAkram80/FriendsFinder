package com.example.arif.friendsfinder;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {

    String Email, Password, Name;
    EditText Ed_name;
    CircleImageView circleImageView;
    Uri resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Ed_name = findViewById(R.id.editText5);
        circleImageView = findViewById(R.id.profilepic);
        Intent myIntend = getIntent();
        if(myIntend!=null){
            Email = myIntend.getStringExtra("email");
            Password = myIntend.getStringExtra("password");
        }
    }

    public  void genarateCode(View view){
        Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();
        int n = 100000 + r.nextInt(900000);
        
        String code = String.valueOf(n);

        if(resultUri != null)
        {
            //Send The name email passsword and date
            Intent  intent = new Intent(NameActivity.this, InviteCodeActivity.class);
            intent.putExtra("name", Ed_name.getText().toString());
            intent.putExtra("email", Email);
            intent.putExtra("password", Password);
            intent.putExtra("date", date);
            intent.putExtra("isSharing", "False");
            intent.putExtra("code", code);
            intent.putExtra("imageUri", resultUri);

            startActivity(intent);
            finish();

        }
        else{
            Toast.makeText(this, "Please Choice a image", Toast.LENGTH_SHORT).show();
        }


    }
        public void selectImage(View v){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("Image/*");
        startActivityForResult(i,12);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 12 && resultCode == RESULT_OK && data!=null){
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();
                 circleImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}