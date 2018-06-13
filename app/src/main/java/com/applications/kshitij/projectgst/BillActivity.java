package com.applications.kshitij.projectgst;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class BillActivity extends AppCompatActivity {

    TextView txtBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        txtBill = (TextView) findViewById(R.id.txtBill);

        String CompleteBill="";
        String txtamount = getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("TotalAmount", "0");

        String[] items=  getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("Itemlist", "There is no Item selected").split("\n<br>\n");

        for(int i =0; i<items.length; i++){
            String[] itemdetail = items[i].split("\n");

            String Name = itemdetail[0];
            String Quantity = itemdetail[1];
            String Gst = itemdetail[2];
            String Cost = itemdetail[3];
            String dt;

            Date cal = (Date) Calendar.getInstance().getTime();
            dt = cal.toLocaleString();
            String today_date = dt.toString();

            CompleteBill = CompleteBill + "\n\n\n\n"
                                        + "\n\n"    + "Dated : " + today_date
                                        + "\n\n\n"  + "Item Name : " + Name
                                        + "\n"      + "Item Quantity : " + Quantity
                                        + "\n"      + "Item Gst : " + Gst
                                        + "\n"      + "Item Rate : " + Cost;


        }


        CompleteBill=CompleteBill + "\n\nTotal Amount : " + txtamount;
        txtBill.setText(CompleteBill);
    }
}
