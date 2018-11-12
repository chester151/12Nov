package com.example.yeohf.loginsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Entity.Rental;
import com.example.yeohf.loginsystem.Entity.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditListingActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myref;

    private EditText titleet;
    private EditText priceet;

    private Button backbtn;
    private Button submitbtn;

    private RadioGroup listingType2;
    private RadioButton listtype2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);

        titleet=(EditText)findViewById(R.id.titleet);
        priceet=(EditText)findViewById(R.id.priceet);
        backbtn=(Button)findViewById(R.id.backbtn);
        submitbtn=(Button)findViewById(R.id.submitbtn);
        listingType2 = findViewById(R.id.rentalresaleradio2);


        Intent intent= getIntent();
        String rentalid= intent.getStringExtra("key");



        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myref= firebaseDatabase.getReference("Rentals").child(rentalid);
        DatabaseReference databaseReference= myref;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Rental r=dataSnapshot.getValue(Rental.class);
                titleet.setText(r.getTitle());
                priceet.setText(r.getPrice());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditListingActivity.this,MyRentalActivity.class));
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listtype2 = findViewById(listingType2.getCheckedRadioButtonId());
                final String newtitle= titleet.getText().toString();
                final String newprice= priceet.getText().toString();
                final String newtype= listtype2.getText().toString();
                Log.d("SUBMITED:",newtype);

                DatabaseReference databaseReference2= myref;
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Rental r=dataSnapshot.getValue(Rental.class);
                        r.setTitle(newtitle);
                        r.setPrice(newprice);
                        r.setListingType(newtype);
                        dataSnapshot.getRef().setValue(r);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

            }
        });

    }
}
