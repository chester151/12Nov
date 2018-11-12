package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Adapters.RentalListAdapter;
import com.example.yeohf.loginsystem.Entity.Rental;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ListView listViewPersonalRentals;
    ListView listViewOverallRentals;
    List<Rental> personal_rentallist;
    List<Rental> overall_rentallist;
    DatabaseReference database_ownref;
    DatabaseReference database_allref;
    FirebaseAuth firebaseauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseauth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listViewPersonalRentals = findViewById(R.id.lvownrentals);
        listViewOverallRentals = findViewById(R.id.lvallrentals);
        personal_rentallist = new ArrayList<>();
        overall_rentallist = new ArrayList<>();
        database_ownref = FirebaseDatabase.getInstance().getReference("Rentals").child(firebaseauth.getUid());
        database_allref = FirebaseDatabase.getInstance().getReference("Rentals");

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view3);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        database_ownref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personal_rentallist.clear();
                for (DataSnapshot rentalsnapshot : dataSnapshot.getChildren()) {
                    Rental rental = rentalsnapshot.getValue(Rental.class);
                    personal_rentallist.add(rental);
                }
                RentalListAdapter adapter = new RentalListAdapter(SecondActivity.this, personal_rentallist);
                listViewPersonalRentals.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listViewOverallRentals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SecondActivity.this, RentalDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("title", overall_rentallist.get(position).getTitle());
                // extras.putString("roomtype", overall_rentallist.get(position).getDesc());
                extras.putString("price", overall_rentallist.get(position).getPrice());
                extras.putString("address", overall_rentallist.get(position).getAddress());
                extras.putString("desc", overall_rentallist.get(position).getDesc());
                //extras.putInt("imgid", imgid[position]);
                intent.putExtras(extras);
                SecondActivity.this.startActivity(intent);

            }
        });

        database_allref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                overall_rentallist.clear();
                for (DataSnapshot uniquekeySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot rentalzsnapshot : uniquekeySnapshot.getChildren()) {
                        Rental rental2 = rentalzsnapshot.getValue(Rental.class);
                        overall_rentallist.add(rental2);
                    }
                }
                Toast.makeText(getApplicationContext(), "Cool!", Toast.LENGTH_SHORT).show();
                RentalListAdapter adapter2 = new RentalListAdapter(SecondActivity.this, overall_rentallist);
                listViewOverallRentals.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flatinfoicon:
                Toast.makeText(getApplicationContext(), "You are already in this page!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeicon:
                Intent intent = new Intent(SecondActivity.this, ScreenActivity.class);
                startActivity(intent);
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
