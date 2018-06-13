package com.applications.kshitij.projectgst;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
    }
    
    public void selectIndustry(View v){
        String[] itemslist = getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available").split("<br><hr><br>");
        String[] ItemIndustry = new String[itemslist.length];
        String[] ItemName  = new String[itemslist.length];
        String[]  ItemCost= new String[itemslist.length];
        String[]  ItemGST = new String[itemslist.length];

//        for(int i =1; i<itemslist.length;i++){
//            ItemIndustry[i-1]=itemslist[i].split("<br>")[0];
//            ItemName[i-1]=itemslist[i].split("<br>")[1];
//            ItemCost[i-1]=itemslist[i].split("<br>")[2];
//            ItemGST[i-1]=itemslist[i].split("<br>")[3];
//        }

        for(int i =1; i<=itemslist.length;i++){
            if(i<itemslist.length){
                ItemIndustry[i-1]=itemslist[i].split("<br>")[0];
                ItemName[i-1]=itemslist[i].split("<br>")[1];
                ItemCost[i-1]=itemslist[i].split("<br>")[2];
                ItemGST[i-1]=itemslist[i].split("<br>")[3];
            } else {
                ItemIndustry[i - 1] = "Cancel";
                ItemName[i - 1] = "Cancel";
                ItemCost[i - 1] = "Cancel";
                ItemGST[i - 1] = "Cancel";
            }
        }

        String[] uniqueitems = new HashSet<String>(Arrays.asList(ItemIndustry)).toArray(new String[0]);

//        String[] itemsdata = new String[ItemIndustry.length+1];
//        itemsdata[0] = ItemIndustry[0];
//        for(int i =1; i<ItemIndustry.length;i++) {
//            boolean isstere = false;
//            for(int j =1; j<itemsdata.length;j++) {
//                if(itemsdata[j].matches(ItemIndustry[i])){
//                    isstere=true;
//                }
//            }
//            if(!isstere){
//                itemsdata[i]
//            }
//        }
//        String[] itemsdata = new String[ItemIndustry.length+1];
//        for(int i =0; i < ItemIndustry.length)
//        itemsdata[ItemIndustry.length]="Cancel";

        final CharSequence items[];
        items = uniqueitems;
//        items.add = "Cancel";



//        final CharSequence[] items = { "Clothing", "Food", "Amusement", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Industry!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                } else {
                    getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("industry", items[item].toString()).apply();
//                    Toast.makeText(MainActivity.this, "Clothing", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, AddItemActivity.class));
                    finish();
                }
            }
        });
        builder.show();
    }

}
