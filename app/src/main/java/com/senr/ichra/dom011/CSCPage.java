package com.senr.ichra.dom011;

import android.app.Activity;
import android.app.ProgressDialog;
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

import java.util.*;


public class CSCPage extends Activity {

    private ListView listView;
    private static final String TAG = "CSCPage";
    private ArrayList<Place> arraylist;
    private ArrayList<Place> SUBarraylist;
    private String url;
    private String name;
    private String phone;
    private String rating;
    private double lat;
    private double lng;
    int i;
    ArrayAdapter<String> adapter;
    Button buttoMore;
    boolean isMoreClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttoMore = (Button) findViewById(R.id.buttoMore);

        // create arraylist of Place objects
        arraylist = new ArrayList<Place>();

        //ref to DB, particularly CSCTable
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("CSCTable");

        String count = "000";
        Log.e(TAG, "count: " + count);

        // loop to read from DB
        for (i = 1; i <= 15; i++) {
            count = "00" + i;
            if (i >= 10)
                count = "0" + i;
            Log.e(TAG, "count: " + count);

            // Retrieve new posts as they are added to Firebase
            current_user_db.child(count).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) { // dataSnapshot = the data we get it from DB

                            // put dataSnapshot in a Map obj (Map like array or List -not map map-)
                            java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();

                            // get name from map obj
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

                            // make new obj of Place class and add it to our arraylist
                            arraylist.add(new Place(name, phone, rating, lat, lng, url));

                            if (i == 16) { // make sure that is last thing after make our arraylist full
                                Log.e(TAG, "i==16 ");

                                afr(); // call this method after make sure that data are read from DB 
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {/*nothing*/}
                    }
            );
        }
    }

    public void afr() {// this method will fill our list that is showed to our user

        setContentView(R.layout.activity_cscpage); // now the user can see our layout (U CAN TEST: TRUN OFF WIFI AND OPEN THIS PAGE -NOTHING SHOW TO U- )

        Log.e(TAG, "afr");

        // call distanceCalculate, the return will be arraylist with calculated distance bettwen curten place and user loction
        arraylist = HaversineFormula.distanceCalculate(arraylist, Main2Activity.currentLat, Main2Activity.currentlng);

        // sort our arraylist by distance
        Collections.sort(arraylist, new Comparator<Place>() {
                    @Override
                    public int compare(Place p1, Place p2) {
                        return Double.compare(p1.getDistance(), p2.getDistance());
                    }
                }
        );

        listView = findViewById(R.id.ListViewCSC);

        // make small list of 5 items
        if (arraylist.size() > 6 && !isMoreClicked) { // if arraylist's size more than 6 and btn 'MORE' not clicked

            // make sub arraylist from our original arraylist
            SUBarraylist = new ArrayList<Place>(arraylist.subList(0, 5));

            // make adapter to fill out the list
            ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this, android.R.layout.simple_list_item_2, android.R.id.text1, SUBarraylist) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) { // make our list row by row

                    View view = super.getView(position, convertView, parent);

                    // text1 = first line in list item (row)
                    // text2 = second line in list item (row)
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    //fill first line and second line
                    text1.setText(arraylist.get(position).getName());
                    text2.setText("Phone: " + arraylist.get(position).getPhone() + " Rating: " + arraylist.get(position).getRating() + " Distance: " + Math.round(arraylist.get(position).getDistance()) + " KM");

                    // set color to our lines
                    text1.setTextColor(Color.WHITE);
                    text2.setTextColor(Color.WHITE);

                    return view;
                }
            };

            listView.setAdapter(adapter);
            Log.e(TAG, "adapter " + adapter);

            // when the item list (row) clicked
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

                    // take url from arraylist
                    String url = arraylist.get(itemPosition).getUrl();

                    // when clicked open the url
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }

            });

        }
        // make full list
        else {
            // same Concept, code

            ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arraylist) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);

                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(arraylist.get(position).getName());

                    text2.setText("Phone: " + arraylist.get(position).getPhone() + " Rating: " + arraylist.get(position).getRating() + " Distance: " + Math.round(arraylist.get(position).getDistance()) + " KM");

                    text1.setTextColor(Color.WHITE);
                    text2.setTextColor(Color.WHITE);

                    return view;
                }
            };

            listView.setAdapter(adapter);
            Log.e(TAG, "adapter " + adapter);
//
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

    public void onClickButtonMore(View view) {
        isMoreClicked = true;
        afr();
    }
}

