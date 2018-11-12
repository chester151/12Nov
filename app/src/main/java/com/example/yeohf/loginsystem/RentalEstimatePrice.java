package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;

import com.example.yeohf.loginsystem.Adapters.RentalPriceEstimateAdapter;
import com.example.yeohf.loginsystem.Entity.JSONModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RentalEstimatePrice extends AppCompatActivity {
    private ListView l1;
    String zone;
    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentalestimateprice);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.7));
        l1 = findViewById(R.id.listviewrentestimate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        zone = bundle.getString("zone");
        setTitle("Monthly Estimation for " + zone);
        PriceEstimationJSONWork jsonWork = new PriceEstimationJSONWork();
        jsonWork.execute();
    }


    private class PriceEstimationJSONWork extends AsyncTask<String,String,List<JSONModel>> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String  FullJsonData;
        @Override        protected List<JSONModel> doInBackground(String... strings) {

            try {
                URL url = new URL("https://data.gov.sg/api/action/datastore_search?resource_id=6b1ec2ff-7c38-4ce9-9bbb-af865b4d78cb&q="+zone);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer= new StringBuffer();
                String line = "";

                while ((line= bufferedReader.readLine())!= null){
                    stringBuffer.append(line);
                }

                FullJsonData =  stringBuffer.toString();

                List<JSONModel> jsonModelList = new ArrayList<>();
                Log.i(FullJsonData,"json object");
                JSONObject jsonStartingObject = new JSONObject(FullJsonData);
                JSONObject Object = jsonStartingObject.getJSONObject("result");
                JSONArray jsonRentalArray = Object.getJSONArray("records");
                   for(int j = 0; j < jsonRentalArray.length(); j++) {
                       JSONObject jsonUnderArrayObject = jsonRentalArray.getJSONObject(j);
                       JSONModel jsonModel = new JSONModel();
                       jsonModel.setPrice("$"+jsonUnderArrayObject.getString("median_rent"));
                       jsonModel.setZone(jsonUnderArrayObject.getString("town"));
                       jsonModel.setType(jsonUnderArrayObject.getString("flat_type"));
                       jsonModel.setQuarter(jsonUnderArrayObject.getString("quarter"));
                       jsonModelList.add(jsonModel);
                }

                return jsonModelList;


            } catch (Exception e) {
                e.printStackTrace();
            } finally{

                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override        protected void onPostExecute(List<JSONModel> jsonModels) {
            super.onPostExecute(jsonModels);
            if (jsonModels != null) {
                RentalPriceEstimateAdapter adapter = new RentalPriceEstimateAdapter(getApplicationContext(), R.layout.activity_rentalestimatepricedetails, jsonModels);
                l1.setAdapter(adapter);
            }

        }
        }
}
