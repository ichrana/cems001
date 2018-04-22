package com.senr.ichra.dom011;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MaleFunctionsPage extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "MaleFunctionsPage";
    private ArrayList<MaleFunctions> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.male_functions_page);

        arraylist = new ArrayList<MaleFunctions>();

        arraylist.add(new MaleFunctions("Uneven Tire Wear",
                "The easiest way is to jack up your car and inspect each tire individually, noting whether there are any bald spots on the inside or outside of the tire, or whether there are any dips and dents in the tire tread, Rotating your tires and having your wheels aligned regularly."
        ));
        arraylist.add(new MaleFunctions("Starting the Engine",
                "There are a number of reasons which can cause a car engine not to start, the most common, of course, is a dead battery. Pay special attention to the noise it makes when you turn the key. Is the car completely silent? If so, there may be a problem with your battery terminal cable connections. Does your car crank over but not start? Then it may be your spark plugs or fuel supply to your engine. In any case, if you’re out on the road, try jumpstarting your car then investigating the cause further when you’re safely back at home."
        ));
        arraylist.add(new MaleFunctions("Air Conditioner not Working",
                "The most likely cause of this is that there is no refrigerant left in your system. This could be caused by a leak in your system somewhere, which will have to be fixed before refilling the refrigerant. If you’re car-savvy and you own a set of air conditioning gauges, refilling the refrigerant is usually easy to do yourself. However, if you’re not so confident, enlist the help of a knowledgeable friend or take a quick trip to the mechanic."
        ));
        arraylist.add(new MaleFunctions("Engine Overheating",
                "Overheating can be caused by a few different factors. The simplest cause may be that your car needs more coolant. Yet depleted coolant can be caused by the bigger problem, like leaks or faulty hoses, so always check for the underlying cause before simply filling it up with more. Another common reason for overheating may be that the radiator fan which keeps your engine cool is faulty, so check your fan motor connection and fan thermostat."
        ));
        arraylist.add(new MaleFunctions("Noisy Brakes",
                "There could be a number of reasons for noisy brakes. It could be that your brake pads are loose, worn out, or you may have brake dust inside the drum. If you can’t see anything wrong with your brake pads, and you suspect it may be brake dust, it may be best to leave this to a professional – brake dust can be extremely dangerous if accidentally inhaled."
        ));
        arraylist.add(new MaleFunctions("Vibration in Steering Wheel",
                "Could potentially be related to suspension with the ball joint. Or engine mount has been broken. Alignment out with balancing issue. also check the wheels, axle which can also cause vibration in the steering wheel of the car"
        ));
        arraylist.add(new MaleFunctions("Car Tilt Sideways",
                "When car does not move in a straight line or tilt sideways during driving, the most probable issue is inaccurate tire pressure or misaligned tires and needs wheel alignment. In other extreme cases, the bushes, coil springs would need replacement"
        ));
        arraylist.add(new MaleFunctions("Water Pump Does Not Work",
                "The openings from which the water pump are clogged with sediment, try to clean it slowly and check on the fuse and a check on the dynamo and fill it out with distilled water"
        ));

        listView = findViewById(R.id.ListViewMaleFunctions);
        ArrayAdapter<MaleFunctions> adapter = new ArrayAdapter<MaleFunctions>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arraylist) {

            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(arraylist.get(position).getProblem());

                text2.setText(" ");

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

                Intent intent = new Intent(MaleFunctionsPage.this, MaleFunctionsSuggestionPage.class);
                intent.putExtra("Suggestion", arraylist.get(position).getSuggestion());
                startActivity(intent);
            }

        });

    }
}
