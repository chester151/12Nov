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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Entity.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText username;
    private EditText userPassword;
    private EditText userEmail;
    private EditText userContact;
    private ImageView registerImage;

    String name,email,password,contact;

    private Button regButton;
    private TextView userLogin;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private static final String TAG= "Reg Activity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.d(TAG,"in registration activity");
        setupUIViews();
        progressDialog= new ProgressDialog(this);

        firebaseAuth = firebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    // upload to database
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressDialog.setTitle("Uploading to Database");
                                progressDialog.show();
                                Toast.makeText(RegistrationActivity.this, "Registration successful!, Upload to Database complete", Toast.LENGTH_SHORT).show();
                                Log.d(TAG,"Create User Success");
                                progressDialog.cancel();
                                sendUserdata();

                                startActivity(new Intent(RegistrationActivity.this,StartActivity.class ));
                            }
                            else{
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Log.e("LoginActivity", "Failed Registration", e);
                                Log.d(TAG,"Create user failed");
                                Toast.makeText(RegistrationActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                };
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, StartActivity.class));
            }
        });
    }
    private void setupUIViews() {
        username=(EditText)findViewById(R.id.etName);
        userPassword=(EditText)findViewById(R.id.etPassword);
        userEmail=(EditText)findViewById(R.id.etEmail);
        regButton= (Button) findViewById(R.id.btnRegister);
        userLogin=(TextView)findViewById(R.id.tvUserlog);
        userContact=(EditText)findViewById(R.id.etContact);
        registerImage=(ImageView)findViewById(R.id.ivRegisterimage);
    }
    private boolean validate(){
        boolean result=false;
        name= username.getText().toString();
        email= userEmail.getText().toString();
        password= userPassword.getText().toString();
        contact=userContact.getText().toString();

        if(name.isEmpty()||password.isEmpty()||email.isEmpty()||contact.isEmpty()){
            Toast.makeText(RegistrationActivity.this, "Please enter all details: ",Toast.LENGTH_LONG).show();
        }{
            result=true;
        }
        return result;
    }

    private void sendUserdata(){
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference("Users");
        UserProfile userProfile= new UserProfile(firebaseAuth.getUid(),name,contact,email);
        myRef.child(firebaseAuth.getUid()).setValue(userProfile);

    }
}
