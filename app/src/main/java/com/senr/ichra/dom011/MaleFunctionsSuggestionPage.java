package com.senr.ichra.dom011;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MaleFunctionsSuggestionPage extends AppCompatActivity {

    TextView textViewSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_functions_suggestion_page);

        textViewSuggestion = (TextView) findViewById(R.id.textViewSuggestion);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("Suggestion");
            textViewSuggestion.setText(getName);
        }
    }
}
