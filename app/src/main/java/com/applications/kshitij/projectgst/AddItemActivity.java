package com.applications.kshitij.projectgst;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddItemActivity extends AppCompatActivity {

    TextView industryName;
    EditText edit_name;
    EditText edit_quantity;
    EditText edit_gst;
    EditText edit_cost;
    TextView txtamount;
    Button buttonItem;

    double Calcost;
    double TotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        industryName = (TextView) findViewById(R.id.industryName);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_quantity = (EditText) findViewById(R.id.edit_quantity);
        edit_gst = (EditText) findViewById(R.id.edit_gst);
        edit_cost = (EditText) findViewById(R.id.edit_cost);
        txtamount = (TextView) findViewById(R.id.txtamount);
        buttonItem = (Button) findViewById(R.id.buttonItem);
        edit_quantity.requestFocus();


        industryName.setText(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("industry","Industry Name"));
        TotalAmount= Double.valueOf(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("TotalAmount", "0"));
        txtamount.setText(String.valueOf(TotalAmount));

        edit_quantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    if( !edit_cost.getText().toString().isEmpty() &&
                            !edit_gst.getText().toString().isEmpty() &&
                            !edit_name.getText().toString().isEmpty()){

                        int editquant = 0;
                        Integer editcost = 0 ;
                        Integer editgst  = 0;

                            editquant=Integer.valueOf(edit_quantity.getText().toString());
//                          editquant = Integer.valueOf(edit_quantity.toString().replaceAll("[^0-9.]", ""))
                            editcost = Integer.valueOf(edit_cost.getText().toString().replaceAll("[^0-9.]", ""));
                            editgst = Integer.valueOf(edit_gst.getText().toString().replaceAll("[^0-9.]", ""));

                        Calcost =  ( editquant * editcost )  +  ( editquant * editcost * editgst/100 );
                        txtamount.setText(String.valueOf(TotalAmount)+" + "+String.valueOf(Calcost));
                    }
            }
        });

    }

    public void intenttototal(View v){
        String Itemlist = edit_name.getText().toString()        + "\n" +
                            edit_quantity.getText().toString()  + "\n" +
                            edit_gst.getText().toString()       + "\n" +
                            edit_cost.getText().toString() ;
        String OldtringItemsList=getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("Itemlist","There is no Item selected");
        if(OldtringItemsList.equals("There is no Item selected")){
            getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("Itemlist",Itemlist).apply();
        } else {
            getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("Itemlist",OldtringItemsList+"\n<br>\n"+Itemlist).apply();
        }


        //set total amount in sharedPref
        double amounttotal =  TotalAmount + Calcost;
        getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("TotalAmount", String.valueOf(amounttotal)).apply();


//        Toast.makeText(this, OldtringItemsList, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, Itemlist, Toast.LENGTH_SHORT).show();

//        startActivity(new Intent(this, TotalActivity.class));
        finish();
    }


    public void selectItem(View v){
        String[] itemslist = getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available").split("<br><hr><br>");
//        Toast.makeText(this, getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available"), Toast.LENGTH_SHORT).show();
        final String[] ItemIndustry = new String[itemslist.length-1], ItemName = new String[itemslist.length-1], ItemCost = new String[itemslist.length-1], ItemGST = new String[itemslist.length-1];
        for(int i =1; i<itemslist.length;i++){
            ItemIndustry[i-1]=itemslist[i].split("<br>")[0];
            ItemName[i-1]=itemslist[i].split("<br>")[1];
            ItemCost[i-1]=itemslist[i].split("<br>")[2];
            ItemGST[i-1]=itemslist[i].split("<br>")[3];
        }


//        String[] items1 = new String[ItemName.length];
//        items1[0]="Cancel";
//        for(int i=0;i<ItemName.length;i++){
//            if(!ItemIndustry[i].equals(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("industry","Industry Name"))) {
//                ItemName[i] = "Cancel";
//            }
//        }


//        String[] uniqueitems = new HashSet<String>(Arrays.asList(ItemName)).toArray(new String[0]);
        final CharSequence[] items = ItemName;


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Industry!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                } else {//if (items[item].equals(items[0])) {
                    edit_name.setText(ItemName[item]);
                    edit_cost.setText(String.valueOf(ItemCost[item].replaceAll("[^0-9.]", "")));
                    edit_gst.setText(String.valueOf(ItemGST[item].replaceAll("[^0-9.]", "")));

//                    getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("industry",items[item].toString()).apply();
//                    getApplicationContext().getSharedPreferences("UserDetails", 0).edit().clear().apply();
//                    getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("name",str_name).apply();
//                    getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("surname",str_surname).apply();
//                    getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("emailid",str_emailid).apply();
                }
            }
        });
        builder.show();
    }


