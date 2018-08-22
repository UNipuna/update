package com.somethings.nipuna.rdb.Connection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Streems {
    //.php class files;
    public static final String  user_login ="user_login.php";
    public static final String  branch_addres_book_search="phone_addres_book_search.php";
    public static final String  phone_addres_book_advances_search="phone_addres_book_advances_search.php";
//    public static final String  phone_addres_book_search="phone_addres_book_search.php";

    //HttpURLConnection
    public static HttpURLConnection getConnection(String phpfile) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(ipConnection.IP + phpfile);
            urlConnection = (HttpURLConnection) url.openConnection();
          urlConnection.setRequestMethod("POST");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    //JSONObject

    public static JSONObject getJsonObject(JSONObject jsonObject, String phpname) {
        try {
            HttpURLConnection connection = getConnection(phpname);
            //ObjectOutputStream
            ObjectOutputStream outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.writeObject(jsonObject.toString());
            outputStream.close();
            //ObjectInputStream
            ObjectInputStream inputStream = new ObjectInputStream(connection.getInputStream());
            //JSONObject
            JSONObject object = new JSONObject(inputStream.readObject().toString());
            return object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //JSONObject
    public static JSONArray getJsonArray(JSONObject jsonObject, String servletsname) {
        try {
            HttpURLConnection connection = getConnection(servletsname);
            //ObjectOutputStream
            ObjectOutputStream outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.writeObject(jsonObject.toString());
            outputStream.close();

            // //ObjectInputStream
            ObjectInputStream inputStream = new ObjectInputStream(connection.getInputStream());
            //JSONArray
            JSONArray jsonArray = new JSONArray(inputStream.readObject().toString());
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
