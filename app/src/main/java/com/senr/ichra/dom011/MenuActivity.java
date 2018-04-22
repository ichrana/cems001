package com.senr.ichra.dom011;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity { // ☠☠☠ NOTHING HERE REALLY :) ☠☠☠

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    public void onClickButtonTEESTT(View view)
    {
/*
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("DrivingSchoolTable");

        current_user_db.child("001").child("name").setValue("Dallah Driving School");
        current_user_db.child("001").child("lat").setValue(21.5420874);
        current_user_db.child("001").child("lng").setValue(39.2200983);
        current_user_db.child("001").child("phone").setValue("012 672 4801");
        current_user_db.child("001").child("Rating").setValue(3.3);
        current_user_db.child("001").child("URL").setValue("https://goo.gl/maps/uQ4JgRQmTRp");

        current_user_db.child("002").child("name").setValue("North Jeddah Driving School");
        current_user_db.child("002").child("lat").setValue(21.6348177);
        current_user_db.child("002").child("lng").setValue(39.2132487);
        current_user_db.child("002").child("phone").setValue("012 672 4801");
        current_user_db.child("002").child("Rating").setValue(3.4);
        current_user_db.child("002").child("URL").setValue("https://goo.gl/maps/xe89ajBZggq");


        //☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠


        current_user_db.child("005").child("name").setValue("");
        current_user_db.child("").child("lat").setValue();
        current_user_db.child("").child("lng").setValue();
        current_user_db.child("").child("phone").setValue("");
        current_user_db.child("").child("Rating").setValue();
        current_user_db.child("").child("URL").setValue("");

        /////////////////////////////////////////////////////////////

                 DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("CSCTable");


        current_user_db.child("001").child("name").setValue("Al Zaaman Car Services Center");
        current_user_db.child("001").child("lat").setValue(21.569058400000003);
        current_user_db.child("001").child("lng").setValue(39.169857300000004);
        current_user_db.child("001").child("phone").setValue("+966 50 386 3816");
        current_user_db.child("001").child("Rating").setValue(5.0);
        current_user_db.child("001").child("URL").setValue("https://goo.gl/maps/b4fLyvwgdH72");

        current_user_db.child("002").child("name").setValue("Car Services Center");
        current_user_db.child("002").child("lat").setValue(21.5902876);
        current_user_db.child("002").child("lng").setValue(39.2005146);
        current_user_db.child("002").child("phone").setValue("no phone#");
        current_user_db.child("002").child("Rating").setValue(4.0);
        current_user_db.child("002").child("URL").setValue("https://goo.gl/maps/9VDYyxD2heG2");

        current_user_db.child("003").child("name").setValue("Balance Al-amal computer");
        current_user_db.child("003").child("lat").setValue(21.5849291);
        current_user_db.child("003").child("lng").setValue(39.19111040000001);
        current_user_db.child("003").child("phone").setValue("+966 50 460 0029");
        current_user_db.child("003").child("Rating").setValue(0.0);
        current_user_db.child("003").child("URL").setValue("https://goo.gl/maps/rTDPXJEqRfu");

        current_user_db.child("004").child("name").setValue("Abu Diyab Car Work Shop");
        current_user_db.child("004").child("lat").setValue(21.561989999999998);
        current_user_db.child("004").child("lng").setValue(39.18551);
        current_user_db.child("004").child("phone").setValue("no phone#");
        current_user_db.child("004").child("Rating").setValue(5.0);
        current_user_db.child("004").child("URL").setValue("https://goo.gl/maps/3pmncGcV91F2");


        current_user_db.child("005").child("name").setValue("Quick Lane Ford Al Jazeera Leasing Workshop");
        current_user_db.child("005").child("lat").setValue(21.5756059);
        current_user_db.child("005").child("lng").setValue(39.1909083);
        current_user_db.child("005").child("phone").setValue("no phone#");
        current_user_db.child("005").child("Rating").setValue(3.7);
        current_user_db.child("005").child("URL").setValue("https://goo.gl/maps/2tT6NGocu4q");

        //STH
        current_user_db.child("006").child("name").setValue("Center Najd Car Service");
        current_user_db.child("006").child("lat").setValue(21.4669996);
        current_user_db.child("006").child("lng").setValue(39.2347461);
        current_user_db.child("006").child("phone").setValue("053 491 6595");
        current_user_db.child("006").child("Rating").setValue(0.0);
        current_user_db.child("006").child("URL").setValue("https://goo.gl/maps/WYW9GsACrEw");

        current_user_db.child("007").child("name").setValue("Modern Auto Service Center Waziriya");
        current_user_db.child("007").child("lat").setValue(21.4583758);
        current_user_db.child("007").child("lng").setValue(39.2515644);
        current_user_db.child("007").child("phone").setValue("no phone#");
        current_user_db.child("007").child("Rating").setValue(2.0);
        current_user_db.child("007").child("URL").setValue("https://goo.gl/maps/7sgceRSkwGw");

        current_user_db.child("008").child("name").setValue("Al Jazeera Car Service Center");
        current_user_db.child("008").child("lat").setValue(21.4534134);
        current_user_db.child("008").child("lng").setValue(39.2424244);
        current_user_db.child("008").child("phone").setValue("053 395 6869");
        current_user_db.child("008").child("Rating").setValue(5.0);
        current_user_db.child("008").child("URL").setValue("https://goo.gl/maps/hVv7Tbmckv92");

        current_user_db.child("009").child("name").setValue("DENSO Service Center");
        current_user_db.child("009").child("lat").setValue(21.4790486);
        current_user_db.child("009").child("lng").setValue(39.2159289);
        current_user_db.child("009").child("phone").setValue("012 633 6258");
        current_user_db.child("009").child("Rating").setValue(3.7);
        current_user_db.child("009").child("URL").setValue("https://goo.gl/maps/THFun2qwBaM2");

        current_user_db.child("010").child("name").setValue("Mize Service Centers");
        current_user_db.child("010").child("lat").setValue(21.4902899);
        current_user_db.child("010").child("lng").setValue(39.2195821);
        current_user_db.child("010").child("phone").setValue("012 659 5420");
        current_user_db.child("010").child("Rating").setValue(4.0);
        current_user_db.child("010").child("URL").setValue("https://goo.gl/maps/uGQTWjYuCtE2");

        //NTH
        current_user_db.child("011").child("name").setValue("ACDelco Cars Service Center");
        current_user_db.child("011").child("lat").setValue(21.6936944);
        current_user_db.child("011").child("lng").setValue(39.1028946);
        current_user_db.child("011").child("phone").setValue("no phone#");
        current_user_db.child("011").child("Rating").setValue(0.0);
        current_user_db.child("011").child("URL").setValue("https://goo.gl/maps/QVAddUVNC5C2");

        current_user_db.child("012").child("name").setValue("Eastern Car Service Center");
        current_user_db.child("012").child("lat").setValue(21.6999801);
        current_user_db.child("012").child("lng").setValue(39.2013109);
        current_user_db.child("012").child("phone").setValue("no phone#");
        current_user_db.child("012").child("Rating").setValue(4.5);
        current_user_db.child("012").child("URL").setValue("https://goo.gl/maps/VgnYjJrpCGD2");

        current_user_db.child("013").child("name").setValue("Crown Car Services Center");
        current_user_db.child("013").child("lat").setValue(21.7685329);
        current_user_db.child("013").child("lng").setValue(39.1779395);
        current_user_db.child("013").child("phone").setValue("no phone#");
        current_user_db.child("013").child("Rating").setValue(4.8);
        current_user_db.child("013").child("URL").setValue("https://goo.gl/maps/7WyhuKrRzKH2");

        current_user_db.child("014").child("name").setValue("Lexus Service Center");
        current_user_db.child("014").child("lat").setValue(21.626694);
        current_user_db.child("014").child("lng").setValue(39.1478513);
        current_user_db.child("014").child("phone").setValue("9200 02670");
        current_user_db.child("014").child("Rating").setValue(4.5);
        current_user_db.child("014").child("URL").setValue("https://goo.gl/maps/ZL7mE5stjZM2");

        current_user_db.child("015").child("name").setValue("Hyundai Quick Service Center");
        current_user_db.child("015").child("lat").setValue(21.6233847);
        current_user_db.child("015").child("lng").setValue(39.2051649);
        current_user_db.child("015").child("phone").setValue("9200 28008");
        current_user_db.child("015").child("Rating").setValue(3.9);
        current_user_db.child("015").child("URL").setValue("https://goo.gl/maps/4jXSQSyYu1U2");

                //*****************************************

                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("DrivingSchoolTable");

                current_user_db.child("001").child("name").setValue("Dallah Driving School");
                current_user_db.child("001").child("lat").setValue(21.5420874);
                current_user_db.child("001").child("lng").setValue(39.2200983);
                current_user_db.child("001").child("phone").setValue("012 672 4801");
                current_user_db.child("001").child("Rating").setValue(3.3);
                current_user_db.child("001").child("URL").setValue("https://goo.gl/maps/uQ4JgRQmTRp");

                current_user_db.child("002").child("name").setValue("North Jeddah Driving School");
                current_user_db.child("002").child("lat").setValue(21.6348177);
                current_user_db.child("002").child("lng").setValue(39.2132487);
                current_user_db.child("002").child("phone").setValue("012 672 4801");
                current_user_db.child("002").child("Rating").setValue(3.4);
                current_user_db.child("002").child("URL").setValue("https://goo.gl/maps/xe89ajBZggq");

                /********************

        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("GuardianTable");
        current_user_db.child("001").child("name").setValue("Rana");
        current_user_db.child("001").child("email").setValue("ichranakhader@gmail.com");

         */

        //*****************************************

        //*****************************************

        //☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠

    }
/*
 @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(this, DriverMapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
 */

}