//    public void selectItem(View v){
//        String[] itemslist = getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available").split("<br><hr><br>");
////        Toast.makeText(this, getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available"), Toast.LENGTH_SHORT).show();
//        final String[] ItemIndustry = new String[itemslist.length-1], ItemName = new String[itemslist.length-1], ItemCost = new String[itemslist.length-1], ItemGST = new String[itemslist.length-1];
//        for(int i =1; i<itemslist.length;i++){
//            ItemIndustry[i-1]=itemslist[i].split("<br>")[0];
//            ItemName[i-1]=itemslist[i].split("<br>")[1];
//            ItemCost[i-1]=itemslist[i].split("<br>")[2];
//            ItemGST[i-1]=itemslist[i].split("<br>")[3];
//        }
//
//
//        String[] items1 = new String[ItemName.length];
//        items1[0]="Cancel";
//        int j=1;
//        for(int i=0;i<ItemName.length;i++){
////            if(!ItemIndustry[i].equals(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("industry","Industry Name"))) {
////                ItemName[i] = "Cancel";
////            }
//            if(ItemIndustry[i].equals(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("industry","Industry Name"))) {
//                items1[j]=ItemName[i]; j++;
//            }
//        }
//
//
////        String[] uniqueitems = new HashSet<String>(Arrays.asList(ItemName)).toArray(new String[0]);
//        final CharSequence[] items = items1;
//
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Industry!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                } else {//if (items[item].equals(items[0])) {
//                    for(int i = 0 ; i<ItemName.length; i++){
//                        if(items[item].equals(ItemName[i])){
//                            edit_name.setText(ItemName[i]);
//                            edit_cost.setText(String.valueOf(ItemCost[i].replaceAll("[^0-9.]", "")));
//                            edit_gst.setText(String.valueOf(ItemGST[i].replaceAll("[^0-9.]", "")));
//                        }
//                    }
////                    getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("industry",items[item].toString()).apply();
////                    getApplicationContext().getSharedPreferences("UserDetails", 0).edit().clear().apply();
////                    getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("name",str_name).apply();
////                    getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("surname",str_surname).apply();
////                    getSharedPreferences("UserDetails", Context.MODE_PRIVATE).edit().putString("emailid",str_emailid).apply();
//                }
//            }
//        });
//        builder.show();
//    }



//    public void selectItem(View v){
//        String[] itemslist = getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("AllData","No data Available").split("<br><hr><br>");
//        String[] ItemIndustry = new String[itemslist.length];
//        final String[] ItemName  = new String[itemslist.length];
//        final String[]  ItemCost= new String[itemslist.length];
//        final String[]  ItemGST = new String[itemslist.length];
//
//        for(int i =1; i<itemslist.length;i++){
//            ItemIndustry[i-1]=itemslist[i].split("<br>")[0];
//            ItemName[i-1]=itemslist[i].split("<br>")[1];
//            ItemCost[i-1]=itemslist[i].split("<br>")[2];
//            ItemGST[i-1]=itemslist[i].split("<br>")[3];
//        }
//
////        for(int i =1; i<=itemslist.length;i++){
////            if(i<itemslist.length){
////                ItemIndustry[i-1]=itemslist[i].split("<br>")[0];
////                ItemName[i-1]=itemslist[i].split("<br>")[1];
////                ItemCost[i-1]=itemslist[i].split("<br>")[2];
////                ItemGST[i-1]=itemslist[i].split("<br>")[3];
////            } else {
////                ItemIndustry[i - 1] = "cancel";
////                ItemName[i - 1] = "cancel";
////                ItemCost[i - 1] = "cancel";
////                ItemGST[i - 1] = "cancel";
////            }
////        }
//
////        String[] uniqueitems = new HashSet<String>(Arrays.asList(ItemIndustry)).toArray(new String[0]);
//
////        String[] itemsdata = new String[ItemIndustry.length+1];
////        itemsdata[0] = ItemIndustry[0];
////        for(int i =1; i<ItemIndustry.length;i++) {
////            boolean isstere = false;
////            for(int j =1; j<itemsdata.length;j++) {
////                if(itemsdata[j].matches(ItemIndustry[i])){
////                    isstere=true;
////                }
////            }
////            if(!isstere){
////                itemsdata[i]
////            }
////        }
////        String[] itemsdata = new String[ItemIndustry.length+1];
////        for(int i =0; i < ItemIndustry.length)
////        itemsdata[ItemIndustry.length]="Cancel";
//
//        final CharSequence items[];
//        items = ItemName;
////        items.add = "Cancel";
//
//
//
////        final CharSequence[] items = { "Clothing", "Food", "Amusement", "Cancel" };
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Industry!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                } else {
//                    edit_name.setText(ItemName[item]);
//                    edit_cost.setText(ItemCost[item]);
//                    edit_gst.setText(ItemGST[item]);
//                }
//            }
//        });
//        builder.show();
//    }


}
