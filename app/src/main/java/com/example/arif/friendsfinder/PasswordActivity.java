package com.example.arif.friendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    String email;
    EditText e4_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Intent intent = getIntent();
        e4_Password = findViewById(R.id.editText4);
        if(intent !=null)
        {
            email = intent.getStringExtra("email");
        }
    }

    public void goToNamePickActivity(View v){
        if(e4_Password.getText().toString().length() > 5){
            Intent myIntend = new Intent(PasswordActivity.this, NameActivity.class);
            myIntend.putExtra("email",email);
            myIntend.putExtra("password",e4_Password.getText().toString());
            startActivity(myIntend);
            finish();
        }
        else{
            Toast.makeText(this, "Password Should be more than 6 characters", Toast.LENGTH_SHORT).show();
        }

    }
}
