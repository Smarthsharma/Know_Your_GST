package com.applications.kshitij.projectgst;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

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
import java.net.URLEncoder;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }


    public void launcherclick(View v){
        load_data_from_server("lancher","");
    }

    public void load_data_from_server(String type, String str1){
        AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {
            ProgressDialog pDialog;
            String getitems_url = "http://kshitijtomar.000webhostapp.com/projects/projectGST/getindustry.php";
            @Override
            protected String doInBackground(String... strings) {
                try {
                    String industryname = strings[1];
                    URL url = new URL(getitems_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data =
                            URLEncoder.encode("industryname", "UTF-8") + "=" + URLEncoder.encode(industryname, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(LauncherActivity.this);
                pDialog.setMessage("Retreiving Data...");
                pDialog.setCancelable(false);
                pDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String avoid) {
                if(pDialog.isShowing()) pDialog.cancel();
                if (avoid.contains("ALL ITEMS :")) {
                    getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("AllData",avoid).apply();
//                    Toast.makeText(LauncherActivity.this, avoid, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LauncherActivity.this,TotalActivity.class));
                    finish();

/**********************Use this code for data usage***************************
                    String[] itemslist = getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available").split("<br><hr><br>");
                    String ItemIndustry, ItemName , ItemCost, ItemGST;
                    for(int i =1; i<itemslist.length;i++){
                        ItemIndustry=itemslist[i].split("<br>")[0];
                        ItemName=itemslist[i].split("<br>")[1];
                        ItemCost=itemslist[i].split("<br>")[2];
                        ItemGST=itemslist[i].split("<br>")[3];
                    }
*************************************************************************/


//                    if (iteminfo.length > 6) {
//                        getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("item_name",iteminfo[1]).apply();
//
//                        getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("name", iteminfo[1]).apply();
//                        getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("surname", userinfo[2]).apply();
//                        getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("emailid", userinfo[3]).apply();
//                        getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("contactno", userinfo[4]).apply();
//                        getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("ieeememno", userinfo[5]).apply();
//                        getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("email_auth", userinfo[6]).apply();
//                        Toast.makeText(AddItemActivity.this, "Login Successful! Welcome " + userinfo[1] + " " + userinfo[2] + "!!!", Toast.LENGTH_SHORT).show();
//                        finish();
                    } else Toast.makeText(LauncherActivity.this, avoid, Toast.LENGTH_LONG).show();
                    super.onPostExecute(avoid);
                }
            };

        task.execute(type,str1);
        }
}
