package com.somethings.nipuna.rdb.Load_Data;

import android.os.AsyncTask;
import android.util.Log;

import com.somethings.nipuna.rdb.Class.ATM;
import com.somethings.nipuna.rdb.Class.AddressBook;

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

public class GetATM extends AsyncTask<Void,ATM,Void> {

    public static ArrayList<ATM> atms=new ArrayList<>();


    public final static String  atm_location="http://cms-androids.000webhostapp.com/atm_location.php";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL url=new URL(atm_location);
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
            System.out.println(json_string+"nipuna");
            JSONObject jsonObject= new JSONObject(json_string);
            JSONArray jsonArray=jsonObject.getJSONArray("server_respone");
            int count=0;
          //  arrayList.removeAll(arrayList);
            while (count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                count++;
                ATM atm = new ATM(JO.getString("atm_terminal_id"),
                        JO.getString("branch"),
                        JO.getString("location"),
                        JO.getString("branch_latitude"),
                        JO.getString("branch_longitude"));
                publishProgress(atm);
                Thread.sleep(1);
            }
            Log.d("JSON", String.valueOf(jsonArray));

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
    protected void onProgressUpdate(ATM... values) {
       atms.add(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
