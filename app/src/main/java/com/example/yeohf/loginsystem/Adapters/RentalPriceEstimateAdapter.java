package com.example.yeohf.loginsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yeohf.loginsystem.Entity.JSONModel;
import com.example.yeohf.loginsystem.R;

import java.util.List;

public class RentalPriceEstimateAdapter extends BaseAdapter {


    private Context applicationContext;
    private int sample;
    private List<JSONModel> jsonModels;


    public RentalPriceEstimateAdapter(Context applicationContext, int sample, List<JSONModel> jsonModels) {

        this.applicationContext =applicationContext;
        this.sample = sample;
        this.jsonModels = jsonModels;

    }


    @Override    public int getCount() {
        return jsonModels.size();
    }

    @Override    public Object getItem(int i) {
        return jsonModels.get(i);
    }

    @Override    public long getItemId(int i) {
        return i;
    }

    @Override    public View getView(int i, View view, ViewGroup viewGroup) {



        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view =  layoutInflater.inflate(R.layout.activity_rentalestimatepricedetails,viewGroup,false);

        }

        TextView zone, type, quarter, price;

        zone = view.findViewById(R.id.txtZoneEst);
        type = view.findViewById(R.id.txtTypeEst);
        quarter = view.findViewById(R.id.txtQuarterEst);
        price = view.findViewById(R.id.txtPriceEst);


        zone.setText(jsonModels.get(i).getZone());
        type.setText(jsonModels.get(i).getType());
        quarter.setText(jsonModels.get(i).getQuarter());
        price.setText(jsonModels.get(i).getPrice());


        return view;
    }
}
