package com.example.beproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;

public class TripGenerator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Constants
    private static final String SHARED_PREF = "SharedPrefs";
    private static final String KEY_BUDGET = "Budget";

    //Custom variables
    private String strBudget = "";

    //XML Components
    Spinner spinner_City , spinner_Budget;
    Button btn_Generate_Trip;

    //Android built-in classes
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_generator);

        sharedPreferences = getSharedPreferences(SHARED_PREF , MODE_PRIVATE);

        //Connecting XML components
        connectXmlComponents();

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this , R.array.spinner_city , android.R.layout.simple_spinner_dropdown_item);
        spinner_City.setAdapter(cityAdapter);
        ArrayAdapter<CharSequence> budgetAdapter = ArrayAdapter.createFromResource(this , R.array.spinner_budget , android.R.layout.simple_spinner_dropdown_item);
        spinner_Budget.setAdapter(budgetAdapter);

        btn_Generate_Trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext() , strBudget , Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_BUDGET , strBudget);
                editor.apply();
                Intent intent = new Intent(TripGenerator.this , MapActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strBudget = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this , strBudget , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Connecting XML components
    private void connectXmlComponents(){
        spinner_City = findViewById(R.id.spinnerCity);
        spinner_City.setOnItemSelectedListener(this);
        spinner_Budget = findViewById(R.id.spinnerBudget);
        spinner_Budget.setOnItemSelectedListener(this);
        btn_Generate_Trip = findViewById(R.id.btnGenerateTrip);
    }
}