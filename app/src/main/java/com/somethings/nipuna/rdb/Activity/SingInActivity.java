package com.somethings.nipuna.rdb.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.somethings.nipuna.rdb.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SingInActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private EditText etusername;
    private EditText etpassword;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                Intent intent = new Intent(SingInActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        etusername = (EditText) findViewById(R.id.input_emp_number);
        etpassword = (EditText) findViewById(R.id.input_emp_pin);

    }

    public void sing_in(View view) {

        final String username = etusername.getText().toString();
        final String password = etpassword.getText().toString();

        new AsyncLogin().execute(username, password);

//        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), password, Toast.LENGTH_LONG).show();

    }

    private class AsyncLogin extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog = new ProgressDialog(SingInActivity.this);
        HttpURLConnection connection;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... prams) {

            try {

               url = new URL("http://cms-androids.000webhostapp.com/user_login.php");
               //url = new URL("http://77.104.168.130/~ecocott5/user_login.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }

            try {
                System.out.println("1");
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", prams[0]).appendQueryParameter("password", prams[1]);

                String json_string = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(json_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                connection.connect();
                System.out.println("2");


            } catch (IOException e) {
                e.printStackTrace();

                return "exception";
            }
            try {

                int response_code = connection.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return ("unsuccessful");

                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {

                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            if (result.equalsIgnoreCase("true")) {

                // user sign in
                Intent intent = new Intent(SingInActivity.this, PhoneAddressBook.class);
                startActivity(intent);
                SingInActivity.this.finish();



            } else if (result.equalsIgnoreCase("false")) {
                // user sign in Invalid Employee ID or PIN
                AlertDialog alertDialog = new AlertDialog.Builder(SingInActivity.this).create();
                alertDialog.setTitle("Info");
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setMessage("Invalid Employee ID or PIN");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                // user sign in Connection Problem
                AlertDialog alertDialog = new AlertDialog.Builder(SingInActivity.this).create();
                alertDialog.setTitle("Info");
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setMessage("Connection Problem");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }
    }
}