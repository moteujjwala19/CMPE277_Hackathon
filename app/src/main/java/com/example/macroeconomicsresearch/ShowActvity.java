package com.example.macroeconomicsresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowActvity extends AppCompatActivity {
    private String selectedCountry;
    private int selectedUser;
    private int selectedTable;
    private CheckBox checkBox_1;
    private CheckBox checkBox_2;
    private CheckBox checkBox_3;
    private CheckBox checkBox_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_actvity);
        TextView selectedCountry_tv=findViewById(R.id.country_name);
        Button show_btn=findViewById(R.id.show_btn);
        ImageView macroeconomics_table_image=findViewById(R.id.macroeconomics_table_image);
        ImageView agriculture_table_image=findViewById(R.id.agriculture_table_image);
        ImageView debt_table_image=findViewById(R.id.debt_table_image);

        checkBox_1=findViewById(R.id.checkBox_1);
        checkBox_2=findViewById(R.id.checkBox_2);
        checkBox_3=findViewById(R.id.checkBox_3);
        checkBox_4=findViewById(R.id.checkBox_4);

        Intent intent = getIntent();
        selectedCountry=intent.getStringExtra("selectedCountry");
        selectedUser=intent.getIntExtra("selectedUser",0);
        selectedTable=intent.getIntExtra("selectedTable",0);
        selectedCountry_tv.setText(selectedCountry);

        if(selectedTable==0){
            show_btn.setBackgroundColor(getResources().getColor(R.color.Blue_color));;
            macroeconomics_table_image.setBackgroundColor(getResources().getColor(R.color.Blue_color));
        }
        else if(selectedTable==1){
            show_btn.setBackgroundColor(getResources().getColor(R.color.Green_color));;
            agriculture_table_image.setBackgroundColor(getResources().getColor(R.color.Green_color));
            checkBox_1.setText(R.string.contribution_gdp);
            checkBox_2.setText(R.string.Credit);
            checkBox_3.setText(R.string.Fertilizers);
            checkBox_4.setText(R.string.Fertilizer_Production);
        }
        else{
            show_btn.setBackgroundColor(getResources().getColor(R.color.Brown_color));;
            debt_table_image.setBackgroundColor(getResources().getColor(R.color.Brown_color));
            checkBox_1.setText(R.string.Reserves);
            checkBox_2.setText(R.string.GNI);
            checkBox_3.setText(R.string.Total_Debt);
            checkBox_4.setText(R.string.GNI_Current);
        }
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_1.isChecked()||checkBox_2.isChecked()||checkBox_3.isChecked()||checkBox_4
                .isChecked()){
                    openAnnotationsActivity();
                }
            }
        });
    }

    private void openAnnotationsActivity() {
        ArrayList<String> checkedStrings=new ArrayList<>();
        if(checkBox_1.isChecked()){
            checkedStrings.add(checkBox_1.getText().toString());
        }
        if(checkBox_2.isChecked()){
            checkedStrings.add(checkBox_2.getText().toString());
        }
        if(checkBox_3.isChecked()){
            checkedStrings.add(checkBox_3.getText().toString());
        }
        if(checkBox_4.isChecked()){
            checkedStrings.add(checkBox_4.getText().toString());
        }
        Intent intent = new Intent(this,AnnotationsActivity.class);
        intent.putExtra("selectedCountry",selectedCountry);
        intent.putExtra("selectedUser",selectedUser);
        intent.putExtra("selectedTable",selectedTable);
        intent.putExtra("checkedStrings",checkedStrings);
        startActivity(intent);
    }
}