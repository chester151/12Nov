package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Entity.Rental;
import com.example.yeohf.loginsystem.Helper.PricePrediction;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CreateListingActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ImageButton image;
    String downloadUrl;
    TextView estimatedprice;
    Button submit, predictprice;
    EditText title, price;
    Spinner zone, type, storey, model;
    PlaceAutocompleteFragment autocompleteFragment;
    StorageReference imagepath;
    private int gallery_intent = 2;
    DatabaseReference ref;
    FirebaseDatabase db;
    double lat, lng;
    String address;
    Rental rentalObj;
    RadioGroup listingType;
    FirebaseAuth mAuth;
    FirebaseUser firebaseuser;
    BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlisting);
        estimatedprice = findViewById(R.id.txtViewPrice);
        image = findViewById(R.id.uploadImage);
        submit = findViewById(R.id.inputCreate);
        price = findViewById(R.id.inputPrice);
        zone = findViewById(R.id.zoneSpinner);
        storey = findViewById(R.id.storeySpinner);
        type = findViewById(R.id.typeSpinner);
        model = findViewById(R.id.modelSpinner);
        title = findViewById(R.id.inputTitle);
        predictprice = findViewById(R.id.btnEstimatePrice);
        listingType = findViewById(R.id.rentalresaleradio2);
        mAuth=FirebaseAuth.getInstance();
        firebaseuser=mAuth.getCurrentUser();

        bottomNavigationView = findViewById(R.id.navigation_view3);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        for(int i=0;i<bottomNavigationView.getMenu().size();i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        MenuItem menuitem= bottomNavigationView.getMenu().findItem(R.id.sellicon);
        menuitem.setChecked(true);

        ref = FirebaseDatabase.getInstance().getReference("Rentals");
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("SG")
                .build();
        autocompleteFragment.setFilter(autocompleteFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng latlng = place.getLatLng();
                lat = latlng.latitude;
                lng = latlng.longitude;
                address = place.getName().toString();
                // TODO: Get info about the selected place.
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(), "Something went wrong! Please try again..", Toast.LENGTH_LONG).show();
            }
        });
        ArrayAdapter<CharSequence> zoneadapter = ArrayAdapter.createFromResource(this, R.array.zone, android.R.layout.simple_spinner_item);
        zoneadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zone.setAdapter(zoneadapter);
        ArrayAdapter<CharSequence> storeyadapter = ArrayAdapter.createFromResource(this, R.array.storey, android.R.layout.simple_spinner_item);
        storeyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storey.setAdapter(storeyadapter);
        ArrayAdapter<CharSequence> typeadapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeadapter);
        ArrayAdapter<CharSequence> modeladapter = ArrayAdapter.createFromResource(this, R.array.model, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        model.setAdapter(modeladapter);
    }
    public void uploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, gallery_intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallery_intent && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            image.setImageURI(uri);
            imagepath = FirebaseStorage.getInstance().getReference().child("Rentals").child(uri.getLastPathSegment());
            imagepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Uploaded failed.. Try again.", Toast.LENGTH_SHORT);

                }
            });
        }
    }
    private void getValues() {
        rentalObj = new Rental();
        String uniqueID = UUID.randomUUID().toString();
        String chatId = UUID.randomUUID().toString();
        RadioButton listtype = findViewById(listingType.getCheckedRadioButtonId());
        rentalObj.setImagePath(downloadUrl);
        rentalObj.setRentalid(uniqueID);
        rentalObj.setAddress(address);
        rentalObj.setTitle(title.getText().toString());
        rentalObj.setLat(lat);
        rentalObj.setLng(lng);
        rentalObj.setPrice(price.getText().toString());
        rentalObj.setStorey(storey.getSelectedItem().toString());
        rentalObj.setType(type.getSelectedItem().toString());
        rentalObj.setZone(zone.getSelectedItem().toString());
        rentalObj.setListingType(listtype.getText().toString());
        rentalObj.setModel(model.getSelectedItem().toString());
        rentalObj.setChatId(chatId);
        rentalObj.setUserid(firebaseuser.getUid());
    }
    public void estimatePrice(View view) {
        PricePrediction pp = new PricePrediction(zone.getSelectedItem().toString(), type.getSelectedItem().toString(), storey.getSelectedItem().toString(), model.getSelectedItem().toString(), 80);
        double getprice = pp.getPrice();
        estimatedprice.setText("$" + getprice);
    }
    public void createListing(View view) {
        getValues();
        String id = ref.push().getKey();
        ref.child(id).setValue(rentalObj);
        ref.child(id).child("key").setValue(id);
        Intent intent = new Intent(CreateListingActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Listing created!", Toast.LENGTH_SHORT);
       /* ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            getValues();
            FirebaseDatabase.getInstance().getReference("Rentals").push().setValue(rentalObj);
            Toast.makeText(getApplicationContext(), "Listing created!", Toast.LENGTH_SHORT);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        for(int i=0;i<bottomNavigationView.getMenu().size();i++){
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }

        switch (item.getItemId()) {

            case R.id.homeicon:

                Toast.makeText(getApplicationContext(), "You are now at the Home Page!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (CreateListingActivity.this,MainActivity.class));
                break;

            case R.id.flatinfoicon:
                Toast.makeText(getApplicationContext(), "You may edit your flat listings!", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (CreateListingActivity.this,MyRentalActivity.class));
                break;

            case R.id.sellicon:
                Toast.makeText(getApplicationContext(), "You are already at the Create Listing Page!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profileicon:
                Toast.makeText(getApplicationContext(), "You", Toast.LENGTH_SHORT).show();
                startActivity (new Intent (CreateListingActivity.this,ProfileActivity.class));
        }
        return true;
    }
}
