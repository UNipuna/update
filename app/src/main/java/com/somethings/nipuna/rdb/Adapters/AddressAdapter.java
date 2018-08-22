package com.somethings.nipuna.rdb.Adapters;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.somethings.nipuna.rdb.Class.AddressBook;
import com.somethings.nipuna.rdb.R;

import java.util.ArrayList;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.somethings.nipuna.rdb.Activity.PhoneAddressBook;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0;

    ArrayList<AddressBook> arrayList;
    Dialog dialog;
    PhoneAddressBook mactivity;

    public AddressAdapter(ArrayList<AddressBook> arrayList, PhoneAddressBook mActivity ) {
        this.arrayList = arrayList;
        this.mactivity = mActivity;

    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.load_address_book,parent,false);
       return new ViewHolder(view);
    }

    @android.annotation.SuppressLint("MissingPermission") public void makecall(String mobile){
        String s = mobile;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + s));
        mactivity.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(final AddressAdapter.ViewHolder holder, int position) {
        final AddressBook address_book=arrayList.get(position);
//holder set value
        holder.emp_number.setText(address_book.getEmp_number());
        holder.emp_name.setText(address_book.getEmp_name());
        holder.emp_designation.setText(address_book.getEmp_designation());
        holder.emp_branch_name.setText(address_book.getEmp_branch_name());
        holder.emp_branch_code.setText(address_book.getEmp_branch_code());
        holder.emp_office_no.setText(address_book.getEmp_office_no());
        holder.emp_mobile_no.setText(address_book.getEmp_mobile_no());
//holder get value
        holder.name = address_book.getEmp_name();
        holder.mobile = address_book.getEmp_mobile_no();
        holder.nav_emp_names = address_book.getEmp_name();
        YoYo.with(Techniques.FadeInUp).duration(1000).playOn(holder.linearLayout);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.alert_dialog_contact);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView alert_emp_name = (TextView) dialog.findViewById(R.id.alert_name);
                final TextView alert_emp_mobile = (TextView) dialog.findViewById(R.id.alert_number);
                final Button alert_btn_emp_call = (Button) dialog.findViewById(R.id.alert_btn_emp_call) ;
                alert_emp_name.setText(holder.name);
                alert_emp_mobile.setText(holder.mobile);

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

                          makecall(holder.mobile);
                        }
                    }
                });

                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static String nav_emp_names="";
        String name,mobile;
        private TextView emp_number,emp_name,emp_designation,emp_branch_name,emp_branch_code,emp_office_no,emp_mobile_no,emp_email;
        private LinearLayout linearLayout;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            emp_number = (TextView) itemView.findViewById(R.id.emp_number);
            emp_name = (TextView) itemView.findViewById(R.id.emp_name);
            emp_designation=(TextView) itemView.findViewById(R.id.emp_designation);
            emp_branch_name=(TextView) itemView.findViewById(R.id.emp_branch_name);
            emp_branch_code=(TextView) itemView.findViewById(R.id.emp_branch_code);
            emp_office_no=(TextView) itemView.findViewById(R.id.emp_office_no);
            emp_mobile_no=(TextView) itemView.findViewById(R.id.emp_mobile_no);
//            emp_email=(TextView) itemView.findViewById(R.id.emp_email);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.activity_address_book);
        }
    }

    public void setFilter(ArrayList<AddressBook> filter){
        arrayList=filter;
        notifyDataSetChanged();
    }
}
