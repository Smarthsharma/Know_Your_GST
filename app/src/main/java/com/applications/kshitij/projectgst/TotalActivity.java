package com.applications.kshitij.projectgst;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class TotalActivity extends AppCompatActivity {

    String[] items;
    ArrayList<String> listitemsFL;
    ArrayAdapter<String> adapter;
    ListView listVwFL;

    TextView txtamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        listVwFL = (ListView) findViewById(R.id.Finallistview);
        txtamount = (TextView) findViewById(R.id.txtamount);

        getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("TotalAmount", "0").apply();
//

        getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("Itemlist", "There is no Item selected").apply();
//Itemlist
        initList();


        listVwFL.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                String itemselected = String.valueOf(adapterView.getItemAtPosition(pos));
                Deleteentry(itemselected,pos);
                return true;
            }
        });
//
//        listVwFL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//                String itemselected = String.valueOf(adapterView.getItemAtPosition(pos));
//                if (!itemselected.matches( "There is no Item selected")) {
//                    String name_of_item_selected = itemselected.split("\t:\t")[0];
//                    changeItemQuantity( name_of_item_selected , pos);
//                }
//            }
//        });
//
//        listVwFL.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
//                String itemselected = String.valueOf(adapterView.getItemAtPosition(pos));
//                Deleteentry(itemselected,pos);
//                return true;
//            }
//        });
    }
//
    public void initList(){
        changeamount();
        items=  getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("Itemlist", "There is no Item selected").split("\n<br>\n");;
        listitemsFL = new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<>(TotalActivity.this,R.layout.layout_list_item_final_list,R.id.txtitemFL,listitemsFL);
        listVwFL.setAdapter(adapter);
    }

    private void changeamount() {
        txtamount.setText(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("TotalAmount", "0"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }


    public void SubmitFinalList(View v){

        if(!getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("Itemlist", "There is no Item selected").toString().matches("There is no Item selected"))
        {startActivity(new Intent(this, BillActivity.class));
        finish();}
        else {
            Toast.makeText(this, "Add Items to the list before generating the Bill", Toast.LENGTH_SHORT).show();
        }
    }


    public void Deleteentry(final String items_name, final int position) {
        final AlertDialog.Builder builderAl = new AlertDialog.Builder(TotalActivity.this);
        String Strmsg = "Do you want to delete : " + items_name;
        builderAl.setMessage(Strmsg);
        builderAl.setCancelable(true);

        //Positive Button For Interface
        builderAl.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position);
                reducetotalamount(items_name);
            }
        });

        //Negative button for interface
        builderAl.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builderAl.create();
        alertDialog.show();

    }

    private void reducetotalamount(String s) {
        int quantity = Integer.valueOf(s.split("\n")[1]);
        int gst = Integer.valueOf(s.split("\n")[2]);
        int cost = Integer.valueOf(s.split("\n")[3]);
        double amaount = ( quantity * cost )  +  ( quantity * cost * gst/100 );

        double total = Double.valueOf(getSharedPreferences("AppData", Context.MODE_PRIVATE).getString("TotalAmount", "0"));
        txtamount.setText(String.valueOf(total-amaount));

    }


    public void removeItem(int pos) {
        listitemsFL.remove(pos);
        if(listitemsFL.isEmpty()){
            listitemsFL.add("There is no Item selected");
        }
        String fi = ListToString();
        getSharedPreferences("AppData", Context.MODE_PRIVATE).edit().putString("Itemlist", fi).apply();
        initList();
    }

    public String ListToString() {
        final String[] x = {"" };
        for (int k=0;k<listitemsFL.size();k++){
            if (k==0){
                x[0] = listitemsFL.get(k);
            }else{
                x[0] = x[0] + "\n<br>\n" + listitemsFL.get(k);
            }
        }
        return x[0];
    }



    //
//    public void removeItem(int pos) {
//        listitemsFL.remove(pos);
//        if(listitemsFL.isEmpty()){
//            listitemsFL.add("There is no Item selected");
//        }
//        String fi = ListToString();
//        getSharedPreferences("Temp_list_variable", Context.MODE_PRIVATE).edit().putString("Itemlist", fi).apply();
//        initList();
//    }
//
//    public void changeItemQuantity(final String name_of_item_selected, final int pos){
//        LayoutInflater inflater = LayoutInflater.from(TotalActivity.this);
//        View subView = inflater.inflate(R.layout.layout_list_item_final_list,null);
//        final EditText etAddQuantityET=(EditText) subView.findViewById(R.id.et_Name);
//        final EditText etAddQuantityET=(EditText) subView.findViewById(R.id.et_Name);
//        final EditText etAddQuantityET=(EditText) subView.findViewById(R.id.et_Name);
//        etAddQuantityET.setHint("Quantity");
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Change Quantity");
//        builder.setMessage("Enter Quantity of Items(" + name_of_item_selected + ") :\n(in numbers)");
//        builder.setView(subView);
//        AlertDialog alertDialog = builder.create();
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (etAddQuantityET.getText().toString().isEmpty()) {       //checks if empty
//                    dialog.cancel();
//                    Toast.makeText(FinalListActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
//                } else {
//                    if(etAddQuantityET.getText().toString().matches("0")){
//                        removeItem(pos);
//                    }else{
//                        if( etAddQuantityET.getText().toString().matches("\\d+") ) {
//                            listitemsFL.remove(pos);                                                                         //removing the to be changed listitem
//                            listitemsFL.add(name_of_item_selected + "\t:\t"+ etAddQuantityET.getText().toString());             //adding the changd item
//                            String fi = ListToString();                                                                   //saving to ItemlistShardepref
//                            getSharedPreferences("Temp_list_variable", Context.MODE_PRIVATE).edit().putString("Itemlist", fi).apply();
//                        }else{
//                            dialog.cancel();
//                            Toast.makeText(FinalListActivity.this, "Enter Numeric characters only", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//        //alertDialog.show();
//    }
    public void AddMoreItems(View v){
        startActivity(new Intent(TotalActivity.this, MainActivity.class));
    }


}
