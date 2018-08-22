package com.somethings.nipuna.rdb.Load_Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.somethings.nipuna.rdb.Activity.PhoneAddressBook;
import com.somethings.nipuna.rdb.Adapters.AddressAdapter;
import com.somethings.nipuna.rdb.Class.AddressBook;
import com.somethings.nipuna.rdb.Connection.Streems;
import com.somethings.nipuna.rdb.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetAddresBook extends AsyncTask<Void,AddressBook,Void>{

    Context context;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    public static ArrayList <AddressBook> arrayList = new ArrayList<>();
    PhoneAddressBook phoneAddressBook;

    public GetAddresBook(Context ctx, PhoneAddressBook phoneAddressBook){
        this.context=ctx;
        activity= (Activity) ctx;
        this.phoneAddressBook = phoneAddressBook;
    }

    public final static String  phone_addres_book_search="http://cms-androids.000webhostapp.com/phone_addres_book_search.php";


    @Override
    protected void onPreExecute() {
        recyclerView = (RecyclerView) activity.findViewById(R.id.address_book_View);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new AddressAdapter(arrayList,phoneAddressBook);
        recyclerView.setAdapter(adapter);

        progressDialog=new ProgressDialog(context);
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setTitle("Please wait....");
        progressDialog.setMessage("List is Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL(phone_addres_book_search);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line;

            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line + "\n");
            }
            urlConnection.disconnect();
            String json_string=stringBuilder.toString().trim();
            JSONObject jsonObject= new JSONObject(json_string);
            JSONArray jsonArray=jsonObject.getJSONArray("server_respone");
            int count=0;
            arrayList.removeAll(arrayList);
            while (count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                count++;
                AddressBook addressBook1 = new AddressBook(JO.getString("emp_id"),
                        JO.getString("emp_number"),
                        JO.getString("emp_name"),
                        JO.getString("emp_designation"),
                        JO.getString("emp_email"),
                        JO.getString("emp_branch_code"),
                        JO.getString("emp_branch_name"),
                        JO.getString("emp_office_no"),
                        JO.getString("emp_mobile_no"));
                publishProgress(addressBook1);
                Thread.sleep(1);
            }
            Log.d("JSONSTRING", String.valueOf(jsonArray));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
}

    @Override
    protected void onProgressUpdate(AddressBook... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
    }
}

