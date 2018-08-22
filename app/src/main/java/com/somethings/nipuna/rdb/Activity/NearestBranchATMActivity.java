package com.somethings.nipuna.rdb.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.somethings.nipuna.rdb.Class.location;
import com.somethings.nipuna.rdb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class NearestBranchATMActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap gMap;
    LocationManager locationManager;
    public String mprovider;
    Double fromLatitude, fromLongitude;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient client;
    LocationManager lm;
    String A = "A";
    String B = "B";
    String C = "C";
    Polyline line;
    private static final int MY_PERMISSION_RERQEST_CODE = 1;
    static public final int REQUEST_LOCATION = 1;
    private static final int LOCATION_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_branch_atm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (A.equals(getIntent().getStringExtra("A"))) {

            //  A = getIntent().getStringExtra("A");
            // Toast.makeText(this,"A",Toast.LENGTH_LONG).show();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NearestBranchATMActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else if (B.equals(getIntent().getStringExtra("B"))) {

            //  B = getIntent().getStringExtra("B");
            // Toast.makeText(this,"B",Toast.LENGTH_LONG).show();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NearestBranchATMActivity.this, ATMLocationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });


        } else if (C.equals(getIntent().getStringExtra("C"))) {

            //  C = getIntent().getStringExtra("C");
            //  Toast.makeText(this,"C",Toast.LENGTH_LONG).show();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NearestBranchATMActivity.this, BranchAddressActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.atm_nearest_location);

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        checkLocationandAddToMap();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//        }
//        gMap.setMyLocationEnabled(true);
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.9682285, 79.9047198), 15));
//        gMap.getUiSettings().setZoomControlsEnabled(true);
//        gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//        client = LocationServices.getFusedLocationProviderClient(this);
//        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                    fromLatitude = location.getLatitude();
//                    fromLongitude = location.getLongitude();
//
//                }
//            }
//        });

        gMap.setOnMarkerClickListener(this);
        new loardParth(this).execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    checkLocationandAddToMap();
                } else
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void checkLocationandAddToMap() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_REQUEST_CODE);
            return;
        }
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    fromLatitude = location.getLatitude();
                    fromLongitude = location.getLongitude();

                }
            }
        });
        gMap.setMyLocationEnabled(true);
        gMap.setOnMarkerClickListener(this);
        new loardParth(this).execute();
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        location lll= (location) marker.getTag();
        marker.setTitle("RDB " + lll.getLocation_name());
        if (line!=null) {
            line.remove();
        }
        double Latitude=lll.getLatitude();
        double Longitude=lll.getLongitude();
        //gMap.clear();

       // new loardParth(this).execute();

        getDirection(Latitude,Longitude);

        return false;

    }


    private class loardParth extends AsyncTask<String,String,String>{


        private Context mContext;

        public loardParth (Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            final String branch_atm="http://cms-androids.000webhostapp.com/branch_atm_location.php";
            OkHttpClient okHttpClient=new OkHttpClient();
            FormBody.Builder builder=new FormBody.Builder();

            RequestBody requestBody=builder.build();
            Request request= new Request.Builder()
                    .url(branch_atm)
                    .post(requestBody)
                    .build();

            try {
                Response response=okHttpClient.newCall(request).execute();
                return  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s+"branch_atm");
            if (s!=null){
                try {
                    JSONObject jsonAObjectrray =new JSONObject(s);

                    JSONArray Loadpro = jsonAObjectrray.getJSONArray("server_respone_rdb");
                    List <Double> doubles=new ArrayList<>();
                    for (int i = 0; i < Loadpro.length(); i++) {
                        JSONObject object = Loadpro.getJSONObject(i);
//                        String atm_terminal_id = object.getString("atm_terminal_id");
//                        String branch = object.getString("branch");
//                        String location = object.getString("location");
//                        String branch_latitude = object.getString("branch_latitude");
//                        String branch_longitude = object.getString("branch_longitude");
//                        ATM atm = new ATM(atm_terminal_id, branch, location,branch_latitude,branch_longitude);
//                        arrayList.add(atm);
                        final  String location= object.getString("branch_description");
                        final double latitude= object.getDouble("latitude");
                        final double longitude= object.getDouble("longitude");
                        com.somethings.nipuna.rdb.Class.location location1=new location(location,latitude,longitude);
                        LatLng posisiabsen = new LatLng(latitude,longitude); ////your lat lng


                        System.out.println("uman"+location);
                        String[] split = location.split("-");
                        if(split.length -1 > 0){
                            System.out.println("Location -"+split[0]);
                            System.out.println("Type -"+split[1]);

                           if (split[1].equalsIgnoreCase("ATM")){
                               Marker marker = gMap.addMarker(new MarkerOptions()
                                       .position(posisiabsen)

                                       .title("RDB " + location));//.icon(getMarkerIcon("#0399b7")));

                               marker.setTag(location1);
                               //marker.setTitle(location);
                           }

                        }else {
                            System.out.println("collorss");
                            Marker marker = gMap.addMarker(new MarkerOptions()
                                    .position(posisiabsen)
                                    .icon(getMarkerIcon("#0399b7"))
                                    //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                    .title("RDB " + location));//.icon(getMarkerIcon("#0399b7")));

                            marker.setTag(location1);
                            //marker.setTitle(location);
                        }
//                        Marker marker = gMap.addMarker(new MarkerOptions()
//                                .position(posisiabsen)
//                                .icon(getMarkerIcon("#0399b7"))
//                                .title(location));//.icon(getMarkerIcon("#0399b7")));
//
//                        marker.setTag(location1);

                    }
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fromLatitude, fromLongitude), 15));
                    gMap.getUiSettings().setZoomControlsEnabled(true);
                    gMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        public BitmapDescriptor getMarkerIcon(String color) {
            float[] hsv = new float[3];
            Color.colorToHSV(Color.parseColor(color), hsv);
            return BitmapDescriptorFactory.defaultMarker(hsv[0]);
        }
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {

        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyCFpivqZ23UgcJw-qjiFxWO_G4VQNos4zo");//AIzaSyDSXCqt_lse62oYjllt17NElV6qAoD7Yq8
        return urlString.toString();
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }


        return poly;
    }
    private void getDirection(Double tolat,Double tolon) {
        //Getting the URL

        String url = makeURL(fromLatitude, fromLongitude, tolat, tolon);
        System.out.println("path" + url);
        //Showing a dialog till we get the route
       // final ProgressDialog loading = ProgressDialog.show(this, "Getting Route", "Please wait...", false, false);

        //Creating a string request
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        loading.dismiss();
//                        //Calling the method drawPath to draw the path
//                        drawPath(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loading.dismiss();
//                    }
//                });
//
//        //Adding the request to request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

//okhttp

        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response=okHttpClient.newCall(request).execute();
            drawPath(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();

        }


    }



    //The parameter is the server response
    public void drawPath(String result) {
        //Getting both the coordinates
        //gMap.clear();
       // new  loardParth(this).execute();
      // LatLng from = new LatLng(fromLatitude, fromLongitude);
       //LatLng to = new LatLng(toLatitude, toLongitude);

        //Calculating the distance in meters


        //Displaying the distance
        //Toast.makeText(this,String.valueOf(dist+" Meters"),Toast.LENGTH_SHORT).show();


        try {
            //Parsing json

            System.out.println(result);

            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(0);

//            JSONObject distance = steps.getJSONObject("distance");
//
//            JSONObject time = steps.getJSONObject("duration");
//            System.out.println(String.valueOf(time.get("text").toString()));





            //  Log.i("Distance", distance.toString());
           // dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]", ""));
            // double price = dist * 50;

            //double p2 = price + 200;
            // estimated.setText("Estimated Price:$" + price + "-" + p2);
            //new DriverCheck(this, String.valueOf(dist)).execute();

            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            line = gMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(12)
                    .color(Color.rgb(3,153,183))
                    .geodesic(true)
            );


        } catch (JSONException e) {

        }
    }
}
