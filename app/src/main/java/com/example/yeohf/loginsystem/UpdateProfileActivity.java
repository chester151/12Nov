package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Entity.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText newuser,newcontact;
    private Button save;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myref;
    private UserProfile user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setUp();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myref= firebaseDatabase.getReference("Users").child((firebaseAuth.getUid()));
        final DatabaseReference databaseReference= myref;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userprofile=dataSnapshot.getValue(UserProfile.class);
                user=dataSnapshot.getValue(UserProfile.class);
                newuser.setText(userprofile.getUserName());
                newcontact.setText(userprofile.getUserContact());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfileActivity.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=newuser.getText().toString();
                String contact= newcontact.getText().toString();

                UserProfile userProfile= new UserProfile(user.getUserID(),name,contact, user.getUserEmail());

                databaseReference.setValue(userProfile);
                startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setUp(){
        newuser= (EditText)findViewById(R.id.etNewname);
        newcontact= (EditText)findViewById(R.id.etNewcontact);
        save=(Button)findViewById(R.id.btnSavenew);

    }
}
