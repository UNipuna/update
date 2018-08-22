package com.somethings.nipuna.rdb.Load_Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.somethings.nipuna.rdb.Activity.BranchAddressActivity;
import com.somethings.nipuna.rdb.Adapters.BranchAdapter;
import com.somethings.nipuna.rdb.Class.AddressBranch;
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

public class GetAddressBranch extends AsyncTask<Void,AddressBranch,Void>{

    Context context;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    BranchAddressActivity branchAddressActivity;
    ProgressDialog progressDialog;
    public static ArrayList<AddressBranch> arrayList = new ArrayList<>();

    public GetAddressBranch(Context ctx, BranchAddressActivity branchAddressActivity) {
        this.context = ctx;
        activity = (Activity) ctx;
        this.branchAddressActivity = branchAddressActivity;
    }

    public final static String  brach_addres_book_search="http://cms-androids.000webhostapp.com/brach_addres_book_search.php";

    @Override
    protected void onPreExecute() {

        recyclerView = activity.findViewById(R.id.branch_address_View);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new BranchAdapter(branchAddressActivity,arrayList);
        recyclerView.setAdapter(adapter);

        progressDialog=new ProgressDialog(context);
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setTitle("Please wait....");
        progressDialog.setMessage("List is Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL(brach_addres_book_search);
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
                AddressBranch addressBranch = new AddressBranch(JO.getString("branch_code"),
                        JO.getString("branch_description"),
                        JO.getString("address"),
                        JO.getString("b_office_no"),
                        JO.getString("b_email"),
                        JO.getString("latitude"),
                        JO.getString("longitude"));
                publishProgress(addressBranch);
                Thread.sleep(1);
            }
            Log.d("JSON STRING",brach_addres_book_search);
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
    protected void onProgressUpdate(AddressBranch... values) {
       arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
    }
}
