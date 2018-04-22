package com.senr.ichra.dom011;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpInfoPage extends Activity {

    private static final String TAG = "SignUpInfoPage";
    private EditText mEmail, mPassword, mPhone, mName;
    private Button mRegistration;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private boolean isLoged = false;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_info);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    isLoged = true;
                    setDataSharedPre();

                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                    }
                                }
                            });
                    Intent intent = new Intent(SignUpInfoPage.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mEmail = (EditText) findViewById(R.id.editTextUserEmailSign);
        mPassword = (EditText) findViewById(R.id.editTextPassSign);
        mPhone = (EditText) findViewById(R.id.editTextPhoneSign);
        mName = (EditText) findViewById(R.id.editTextUserNameSign);

        mRegistration = (Button) findViewById(R.id.buttonSign);

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpInfoPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpInfoPage.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("DriverTable").child(user_id);
                            current_user_db.child("Name").setValue(mName.getText().toString().trim());
                            current_user_db.child("Email").setValue(email);
                            current_user_db.child("Phone").setValue(mPhone.getText().toString().trim());
                            current_user_db = FirebaseDatabase.getInstance().getReference().child("GuardianTable").child(user_id);
                            current_user_db.child("email").setValue("no guardian added");
                            current_user_db = FirebaseDatabase.getInstance().getReference().child("openedGuardian").child(user_id);
                            current_user_db.child("grdOpenTime").setValue("not open yet");

                        }
                    }
                });
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    public void setDataSharedPre() {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoged", isLoged);
        editor.commit();
        editor.apply();
        Log.e("setDataSharedPre", " " + isLoged);

    }

}
