package com.senr.ichra.dom011;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button btnSend;
    EditText email;
    FirebaseAuth auth;
    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btnSend = (Button) findViewById(R.id.buttonSendForget);
        email = (EditText) findViewById(R.id.editTextEmailForget);

        // get user info from DB (Firebase)
        auth = FirebaseAuth.getInstance();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress = email.getText().toString();

                // copy past from firebase doc
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Email sent. ", Toast.LENGTH_LONG).show();

                                } else
                                    Toast.makeText(getApplicationContext(), "This Email cant found ", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
