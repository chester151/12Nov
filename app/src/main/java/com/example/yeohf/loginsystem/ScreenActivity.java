package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView profilename, profilecontact, profileemail;
    private Button btnupdateprofile, btngoback;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String add, price,desc,title;
    Button addrental;
    Button seeownrental;
    Button seeallrental;
    private EditText etadd;
    private EditText etprice;
    private EditText etdesc;
    private EditText ettitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        etadd = findViewById(R.id.etAdd);
        etprice= (EditText)findViewById(R.id.etPrice);
        etdesc= (EditText)findViewById(R.id.etDesc);
        ettitle= (EditText)findViewById(R.id.etTitle);

        addrental=(Button)findViewById(R.id.btAddRental);
        seeownrental = (Button)findViewById(R.id.btSeeRentals);
        seeallrental= (Button)findViewById(R.id.btSeeAllRental);


        firebaseAuth = firebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

        addrental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserdata();
            }
        });
        seeownrental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScreenActivity.this, SecondActivity.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view3);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // hold first
    private void sendUserdata(){
        // FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        //        DatabaseReference myRef= firebaseDatabase.getReference("Users");
        //        UserProfile userProfile= new UserProfile(firebaseAuth.getUid(),name,contact,email);
        //        myRef.child(firebaseAuth.getUid()).setValue(userProfile);

        DatabaseReference myRef= firebaseDatabase.getReference("Rentals").child(firebaseAuth.getUid());
        add = etadd.getText().toString();
        price = etprice.getText().toString();
        desc = etdesc.getText().toString();
        title = ettitle.getText().toString();

        String id= myRef.push().getKey();
        //Rental newrental= new Rental(id,add,price,desc,title);
        //myRef.child(id).setValue(newrental);

    }

    //FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    //        DatabaseReference myRef= firebaseDatabase.getReference(firebaseAuth.getUid());
    //        UserProfile userProfile= new UserProfile(name,contact);
    //        myRef.child("Users").setValue(userProfile);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutMenu:
                logout();
                break;
            case R.id.profileMenu:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ScreenActivity.this,StartActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flatinfoicon:
                Toast.makeText(getApplicationContext(), "To be done!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeicon:
                Toast.makeText(getApplicationContext(), "You are already in this page!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sellicon:
                Toast.makeText(getApplicationContext(), "To be done!", Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }
        return true;
    }
}
