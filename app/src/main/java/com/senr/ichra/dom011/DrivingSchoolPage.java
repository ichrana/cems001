package com.senr.ichra.dom011;

import android.app.Activity;
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
import android.widget.Button;
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

public class DrivingSchoolPage extends Activity { // same CSCPage

    private ListView listView;
    private static final String TAG = "DrivingSchoolPage";
    private ArrayList<Place> arraylist;
    private String url;
    private String name;
    private String phone;
    private String rating;
    private double lat;
    private double lng;
    int i;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        arraylist = new ArrayList<Place>();

        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("DrivingSchoolTable");

        // Retrieve new posts as they are added to Firebase
        String count = "000";
        Log.e(TAG, "count: " + count);

        //todo i
        for (i = 1; i <= 2; i++) {
            count = "00" + i;
            Log.e(TAG, "count: " + count);

            current_user_db.child(count).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();

                            name = (String) map.get("name");
                            Log.e(TAG, "name: " + name);

                            lat = (double) map.get("lat");
                            Log.e(TAG, "lat: " + lat);

                            lng = (double) map.get("lng");
                            Log.e(TAG, "lng: " + lng);

                            url = (String) map.get("URL");
                            Log.e(TAG, "url: " + url);

                            rating = String.valueOf(map.get("Rating"));
                            Log.e(TAG, "Rating: " + rating);

                            phone = (String) map.get("phone");
                            Log.e(TAG, "phone: " + phone);

                            arraylist.add(new Place(name, phone, rating, lat, lng, url));


                            if (i == 3) {
                                Log.e(TAG, "i==3 ");

                                afr();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
        }

    }

    public void afr() {

        setContentView(R.layout.driving_school_page);
        Log.e(TAG, "afr");

        String items[] = new String[arraylist.size()];

        arraylist = HaversineFormula.distanceCalculate(arraylist, Main2Activity.currentLat, Main2Activity.currentlng);

        Collections.sort(arraylist, new Comparator<Place>() {
                    @Override
                    public int compare(Place p1, Place p2) {
                        return Double.compare(p1.getDistance(), p2.getDistance());
                    }
                }
        );

        listView = findViewById(R.id.ListViewDrivingSchool);

        ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arraylist) {

            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(arraylist.get(position).getName());

                text2.setText("Phone: " + arraylist.get(position).getPhone() + " Rating:" + arraylist.get(position).getRating()+ " Distance: "+Math.round (arraylist.get(position).getDistance())+" KM");

                text1.setTextColor(Color.WHITE);
                text2.setTextColor(Color.WHITE);

                return view;
            }
        };

        listView.setAdapter(adapter);

        Log.e(TAG, "adapter " + adapter);
        Log.e(TAG, "items " + items[0]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                String url = arraylist.get(itemPosition).getUrl();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }

        });
    }
}
