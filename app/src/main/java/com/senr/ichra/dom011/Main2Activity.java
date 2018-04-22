package com.senr.ichra.dom011;

import android.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static javax.mail.Session.getDefaultInstance;

public class Main2Activity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    public static double currentLat, currentlng;
    public static String userId;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = "Main2Activity";
    private String driverName, driverEmail, rec, subject, textMessage, DestionPlaceID, guardianName;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng destinationLatLng;
    private boolean isFisteCheckOnDesExits = false, isnearToDes = false, isLoged = false, setCameraFirstTime = false, setCameraSecondTime = false;
    private SupportMapFragment mapFragment;
    private Switch mTrackingSwitch;
    private ImageView mPlacePicker, mRemovePicker, imageViewNavHeader;
    private GeoDataClient mGeoDataClient;
    private Marker mmarker;
    private DatabaseReference ref;
    private double lat, lng, nearToDes;
    private LatLng location;
    private Date currentTime;
    private Session session = null;
    private Context context = null;
    private View mview;
    private TextView textViewNameNavHeader, textViewEmailNavHeader;
    private boolean checkForOpenLink = false;
    private boolean isOpenLink = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //showing the layout content
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*
        **************************************
        *
        * this button used when the driver (our user)
        * want to send Email to his guardian
        **************************************
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mview = view;

                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("GuardianTable").child(Main2Activity.userId);
                current_user_db.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();

                                if (!map.get("email").equals("no guardian added")) {
                                    Snackbar.make(mview, "Message sent", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    rec = (String) map.get("email");
                                    guardianName = (String) map.get("name");
                                    sendEmail();
                                    checkForOpenLink = true;
                                } else {
                                    Snackbar.make(mview, "no guardian added", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        }
                );
            }
        });

        /*
        **************************************
        *
        * Construct DrawerLayout, ActionBarDrawerToggle,NavigationView
        *
        **************************************
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Obtain user ID from FirebaseAuth
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Obtain reference of DriverTable
        ref = FirebaseDatabase.getInstance().getReference("DriverTable");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a GeoDataClient. (Google Places Geo Data API)
        mGeoDataClient = Places.getGeoDataClient(this, null);

         /*
        **************************************
        *
        * Obtain the Tracking Switch
        *
        **************************************
         */
        mTrackingSwitch = (Switch) findViewById(R.id.workingSwitch);
        mTrackingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // if the Switch is ON
                    connectDriver();
                } else { // if the Switch is OFF
                    disconnectDriver();
                    RemovePicker();
                }
            }
        });

         /*
        **************************************
        *
        * Construct the Place Picker
        *
        * its a part of the Google Places API for Android.
        *
        **************************************
         */
        mPlacePicker = (ImageView) findViewById(R.id.ImageViewPlacePicker);
        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(Main2Activity.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage());
                }
            }
        });

        /*
        **************************************
        *
        * Obtain the Remove Place Picker
        *
        **************************************
         */
        mRemovePicker = (ImageView) findViewById(R.id.ImageViewRemovePicker);
        mRemovePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemovePicker();
            }
        });

        /*
        **************************************
        *
        * Construct the navigationView header
        *
        * the header show the user information
        * so we need to concat our dataBase to get user's information
        *
        *
        **************************************
         */
        View headViwe = navigationView.getHeaderView(0);
        imageViewNavHeader = headViwe.findViewById(R.id.imageViewNavHeader);
        textViewNameNavHeader = headViwe.findViewById(R.id.textViewNameNavHeader);
        textViewEmailNavHeader = headViwe.findViewById(R.id.textViewEmailNavHeader);

        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();
                driverName = (String) map.get("Name");
                driverEmail = (String) map.get("Email");
                Log.e(TAG, "driverName driverEmail " + driverName + " " + driverEmail);

                setHeaderTextViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }// onCreat() END


    // see if the tacking link is opened
    void openLink() {
        Log.e(TAG, "openLink() " + checkForOpenLink);

        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("openedGuardian").child(Main2Activity.userId);
        current_user_db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();

                        if (!map.get("grdOpenTime").equals("not open yet")) {
                            Log.e(TAG, "!map.get|checkForOpenLink " + checkForOpenLink);

                            Snackbar.make(mview, guardianName + " is open you link", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            rec = driverEmail;
                            isOpenLink = true;
                            sendEmail();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }

    /*
        **************************************
        *
        * this method used by
        * navigationView header
        *
        * its set the header's content
        *
        **************************************
         */
    public void setHeaderTextViews() {
        textViewNameNavHeader.setText(driverName);
        textViewEmailNavHeader.setText(driverEmail);
        imageViewNavHeader.setImageDrawable(getDrawable(R.drawable.logo));
    }

    /*
        **************************************
        *
        * this 3 methods belows
        * onCreateOptionsMenu
        * onOptionsItemSelected
        * onNavigationItemSelected
        *
        *
        * its Handles the navigationView and has menu items
        *
        **************************************
         */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Guardian) {
            // Handle the Guardian page
            Intent intent = new Intent(Main2Activity.this, ListGuardianActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_EmergencyNO) {
            // Handle the Emergency Number page
            Intent intent = new Intent(Main2Activity.this, EmergencyNoPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_ImportantMal) {
            // Handle the Important Malfunctions page
            Intent intent = new Intent(Main2Activity.this, MaleFunctionsPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_CSC) {
            // Handle the Car Service Centers page
            Intent intent = new Intent(Main2Activity.this, CSCPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_DSchools) {
            // Handle the Driving Schools page
            Intent intent = new Intent(Main2Activity.this, DrivingSchoolPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_SignOut) {
            // Handle the Sign Out
            isLoged = false;
            setDataSharedPre();
            disconnectDriver();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Main2Activity.this, LogOrSign.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
       **************************************
       *
       * this 2 methods belows
       * sendEmail
       * doInBackground on class RetreiveFeedTask
       *
       * its Handles sending email to the guardian
       *
       **************************************
        */
    public void sendEmail() {
        Log.e(TAG, "send|checkForOpenLink " + checkForOpenLink);
        Log.e(TAG, "send|isOpenLink " + isOpenLink);

        if (isOpenLink) {
            isOpenLink = false;
            checkForOpenLink = false;
            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("openedGuardian").child(userId);
            current_user_db.child("grdOpenTime").setValue("not open yet");

            subject = guardianName + "just opened your link - CEMS APP";
            textMessage = guardianName + "open your link now";

        } else {
            subject = driverName + " share location with you - CEMS APP";
            textMessage = driverName + " want to share location with you..!" + "    " + "On this link:"
                    + "http://cems.co.nf/app/track.html?ID=" + userId;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noReplyCEMS0@gmail.com", "1995_EER");
            }
        });

        Main2Activity.RetreiveFeedTask task = new Main2Activity.RetreiveFeedTask();
        task.execute();
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("noReplyCEMS0@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

    /*
      **************************************
      *
      * this 5 methods belows
      * onDesExits
      * RemovePicker
      * onActivityResult
      * getPlaceDes
      * OnNearToDes
      *
      *
      *
      * its Handles user's destination
      *
      **************************************
       */
    private void onDesExits() {
        isFisteCheckOnDesExits = true;
        Log.e(TAG, "ref.child(userId).child(\"destination\").child(\"Lat\").toString(): " + ref.child(userId).child("destination").child("Lat").toString());

        ref.child(userId).child("destination").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                java.util.Map<String, Object> map = (java.util.Map<String, Object>) dataSnapshot.getValue();
                if (null != map) {
                    lat = (double) map.get("Lat");
                    lng = (double) map.get("Lng");
                    Log.e(TAG, "lat: " + lat);
                    Log.e(TAG, "lng: " + lng);

                    location = new LatLng(lat, lng);
                    Log.e(TAG, "location: " + location);

                    destinationLatLng = new LatLng(lat, lng);
                    isnearToDes = true;

                    mmarker = mMap.addMarker(new MarkerOptions().position(location).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)));
                    Log.e(TAG, "mmarker: " + mmarker);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void RemovePicker() {
        if (mmarker != null) {
            destinationLatLng = null;
            isnearToDes = false;
            mmarker.remove();
            ref.child(userId).child("destination").removeValue();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlacePicker.getPlace(data, this);
                DestionPlaceID = place.getId();

                getPlaceDes();
            }
        }
    }

    private void getPlaceDes() {
        mGeoDataClient.getPlaceById(DestionPlaceID).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    com.google.android.gms.location.places.Place myPlace = places.get(0);

                    final LatLng location = myPlace.getLatLng();

                    Log.i(TAG, "Place found: " + myPlace.getName() + " " + location.latitude + " " + location.longitude);

                    ref.child(userId).child("destination").child("Lat").setValue(location.latitude);
                    ref.child(userId).child("destination").child("Lng").setValue(location.longitude);
                    destinationLatLng = new LatLng(location.latitude, location.longitude);
                    isnearToDes = true;

                    if (mmarker != null) {
                        mmarker.remove();
                    }

                    mmarker = mMap.addMarker(new MarkerOptions().position(location).title(myPlace.getName() + "").icon(BitmapDescriptorFactory.fromResource(
                            R.drawable.ic_flag)));

                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });
    }

    private void OnNearToDes(double nearToDes) {
        if (nearToDes < 0.030) { // if the distance between the driver location and his destination (= nearToDes) is smaller than 0.030
            RemovePicker();
            Toast.makeText(getApplicationContext(), "You arrived", Toast.LENGTH_LONG).show();
        }
    }

     /*
        **************************************
        *
        * this 2 methods belows
        * onMapReady
        * onLocationResult
        *
        * its Handles the map and obtain user's location
        *
        **************************************
         */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mTrackingSwitch.setChecked(true);
            } else {
                checkLocationPermission();
            }
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {

                mLastLocation = location;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                if (!setCameraFirstTime || !setCameraSecondTime) {

                    if (setCameraFirstTime)
                        setCameraSecondTime = true;

                    setCameraFirstTime = true;
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }

                currentTime = Calendar.getInstance().getTime();
                Log.e(TAG, "currentTime: " + currentTime);
                ref.child(userId).child("lastUpdate").setValue(currentTime.getTime());

                currentLat = location.getLatitude();
                currentlng = location.getLongitude();

                if (isnearToDes) {//destination mark will delete automatically when the driver arrived 
                    nearToDes = HaversineFormula.distanceCalculate(destinationLatLng.latitude, destinationLatLng.longitude, currentLat, currentlng);
                    OnNearToDes(nearToDes);
                }

                // check link opened?
                if (checkForOpenLink)
                    openLink();

                GeoFire geoFire = new GeoFire(ref.child(userId).child("location"));
                geoFire.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
            }
        }
    };


    /*
        **************************************
        *
        * this 2 methods belows
        * connectDriver
        * disconnectDriver
        *
        *
        * its Handles the user location
        * and save it to DB
        * when its disconnect, the location will delete from DB
        *
        **************************************
         */
    private void connectDriver() {
        checkLocationPermission();
        //FusedLocationClient is GoogleApi-based API
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        mMap.setMyLocationEnabled(true);
        if (!isFisteCheckOnDesExits)
            onDesExits();
    }

    private void disconnectDriver() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        GeoFire geoFire = new GeoFire(ref.child(userId).child("location"));
        geoFire.removeLocation(userId);
    }

    /*
        **************************************
        *
        * this 2 methods belows
        * checkLocationPermission
        * onRequestPermissionsResult
        *
        *
        * its Handles the Location Permission
        *
        **************************************
         */
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                        mTrackingSwitch.setChecked(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please provide the permission", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(this)
                            .setTitle("Give Location Permission")
                            .setMessage("Please provide the permission")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                                }
                            })
                            .create()
                            .show();
                }
                break;
            }
        }
    }


    /*
        **************************************
        *
        * this 2 methods belows
        * setDataSharedPre
        * getDataSharedPre
        *
        *
        * its Handles the SharedPreferences
        * that are used to save our varbalis
        * on the user's local cache
        *
        **************************************
         */
    public void setDataSharedPre() {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoged", isLoged);
        editor.commit();
        editor.apply();
        Log.e("setDataSharedPre", " " + isLoged);
    }

    public void getDataSharedPre() {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        isLoged = sharedPref.getBoolean("isLoged", false);
        Log.e("getDataSharedPre", " " + isLoged);
    }

    /*
        **************************************
        *
        * this method belows
        * onBackPressed
        *
        *
        * its Handle with back button
        *
        *
        **************************************
         */
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getDataSharedPre();
            if (isLoged) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
