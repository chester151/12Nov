package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Helper.GetNearbyPlaces;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class RentalDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    MapView mapView;
    GoogleMap gmap;
    double latitude, longitude;
    private int PROXIMITY_RADIUS = 500;
    private GoogleApiClient googleApiClient;
    static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    String title, roomtype, zone, model, listingtype, storey, address, rentid, imagepath, price, chatid;
    TextView lblTitle, lblroomtype, lblzone, lblprice, lblmodel, lbllistingtype, lblstorey;
    ImageView imgview;
    Spinner spinner;
    Button joinchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentaldetails);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        title = extras.getString("title");
        address = extras.getString("address");
        price = extras.getString("price");
        roomtype = extras.getString("type");
        listingtype = extras.getString("listingType");
        model = extras.getString("model");
        storey = extras.getString("storey");
        zone = extras.getString("zone");
        rentid = extras.getString("rentId");
        imagepath = extras.getString("picUrl");
        latitude = extras.getDouble("lat");
        longitude = extras.getDouble("lng");
        chatid = extras.getString("chatid");

        lblTitle = findViewById(R.id.txtTitle);
        lblroomtype = findViewById(R.id.txtType);
        lblmodel = findViewById(R.id.txtModel);
        lbllistingtype = findViewById(R.id.txtListingType);
        lblstorey = findViewById(R.id.txtstorey);
        lblprice = findViewById(R.id.txtPrice);
        lblzone = findViewById(R.id.txtzone);
        imgview = findViewById(R.id.imgViewDetail);
        joinchat = findViewById(R.id.btnChat);

        lblTitle.setText(title);
        lblroomtype.setText(roomtype);
        lblprice.setText("$" + price);
        lblmodel.setText(model);
        lbllistingtype.setText(listingtype);
        lblstorey.setText("Lv." + storey);
        lblzone.setText(zone);
        Picasso.with(RentalDetailsActivity.this).load(imagepath).into(imgview);
        spinner = findViewById(R.id.spinChoice);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choices, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        joinchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentalDetailsActivity.this, ChatActivity.class));
            }
        });

// Apply the adapter to the spinners
        joinchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentalDetailsActivity.this, ChatActivity.class);
                Bundle extras = new Bundle();
                extras.putString("rentid", rentid);
                extras.putString("chatid", chatid);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object transferData[] = new Object[2];
                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                String choice = parent.getItemAtPosition(position).toString();
                String url = "";
                switch (choice) {
                    case "Shopping Malls":
                        clearAndWriteMarker(gmap);
                        url = getUrl(latitude, longitude, "shopping_mall");
                        transferData[0] = gmap;
                        transferData[1] = url;
                        getNearbyPlaces.execute(transferData);
                        Toast.makeText(getApplicationContext(), "Showing nearby Shopping Malls..", Toast.LENGTH_SHORT).show();
                        break;
                    case "Banks":
                        clearAndWriteMarker(gmap);
                        url = getUrl(latitude, longitude, "banks");
                        transferData[0] = gmap;
                        transferData[1] = url;
                        getNearbyPlaces.execute(transferData);
                        Toast.makeText(getApplicationContext(), "Showing nearby banks..", Toast.LENGTH_SHORT).show();
                        break;
                    case "Clinics":
                        clearAndWriteMarker(gmap);
                        url = getUrl(latitude, longitude, "doctor");
                        transferData[0] = gmap;
                        transferData[1] = url;
                        getNearbyPlaces.execute(transferData);
                        Toast.makeText(getApplicationContext(), "Showing nearby clinics..", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(16);
        LatLng sg = new LatLng(latitude, longitude);
        gmap.addMarker(new MarkerOptions().position(sg).title(address));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(sg));
        buildGoogleApiClient();
    }

    public void clearAndWriteMarker(GoogleMap googleMap) {
        googleMap.clear();
        LatLng sg = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(sg).title(address));
    }

    private String getUrl(double latitide, double longitude, String nearbyPlace) {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=" + PROXIMITY_RADIUS);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyABdZo8KXQ9HlprBsZRx6FALzp5pNfDWSs");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());
        System.out.println(googleURL.toString());
        return googleURL.toString();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
