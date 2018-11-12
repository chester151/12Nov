package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Entity.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView profilename, profilecontact, profileemail;
    private Button btnupdateprofile, btngoback,btnchangemailpass;
    private ImageView profiledp;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myref;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUp();

        bottomNavigationView = findViewById(R.id.navigation_view4);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        for(int i=0;i<bottomNavigationView.getMenu().size();i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        MenuItem menuitem= bottomNavigationView.getMenu().findItem(R.id.profileicon);
        menuitem.setChecked(true);


        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myref= firebaseDatabase.getReference("Users").child((firebaseAuth.getUid()));
        DatabaseReference databaseReference= myref;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userprofile=dataSnapshot.getValue(UserProfile.class);
                profilename.setText(userprofile.getUserName());
                profilecontact.setText(userprofile.getUserContact());
                profileemail.setText(userprofile.getUserEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });



        btnupdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
            }
        });

        btnchangemailpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateMailPassActivity.class));
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUp(){
        profilename = findViewById(R.id.etProfilename);
        profilecontact = findViewById(R.id.etProfilecontact);
        profileemail = findViewById(R.id.etProfilemail);

        profiledp = findViewById(R.id.ivProfiledp);

        btnupdateprofile = findViewById(R.id.btnUpdateEmail);
        btnchangemailpass = findViewById(R.id.btnChangemailpass);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeicon:
                Toast.makeText(getApplicationContext(), "You are now at the Home Page!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (ProfileActivity.this,MainActivity.class));
                break;

            case R.id.flatinfoicon:
                Toast.makeText(getApplicationContext(), "You are now at the Personal Listings Page!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (ProfileActivity.this,MyRentalActivity.class));
                break;

            case R.id.sellicon:
                Toast.makeText(getApplicationContext(), "You are now at the Create Listing Page!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (ProfileActivity.this,CreateListingActivity.class));
                break;
            case R.id.profileicon:
                Toast.makeText(getApplicationContext(), "You are already at the profil page!", Toast.LENGTH_SHORT).show();

        }
        return true;
    }
}
