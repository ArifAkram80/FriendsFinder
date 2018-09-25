package com.example.arif.friendsfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {
    EditText ReEmail;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ReEmail = findViewById(R.id.editText3);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
}

    public void goToPasswordActivity(View v){
        dialog.setMessage("Checking Email..");
        dialog.show();
        auth.fetchSignInMethodsForEmail(ReEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                if(task.isSuccessful())
                {
                    dialog.dismiss();
                    boolean check  = !task.getResult().getSignInMethods().isEmpty();
                    if(!check){
                        //Email Does not exist , So we can create this email with User
                        Intent MyIntend =  new Intent(RegisterActivity.this, PasswordActivity.class);
                        MyIntend.putExtra("email",ReEmail.getText().toString());
                        startActivity(MyIntend);

                        finish();

                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "This Email is already Registerred", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
