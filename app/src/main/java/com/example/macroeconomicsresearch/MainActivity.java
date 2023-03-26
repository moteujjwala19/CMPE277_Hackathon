package com.example.macroeconomicsresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String selectedCountry;
    private int selectedUser;
    private int selectedTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner);
        ImageView macroeconomics_researcher_image = findViewById(R.id.macroeconomics_researcher_image);
        ImageView government_official_image = findViewById(R.id.government_official_image);
        ImageView macroeconomics_table_image = findViewById(R.id.macroeconomics_table_image);
        ImageView agriculture_table_image = findViewById(R.id.agriculture_table_image);
        ImageView debt_table_image = findViewById(R.id.debt_table_image);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("China");
        arrayList.add("India");
        arrayList.add("USA");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(2);
        selectedCountry="Please select country";
        selectedUser=0;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     selectedCountry=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }

        });

        macroeconomics_researcher_image.setOnClickListener(this);
        government_official_image.setOnClickListener(this);
        macroeconomics_table_image.setOnClickListener(this);
        agriculture_table_image.setOnClickListener(this);
        debt_table_image.setOnClickListener(this);
        government_official_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.macroeconomics_researcher_image:
                selectedUser=0;
                break;
            case R.id.government_official_image:
                selectedUser=1;
                break;
            case R.id.macroeconomics_table_image:
                selectedTable=0;
                openShowActivity();
                break;
            case R.id.agriculture_table_image:
                selectedTable=1;
                openShowActivity();
                break;
            case R.id.debt_table_image:
                selectedTable=2;
                openShowActivity();
                break;
            default:
                break;
        }
    }
    private  void openShowActivity(){
        Intent intent = new Intent(this,ShowActvity.class);
        intent.putExtra("selectedCountry",selectedCountry);
        intent.putExtra("selectedUser",selectedUser);
        intent.putExtra("selectedTable",selectedTable);
        startActivity(intent);
    }
}