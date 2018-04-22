package com.senr.ichra.dom011;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmergencyNoPage extends AppCompatActivity { // same CSCPage

    private ListView listView;
    private static final String TAG = "EmergencyNoPage";
    private ArrayList<EmergencyNo> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_no_page);
        arraylist = new ArrayList<EmergencyNo>();

        arraylist.add(new EmergencyNo("General Emergency Number", 112));
        arraylist.add(new EmergencyNo("Fire / Recue", 998));
        arraylist.add(new EmergencyNo("Police (Crimes / Robbery)", 999));
        arraylist.add(new EmergencyNo("Traffic Police / Traffic Accidents", 993));
        arraylist.add(new EmergencyNo("Highway Patrol (highway emergencies)", 996));
        arraylist.add(new EmergencyNo("Ambulance (Medical Emergencies)", 997));

        listView = findViewById(R.id.ListViewEmergencyNo);

        ArrayAdapter<EmergencyNo> adapter = new ArrayAdapter<EmergencyNo>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arraylist) {

            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(arraylist.get(position).getService());

                text2.setText(arraylist.get(position).getNumber() + "");

                text1.setTextColor(Color.WHITE);
                text2.setTextColor(Color.WHITE);

                return view;
            }

        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                String phon = String.valueOf(arraylist.get(itemPosition).getNumber()) ;

                // when clicked it will go to dialer to make the call
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.fromParts("tel",phon,null));
                startActivity(i);

            }

        });
    }
}
