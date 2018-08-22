package com.somethings.nipuna.rdb.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.somethings.nipuna.rdb.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ATMLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private GoogleMap mMap;
Button nearest_atm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmlocation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.atm_location);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ATMLocationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        nearest_atm=findViewById(R.id.nearest_atm);

        nearest_atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String B="B";
                Intent intent = new Intent(ATMLocationActivity.this, NearestBranchATMActivity.class);
                intent.putExtra("B",B);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        new atmlocation(this).execute();
//        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//            }
//        }
//        mMap.setMyLocationEnabled(true);

    }

    class atmlocation extends AsyncTask<String,String,String> {

        private Context mContext;

        public atmlocation (Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            final  String  atm_location="http://cms-androids.000webhostapp.com/atm_location.php";
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBuilder = new FormBody.Builder();

            // dynamically add more parameter like this:
            //  formBuilder.add("phone", "000000");

            RequestBody formBody = formBuilder.build();
            Request request = new Request.Builder()
                    .url(atm_location)
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.getMessage();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println(s+"hhhh");
            try {
                JSONObject jsonAObjectrray =new JSONObject(s);

                JSONArray Loadpro = jsonAObjectrray.getJSONArray("server_respone");

                for (int i = 0; i < Loadpro.length(); i++) {
                    JSONObject object = Loadpro.getJSONObject(i);
//                        String atm_terminal_id = object.getString("atm_terminal_id");
//                        String branch = object.getString("branch");
//                        String location = object.getString("location");
//                        String branch_latitude = object.getString("branch_latitude");
//                        String branch_longitude = object.getString("branch_longitude");
//                        ATM atm = new ATM(atm_terminal_id, branch, location,branch_latitude,branch_longitude);
//                        arrayList.add(atm);

                    final double latitude= object.getDouble("latitude");
                    final double longitude= object.getDouble("longitude");

                    LatLng posisiabsen = new LatLng(latitude,longitude); ////your lat lng
                    mMap.addMarker(new MarkerOptions()
                            .position(posisiabsen)
                            .title(" RDB ATM ,  " + object.getString("atm_terminal_id")+ " ,   " +object.getString("location"))
                            );

                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.9682285, 79.9047198), 15));
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


            }catch (Exception e){
                e.printStackTrace();
            }

        }
        // method definition
        public BitmapDescriptor getMarkerIcon(String color) {
            float[] hsv = new float[3];
            Color.colorToHSV(Color.parseColor(color), hsv);
            return BitmapDescriptorFactory.defaultMarker(hsv[0]);
        }
    }

}
