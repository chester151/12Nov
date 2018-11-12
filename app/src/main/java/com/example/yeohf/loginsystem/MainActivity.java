package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Adapters.RentalListAdapter;
import com.example.yeohf.loginsystem.Entity.Rental;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="Main Activity" ;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    ListView listViewOverallRentals;
    FirebaseUser currentuser;
    List<Rental> overall_rentallist;
    DatabaseReference database_allref;
    EditText searchRental;
    FloatingActionButton filter;
    Button reset;
    RentalListAdapter adapter;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.d(TAG,"In Main Activity");
        overall_rentallist = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        database_allref = FirebaseDatabase.getInstance().getReference("Rentals");
        listViewOverallRentals = findViewById(R.id.allrentalsList);
        searchRental = findViewById(R.id.searchRental);
        filter = findViewById(R.id.filterbutton);
        reset = findViewById(R.id.btnReset);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view3);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        for(int i=0;i<bottomNavigationView.getMenu().size();i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        MenuItem menuitem = bottomNavigationView.getMenu().findItem(R.id.homeicon);
        menuitem.setChecked(true);

        listViewOverallRentals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RentalDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("title", overall_rentallist.get(position).getTitle());
                extras.putString("price", overall_rentallist.get(position).getPrice());
                extras.putString("address", overall_rentallist.get(position).getAddress());
                extras.putString("model", overall_rentallist.get(position).getModel());
                extras.putString("listingType", overall_rentallist.get(position).getListingType());
                extras.putString("type", overall_rentallist.get(position).getType());
                extras.putString("storey", overall_rentallist.get(position).getStorey());
                extras.putString("picUrl", overall_rentallist.get(position).getImagePath());
                extras.putString("rentId", overall_rentallist.get(position).getRentalid());
                extras.putDouble("lat", overall_rentallist.get(position).getLat());
                extras.putDouble("lng", overall_rentallist.get(position).getLng());
                extras.putString("chatid", overall_rentallist.get(position).getChatId());
                intent.putExtras(extras);
                MainActivity.this.startActivity(intent);

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });

        database_allref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                overall_rentallist.clear();
                Iterable<DataSnapshot> child = dataSnapshot.getChildren();
                for (DataSnapshot uniquesnap : child) {
                    Rental rental = uniquesnap.getValue(Rental.class);
                    overall_rentallist.add(rental);
                }
               /* overall_rentallist.clear();
                for (DataSnapshot uniquekeySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot rentalzsnapshot : uniquekeySnapshot.getChildren()) {
                        Rental rental2 = rentalzsnapshot.getValue(Rental.class);
                        overall_rentallist.add(rental2);
                    }
                }*/
                // Toast.makeText(getApplicationContext(), "Cool!", Toast.LENGTH_SHORT).show();
                adapter = new RentalListAdapter(MainActivity.this, overall_rentallist);
                listViewOverallRentals.setAdapter(adapter);
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    adapter.filter(extras);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        searchRental.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchRental.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.reset();
                Toast.makeText(getApplicationContext(), "List has been reset!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        if(currentuser==null){
            MenuItem logitem = menu.findItem(R.id.logoutMenu);
            logitem.setTitle("Login");

        }
        else{
            MenuItem logitem = menu.findItem(R.id.logoutMenu);
            logitem.setTitle("Logout");

        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentuser = mAuth.getCurrentUser();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.homeicon:
                Toast.makeText(getApplicationContext(), "You are already at the Home Page!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.flatinfoicon:
                //checks if user is logged in. If not- proceed to log in screen first.
                if(currentuser==null){
                    Toast.makeText(getApplicationContext(), "You have to be logged in to see your flats!", Toast.LENGTH_SHORT).show();
                    startActivity (new Intent (MainActivity.this,StartActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "You may edit your flat listings!", Toast.LENGTH_SHORT).show();
                    startActivity (new Intent (MainActivity.this,MyRentalActivity.class));
                }
                break;

            case R.id.sellicon:
                if(currentuser==null){
                    Toast.makeText(getApplicationContext(), "You have to be logged in to rent/sell your property!!", Toast.LENGTH_SHORT).show();
                    startActivity (new Intent (MainActivity.this,StartActivity.class));
                }
                Intent intent = new Intent(MainActivity.this, CreateListingActivity.class);
                startActivity(intent);
                break;
            case R.id.profileicon:
                if(currentuser==null){
                    Toast.makeText(getApplicationContext(), "You have to be logged in to rent/sell your property!!", Toast.LENGTH_SHORT).show();
                    startActivity (new Intent (MainActivity.this,StartActivity.class));
                }
                startActivity (new Intent (MainActivity.this,ProfileActivity.class));
        }
        return true;
    }

    public void logout() {
        mAuth.signOut();
        finish();
        Toast.makeText(getApplicationContext(), "User Logged out!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutMenu:
                if(currentuser==null){
                    Toast.makeText(getApplicationContext(), "Enter your details to Log in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                }
                else{
                    logout();
                }
                break;
            case R.id.profileMenu:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
