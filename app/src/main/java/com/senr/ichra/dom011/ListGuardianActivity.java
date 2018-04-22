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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListGuardianActivity extends AppCompatActivity { // same CSCPage

    private ListView listView;
    private static final String TAG = "guardianPage";
    private ArrayList<guardian> arraylist;
    private String email;
    private String name;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        arraylist = new ArrayList<guardian>();

        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("GuardianTable");

        current_user_db.child(Main2Activity.userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();

                        if (map != null) {
                            name = (String) map.get("name");
                            Log.e(TAG, "name: " + name);

                            email = (String) map.get("email");
                            Log.e(TAG, "email: " + email);

                            arraylist.add(new guardian(name, email));

                            afr();
                        } else {
                            setContentView(R.layout.activity_list_guardian);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    public void afr() { // after get the data from DB

        setContentView(R.layout.activity_list_guardian);
        Log.e(TAG, "afr");

        listView = findViewById(R.id.ListViewGuardian);

        ArrayAdapter<guardian> adapter = new ArrayAdapter<guardian>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arraylist) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(arraylist.get(position).getName());
                text2.setText(arraylist.get(position).getEmail());

                text1.setTextColor(Color.WHITE);
                text2.setTextColor(Color.WHITE);

                return view;
            }

        };

        listView.setAdapter(adapter);
        Log.e(TAG, "adapter " + adapter);
    }

    public void onClickButtonAddGuardian(View view) {
        Intent intent = new Intent(ListGuardianActivity.this, AddGuardianPage.class);
        startActivity(intent);
    }
}
