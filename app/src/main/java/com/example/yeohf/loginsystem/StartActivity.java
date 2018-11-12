package com.example.yeohf.loginsystem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "Start Activity";
    private EditText name;
    private EditText password;
    private TextView info;
    private TextView userRegistration;
    private Button login;
    private int counter=5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d(TAG,"In Start Activity");

        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        FirebaseUser user=firebaseAuth.getCurrentUser();


        // USER HAS TO SIGN IN ALWAYS FOR NOW.
        if(user!=null){
            firebaseAuth.signOut();
        }


        name = (EditText) findViewById(R.id.etUsername);
        password = (EditText)  findViewById(R.id.etPassword);
        info = (TextView) findViewById(R.id.tvTries);
        login = (Button) findViewById(R.id.btnRegister);
        userRegistration= (TextView)findViewById(R.id.tvRegister);
        forgotpassword= (TextView)findViewById(R.id.tvForgotpass);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(),password.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegistrationActivity.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,PasswordActivity.class));
            }
        });



    }

    private void validate (String userName, String userPassword) {

        progressDialog.setMessage("Validating!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(StartActivity.this, "Login Success! ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(StartActivity.this, "Login Failed ", Toast.LENGTH_SHORT).show();
                    counter--;
                    info.setText("Number of tries left:" + counter);
                    if (counter == 0) {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }
}
