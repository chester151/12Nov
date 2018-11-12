package com.example.yeohf.loginsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class MyRentalActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="In Rental Activity!" ;

    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    ListView listViewOverallRentals;
    FirebaseUser currentuser;
    List<Rental> overall_rentallist;
    DatabaseReference database_allref;
    EditText searchRental;
    RentalListAdapter adapter;
    Menu menu;
    Button delbtn;
    Button editbtn;
    Spinner delspinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rental2);
        Log.d(TAG,"OnCreate: ");
        overall_rentallist = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        database_allref = FirebaseDatabase.getInstance().getReference("Rentals");
        listViewOverallRentals = findViewById(R.id.myrentalsList);
        delbtn= (Button)findViewById(R.id.delbtn);
        editbtn= (Button)findViewById(R.id.editbtn);


        final List<String> editname= new ArrayList<String>();
        editname.add("");
        editname.add("Title");
        editname.add("Price");
        ArrayAdapter<String> editAdapter = new ArrayAdapter<String>(MyRentalActivity.this, android.R.layout.simple_spinner_item, editname);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view2);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        for(int i=0;i<bottomNavigationView.getMenu().size();i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        MenuItem menuitem= bottomNavigationView.getMenu().findItem(R.id.flatinfoicon);
        menuitem.setChecked(true);

        listViewOverallRentals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyRentalActivity.this, RentalDetailsActivity.class);
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
                MyRentalActivity.this.startActivity(intent);

            }
        });
        database_allref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                overall_rentallist.clear();
                final List<String> rentalname= new ArrayList<String>();
                Iterable<DataSnapshot> child = dataSnapshot.getChildren();
                for (DataSnapshot uniquesnap : child) {
                        Rental rental = uniquesnap.getValue(Rental.class);
                        if(rental.getUserid().equals(currentuser.getUid())){
                        overall_rentallist.add(rental);
                        rentalname.add(rental.getTitle());
                    }
                }
               /* overall_rentallist.clear();
                for (DataSnapshot uniquekeySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot rentalzsnapshot : uniquekeySnapshot.getChildren()) {
                        Rental rental2 = rentalzsnapshot.getValue(Rental.class);
                        overall_rentallist.add(rental2);
                    }
                }*/
                // Toast.makeText(getApplicationContext(), "Cool!", Toast.LENGTH_SHORT).show();
                adapter = new RentalListAdapter(MyRentalActivity.this, overall_rentallist);
                listViewOverallRentals.setAdapter(adapter);
                delspinner= (Spinner)findViewById(R.id.spinner);
                ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(MyRentalActivity.this, android.R.layout.simple_spinner_item, rentalname);
                nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                delspinner.setAdapter(nameAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text = delspinner.getSelectedItem().toString();
                final String key="key";
                String rentalkey="";
                for(int i =0;i<overall_rentallist.size();i++){
                    if (overall_rentallist.get(i).getTitle().equals(text)){
                        rentalkey=overall_rentallist.get(i).getKey();
                        break;
                    }

                }
                final String rentalkey2= rentalkey;

                Intent intent=new Intent(MyRentalActivity.this,EditListingActivity.class);
                intent.putExtra(key,rentalkey2);
                startActivity(intent);

            }

        });

//        editbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String variable= editspinner.getSelectedItem().toString();
//                final String rentalname= editspinner2.getSelectedItem().toString();
//                if(variable==""){
//                    Toast.makeText(MyRentalActivity.this,"You must select a variable to change and the corresponding rental name!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyRentalActivity.this);
//                final EditText input = new EditText(MyRentalActivity.this);
//                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
//                        ConstraintLayout.LayoutParams.MATCH_PARENT,
//                        ConstraintLayout.LayoutParams.MATCH_PARENT);
//                input.setLayoutParams(lp);
//                alertDialog.setView(input);
//
//                alertDialog.setPositiveButton("YES",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                final String newvalue = input.getText().toString();
//
//
//                                   database_allref.addValueEventListener(new ValueEventListener() {
//                                       @Override
//                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                           Iterable<DataSnapshot> child = dataSnapshot.getChildren();
//                                           for (DataSnapshot uniquesnap : child) {
//                                               Rental rental = uniquesnap.getValue(Rental.class);
//
//                                               if (rental.getTitle().equals(rentalname)) {
//                                                   if (variable.equals("title")) {
//                                                       uniquesnap.getRef().child("title").setValue(newvalue);
//                                                   } else {
//                                                       uniquesnap.getRef().child("price").setValue(newvalue);
//                                                   }
//                                               }
//                                               break;
//                                           }
//                                       }
//
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                            }});
//                alertDialog.setNegativeButton("NO",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                alertDialog.setMessage("Enter new "+ variable+":");
//                alertDialog.show();
//
//
//            }
//        });
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String text = delspinner.getSelectedItem().toString();




                database_allref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> child = dataSnapshot.getChildren();
                        for (DataSnapshot uniquesnap : child) {
                            Rental rental = uniquesnap.getValue(Rental.class);
                            if(rental.getTitle().equals(text)){
                                uniquesnap.getRef().removeValue();
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }});


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeicon:
                Toast.makeText(getApplicationContext(), "You are now at the Home Page!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (MyRentalActivity.this,MainActivity.class));
                break;

            case R.id.flatinfoicon:
                Toast.makeText(getApplicationContext(), "You are already at the personal listings page!", Toast.LENGTH_SHORT).show();

                break;

            case R.id.sellicon:
                Toast.makeText(getApplicationContext(), "You are now at the Create Listing Page!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (MyRentalActivity.this,CreateListingActivity.class));
                break;
            case R.id.profileicon:
                Toast.makeText(getApplicationContext(), "You", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (MyRentalActivity.this,ProfileActivity.class));
        }
        return true;
    }


}
