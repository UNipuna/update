package com.somethings.nipuna.rdb.Adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.somethings.nipuna.rdb.Activity.BranchAddressActivity;
import com.somethings.nipuna.rdb.Class.AddressBook;
import com.somethings.nipuna.rdb.Class.AddressBranch;
import com.somethings.nipuna.rdb.R;

import java.util.ArrayList;



public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder>{

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0;

    BranchAddressActivity mactivity;
    ArrayList<AddressBranch> branchArrayList;
    Dialog dialog;
    AlertDialog alertDialog;
    SupportMapFragment mMapView;
    public BranchAdapter(BranchAddressActivity mactivity, ArrayList<AddressBranch> branchArrayList) {
        this.mactivity = mactivity;
        this.branchArrayList = branchArrayList;
    }

    @Override
    public BranchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.load_branch_address,parent,false);
       return new ViewHolder(view);
    }
    @android.annotation.SuppressLint("MissingPermission") public void makecall(String branchoffice_no){
        String number = branchoffice_no;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        mactivity.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(final BranchAdapter.ViewHolder holder, int position) {

        final AddressBranch addressBranch = branchArrayList.get(position);
        holder.branch_code.setText(addressBranch.getBranch_code());
        holder.branch_name.setText(addressBranch.getBranch_name());
        holder.branch_address.setText(addressBranch.getBranch_address());
        holder.branch_office_no.setText(addressBranch.getBranch_office_no());
        holder.branch_email.setText(addressBranch.getBranch_email());
        //holder get value
        holder.branchname = addressBranch.getBranch_name();
        holder.branchoffice_no = addressBranch.getBranch_office_no();

        holder.branch_latitude = addressBranch.getBranch_latitude();
        holder.branch_longitude = addressBranch.getBranch_longitude();

        YoYo.with(Techniques.FadeInUp).duration(1000).playOn(holder.linearLayout);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setMessage("Select an option below ?");
                builder.setCancelable(true);
                builder.setNegativeButton("Phone Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.alert_dialog_contact);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView alert_name = (TextView) dialog.findViewById(R.id.alert_name);
                        final TextView alert_number = (TextView) dialog.findViewById(R.id.alert_number);
                        final Button alert_btn_emp_call = (Button) dialog.findViewById(R.id.alert_btn_emp_call) ;
                        alert_name.setText(holder.branchname);
                        alert_number.setText(holder.branchoffice_no);

                        alert_btn_emp_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ActivityCompat.checkSelfPermission(view.getContext(),
                                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // Toast.makeText(mActivity.getApplicationContext(),"plese press agion" ,Toast.LENGTH_LONG).show();
                                            ActivityCompat.requestPermissions(mactivity,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                }else{

                                    makecall(holder.branchoffice_no);

                                }
                            }
                        });

                        dialog.show();

                        /// dialogInterface.cancel();
                    }
                });




                builder.setPositiveButton("Map Location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if(holder.branch_latitude.equals("0") || holder.branch_longitude.equals("0")){
                            final AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Temporally not available");
                            builder.setCancelable(true);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            alertDialog = builder.create();
                            alertDialog.show();
                        }else {
                            loadmap(holder.branch_latitude,holder.branch_longitude,holder.branchname);
                            }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    private void loadmap(String branch_latitude, String branch_longitude,String name) {

        final double latitude= Double.parseDouble(branch_latitude);
        final double longitude= Double.parseDouble(branch_longitude);
        final String branchname=name;
        dialog = new Dialog(mactivity);
        dialog.setContentView(R.layout.load_branch_location);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("Dismiss", "onDismiss: ");
                FragmentManager myFragmentManager =mactivity.getSupportFragmentManager();
                android.support.v4.app.Fragment xmlFragment = myFragmentManager.findFragmentById(R.id.location);
                myFragmentManager.beginTransaction().remove(xmlFragment).commit();
            }
        });
        FragmentManager myFragmentManager = mactivity.getSupportFragmentManager();

        mMapView = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.location);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();// needed to get the map to display immediately
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng posisiabsen = new LatLng(latitude,longitude); ////your lat lng
                googleMap.addMarker(new MarkerOptions().position(posisiabsen)
                        .title(" RDB " + branchname)
                        .icon(getMarkerIcon("#0399b7")));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }
        });

        dialog.show();

    }
    // method definition
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public int getItemCount() {
        return branchArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView branch_code,branch_name,branch_address,branch_office_no,branch_email;
        private LinearLayout linearLayout;
        View view;
        String branchname,branchoffice_no;
        String branch_latitude,branch_longitude;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            branch_code = itemView.findViewById(R.id.branch_code);
            branch_name = itemView.findViewById(R.id.branch_name);
            branch_address = itemView.findViewById(R.id.branch_address);
            branch_office_no = itemView.findViewById(R.id.branch_office_no);
            branch_email = itemView.findViewById(R.id.branch_email); ;
            linearLayout= (LinearLayout) itemView.findViewById(R.id.activity_branch_address_book);
        }
    }
    public void setFilter(ArrayList<AddressBranch> filter){
        branchArrayList=filter;
        notifyDataSetChanged();
    }
}
