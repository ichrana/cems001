package com.senr.ichra.dom011;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddGuardianPage extends Activity {
    EditText nameEditText,emailEditText;
    Button addBtn;
    String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_guardian_page);

        nameEditText = (EditText) findViewById(R.id.editTextNameGuardian);
        emailEditText = (EditText) findViewById(R.id.editTextEmailGuardian);
        addBtn = (Button) findViewById(R.id.buttonAddGuardian);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name =  nameEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                Log.e("AddGuardianPage", "onClick: name " + name);
                Log.e("AddGuardianPage", "onClick: email " + email);

                if(email.contains("@")&&email.contains(".")) { // any email contains a @ and .
                    // Reference to DB, particularly GuardianTable
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("GuardianTable");

                    // add guardian's name and email to DB
                    current_user_db.child(Main2Activity.userId).child("name").setValue(name);
                    current_user_db.child(Main2Activity.userId).child("email").setValue(email);

                    // msg to user
                    Toast.makeText(AddGuardianPage.this, "Guardian added..", Toast.LENGTH_SHORT).show();

                    // move to the main page
                    Intent intent = new Intent(AddGuardianPage.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(AddGuardianPage.this, "invalid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
