package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterActivity extends AppCompatActivity {
    CheckBox checkrental, checkresale;
    Spinner zonefilter, modelfilter, typefilter, storeyfilter;
    EditText pricemin, pricemax;
    Button filter;

    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_filter);
        checkrental = findViewById(R.id.checkRental);
        checkresale = findViewById(R.id.checkResale);
        zonefilter = findViewById(R.id.zoneSpinnerFilter);
        modelfilter = findViewById(R.id.modelSpinnerFilter);
        typefilter = findViewById(R.id.typeSpinnerFilter);
        storeyfilter = findViewById(R.id.storeySpinnerFilter);
        pricemin = findViewById(R.id.editPriceMin);
        pricemax = findViewById(R.id.editPriceMax);
        filter = findViewById(R.id.btnFilterSearch);


        ArrayAdapter<CharSequence> zoneadapter = ArrayAdapter.createFromResource(this, R.array.zone, android.R.layout.simple_spinner_item);
        zoneadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zonefilter.setAdapter(zoneadapter);
        ArrayAdapter<CharSequence> storeyadapter = ArrayAdapter.createFromResource(this, R.array.storey, android.R.layout.simple_spinner_item);
        storeyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeyfilter.setAdapter(storeyadapter);
        ArrayAdapter<CharSequence> typeadapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typefilter.setAdapter(typeadapter);
        ArrayAdapter<CharSequence> modeladapter = ArrayAdapter.createFromResource(this, R.array.model, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelfilter.setAdapter(modeladapter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int choice;
                if (checkrental.isChecked() && !checkresale.isChecked()) {
                    choice = 0; // Only rental is checked
                } else if (checkresale.isChecked() && !checkrental.isChecked()) {
                    choice = 1; // Only resale is checked
                } else if (checkresale.isChecked() && checkrental.isChecked()) {
                    choice = 2; // both is checked
                } else {
                    choice = -999; // Both not checked
                }

                if (choice == -999) {
                    Toast.makeText(getApplicationContext(), "Please ensure that you have checked one of the listing types!", Toast.LENGTH_LONG).show();
                } else if (pricemin.getText().toString().equals("") || pricemax.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please ensure that you have typed in the range.", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(pricemin.getText().toString()) > Integer.parseInt(pricemax.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please ensure that the minimum is lower than the maximum.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("zonefilter", zonefilter.getSelectedItem().toString());
                    extras.putString("storeyfilter", storeyfilter.getSelectedItem().toString());
                    extras.putString("typefilter", typefilter.getSelectedItem().toString());
                    extras.putString("modelfilter", modelfilter.getSelectedItem().toString());
                    extras.putInt("checkedselection", choice);
                    extras.putString("pricemin", pricemin.getText().toString());
                    extras.putString("pricemax", pricemax.getText().toString());
                    intent.putExtras(extras);
                    FilterActivity.this.startActivity(intent);
                }
            }
        });
    }
}
