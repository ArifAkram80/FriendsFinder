package com.example.arif.friendsfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser User;
    PermissionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        User = auth.getCurrentUser();


        if(User == null)
        {
            manager = new PermissionManager() {
            };
            manager.checkAndRequestPermissions(this);
            setContentView(R.layout.activity_main);
        }
        else{
            startActivity(new Intent(MainActivity.this, MyNavigation.class));
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        manager.checkResult(requestCode,permissions,grantResults);
        ArrayList<String> denied_permission = manager.getStatus().get(0).denied;
        
        if(denied_permission.isEmpty()){
            Toast.makeText(this, "Permission Enabled", Toast.LENGTH_SHORT).show();
        }
        
    }

    public void goToLogin(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    public void goToSignUp(View v){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    //permission

}
