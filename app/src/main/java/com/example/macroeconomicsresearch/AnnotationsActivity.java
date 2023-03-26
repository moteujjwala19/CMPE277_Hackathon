package com.example.macroeconomicsresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AnnotationsActivity extends AppCompatActivity {
    private String selectedCountry;
    List<DataPoint> list;
    private int selectedUser;
    GraphView graph = null;
    private int selectedTable;
    List<GDPUSD> GDPList = new ArrayList<>();
    List<GDPUSD> inflowList = new ArrayList<>();
    List<GDPUSD> outflowList = new ArrayList<>();
    ArrayList<String> checkedStrings;
    String start_year="1960",end_year="2020";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotations);
        Button annotations_btn=findViewById(R.id.annotations_btn);
        TextView selectedCountry_tv=findViewById(R.id.country_name);
        Intent intent = getIntent();
        selectedCountry=intent.getStringExtra("selectedCountry");
        selectedUser=intent.getIntExtra("selectedUser",0);
        selectedTable=intent.getIntExtra("selectedTable",0);
        checkedStrings= (ArrayList<String>) getIntent().getSerializableExtra("checkedStrings");

        showGraph();
        // select check box gdp -> read from gdp file -> csv read code

        ImageView macroeconomics_table_image=findViewById(R.id.macroeconomics_table_image);
        ImageView agriculture_table_image=findViewById(R.id.agriculture_table_image);
        ImageView debt_table_image=findViewById(R.id.debt_table_image);
        selectedCountry_tv.setText(selectedCountry);
        if(selectedTable==0){
            annotations_btn.setBackgroundColor(getResources().getColor(R.color.Blue_color));;
            macroeconomics_table_image.setBackgroundColor(getResources().getColor(R.color.Blue_color));
        }
        else if(selectedTable==1){
            annotations_btn.setBackgroundColor(getResources().getColor(R.color.Green_color));;
            agriculture_table_image.setBackgroundColor(getResources().getColor(R.color.Green_color));
        }
        else{
            annotations_btn.setBackgroundColor(getResources().getColor(R.color.Brown_color));;
            debt_table_image.setBackgroundColor(getResources().getColor(R.color.Brown_color));
        }
    }


    public void readCsvgdpusd(String selectedCountry) {
        graph = null;
        InputStream is = getResources().openRawResource(R.raw.gdpusd2);
        int countryOrder = 1;
        if(selectedCountry.equals("India")){
            countryOrder=1;
        }else if(selectedCountry.equals("China")){
            countryOrder=2;
        }else{
            countryOrder=3;
        }
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        Log.d("Annotation","Column" + countryOrder);
            try {
                while ( (line = reader.readLine()) != null){
                    String[] tokens = line.split(",");
//                    System.out.println("token[0]: "+tokens[0]);
//                    int token=Integer.parseInt(tokens[0]);
//                    int sy= Integer.parseInt(start_year);
//                    int ey=Integer.parseInt(end_year);
//                    System.out.println("token : "+tokens+" "+sy+" "+ey);
//                    if(token< sy|| token>ey)
//                        break;
                    GDPUSD gdpusd = new GDPUSD();
                    gdpusd.setYear(tokens[0]);
                    //countryOrder: selected country
                    double val = new BigDecimal(tokens[countryOrder]).doubleValue();
                    gdpusd.setCountry(val);
                    GDPList.add(gdpusd);
                    Log.d("Annotation","Reading: " + gdpusd);
                }
            } catch (IOException e) {
                Log.wtf("Annotation","error reading csv file" + line ,e);
                e.printStackTrace();
            }

         graph = (GraphView) findViewById(R.id.gdp);
        graph.setVisibility(View.VISIBLE);
//        graph.setLayoutParams(new LinearLayout.LayoutParams(1000,500));

//        List<DataPoint>
        list = new ArrayList<>();
        Log.d("Annotation","Year" + GDPList.get(0).getYear());

        double d = Double.parseDouble(start_year);
        for(GDPUSD gdpusd : GDPList){
            System.out.println("d value: "+d+" start:"+start_year+" end year"+end_year);
            if(d<Double.parseDouble(start_year) ||  d>Double.parseDouble(end_year))
                break;
            list.add(new DataPoint( d++,gdpusd.getCountry()));
        }

        DataPoint[] myArray = new DataPoint[list.size()];
        list.toArray(myArray);
        for(DataPoint ls:list){
            System.out.println("x "+ls.getX()+" y"+ls.getY());
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(myArray);

        graph.addSeries(series);

    }

    public void readCsvInFlow(String selectedCountry) {
        graph = null;
        InputStream is = getResources().openRawResource(R.raw.inflow);
        int countryOrder = 2;
        if(selectedCountry.equals("India")){
            countryOrder=2;
        }else if(selectedCountry.equals("China")){
            countryOrder=1;
        }else{
            countryOrder=3;
        }
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        Log.d("Annotation","Column" + countryOrder);

        try {
            while ( (line = reader.readLine()) != null ){

                String[] tokens = line.split(",");
//                if(Integer.parseInt(tokens[0])<Integer.parseInt(start_year) || Integer.parseInt(tokens[0])>Integer.parseInt(end_year))
//                    break;
                GDPUSD gdpusd = new GDPUSD();
                gdpusd.setYear(tokens[0]);
                double val = new BigDecimal(tokens[countryOrder]).doubleValue();
                gdpusd.setCountry(val);
                inflowList.add(gdpusd);
                Log.d("Annotation","Reading: " + gdpusd);
            }
        } catch (IOException e) {
            Log.wtf("Annotation","error reading csv file" + line ,e);
            e.printStackTrace();
        }

        GraphView graph = (GraphView) findViewById(R.id.inflow);
        graph.setVisibility(View.VISIBLE);
//        graph.setLayoutParams(new LinearLayout.LayoutParams(1000,500));
        List<DataPoint> list = new ArrayList<>();
        Log.d("Annotation","Year" + inflowList.get(0).getYear());

        double d = Double.parseDouble(start_year);
        for(GDPUSD gdpusd : inflowList){
            if(d<Double.parseDouble(start_year) ||  d>Double.parseDouble(end_year))
                break;
            list.add(new DataPoint( d++,gdpusd.getCountry()));
        }

        DataPoint[] myArray = new DataPoint[list.size()];
        list.toArray(myArray);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(myArray);

        graph.addSeries(series);

    }

    public void readCsvOutFlow(String selectedCountry) {
        graph = null;
        InputStream is = getResources().openRawResource(R.raw.outflow);
        int countryOrder = 1;
        if(selectedCountry.equals("India")){
            countryOrder=2;
        }else if(selectedCountry.equals("China")){
            countryOrder=1;
        }else{
            countryOrder=3;
        }
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        Log.d("Annotation","Column" + countryOrder);
        try {
            while ( (line = reader.readLine()) != null){
                String[] tokens = line.split(",");
                GDPUSD gdpusd = new GDPUSD();
                gdpusd.setYear(tokens[0]);
                double val = new BigDecimal(tokens[countryOrder]).doubleValue();
                gdpusd.setCountry(val);
                outflowList.add(gdpusd);
                Log.d("Annotation","Reading: " + gdpusd);
            }
        } catch (IOException e) {
            Log.wtf("Annotation","error reading csv file" + line ,e);
            e.printStackTrace();
        }

        GraphView graph = (GraphView) findViewById(R.id.outflow);
        graph.setVisibility(View.VISIBLE);
//        graph.setLayoutParams(new LinearLayout.LayoutParams(1000,500));
        List<DataPoint> list = new ArrayList<>();
        Log.d("Annotation","Year" + outflowList.get(0).getYear());

        double d = Double.parseDouble(start_year);
        for(GDPUSD gdpusd : outflowList){
            if(d<Double.parseDouble(start_year) ||  d>Double.parseDouble(end_year))
                break;
            list.add(new DataPoint( d++,gdpusd.getCountry()));
        }

        DataPoint[] myArray = new DataPoint[list.size()];
        list.toArray(myArray);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(myArray);

        graph.addSeries(series);

    }

    public void applyButton(View v){
        EditText st_year = (EditText) findViewById(R.id.start_year_et);
        start_year = st_year.getText().toString();
        EditText en_year = (EditText) findViewById(R.id.end_year_et);
        end_year = en_year.getText().toString();
//        graph = new GraphView();
//        graph.setVisibility(View.INVISIBLE);
        if(list!=null)
            list.clear();
//        graph.invalidate();
        if(graph!=null)
        graph.removeAllSeries();
//        System.out.println("list size after changing the year "+list.size());
        showGraph();
    }
    public void showGraph(){
        if(selectedUser==0 && selectedTable==0){ // user -> resercher & table -> macroeconomic
            Log.d("Annotation","checkedStrings: " + checkedStrings);
            if(checkedStrings.contains("GDP (USD)")){
                Log.d("Annotation","GDP (USD) ");
                readCsvgdpusd(selectedCountry);
            }
            if(checkedStrings.contains("FDI Inflows(USD)")){
                Log.d("Annotation","FDI Inflows(USD): ");
                readCsvInFlow(selectedCountry);
            }
            if(checkedStrings.contains("FDI Outflows(USD)")){
                Log.d("Annotation","FDI Onflows(USD): ");
                readCsvOutFlow(selectedCountry);
            }
            if(checkedStrings.contains("Import/Export flow")){
                Log.d("Annotation","FDI Onflows(USD): ");
                readCsvOutFlow(selectedCountry);
            }

        }else if(selectedUser==0 && selectedTable==1) {// user -> resercher & table -> agriculture

            Log.d("Annotation agriculture","checkedStrings: " + checkedStrings);
            if(checkedStrings.contains("Contribution To GDP")){
                Log.d("Annotation","GDP (USD) ");
                readCsvgdpusd(selectedCountry);
            }
            if(checkedStrings.contains("Credit")){
                Log.d("Annotation","FDI Inflows(USD): ");
                readCsvInFlow(selectedCountry);
            }
            if(checkedStrings.contains("Fertilizers")){
                Log.d("Annotation","FDI Onflows(USD): ");
                readCsvOutFlow(selectedCountry);
            }
            if(checkedStrings.contains("Fertilizer Production")){
                Log.d("Annotation","FDI Onflows(USD): ");
                readCsvOutFlow(selectedCountry);
            }


        }else if(selectedUser==0 && selectedTable==2){// user -> resercher & table -> debt

            Log.d("Annotation agriculture","checkedStrings: " + checkedStrings);
            if(checkedStrings.contains("Contribution To GDP")){
                Log.d("Annotation","GDP (USD) ");
                readCsvgdpusd(selectedCountry);
            }
            if(checkedStrings.contains("Credit")){
                Log.d("Annotation","FDI Inflows(USD): ");
                readCsvInFlow(selectedCountry);
            }
            if(checkedStrings.contains("Fertilizers")){
                Log.d("Annotation","FDI Onflows(USD): ");
                readCsvOutFlow(selectedCountry);
            }
            if(checkedStrings.contains("Fertilizer Production")){
                Log.d("Annotation","FDI Onflows(USD): ");
                readCsvOutFlow(selectedCountry);
            }

        }
    }

    public void onAnnotationBtnClick(View view) {
        AlertDialog.Builder annotationBuilder = new AlertDialog.Builder(this)
                .setTitle("Graph Annotations");
        //.setMessage("This is some dialog")

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        annotationBuilder.setView(input);

        annotationBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userAnnotation = input.getText().toString();

                        DataController dataController=new DataController(getBaseContext());
                        dataController.open();
                        long retValue= dataController.insert(userAnnotation);
                        dataController.close();
                        // DBWriter writer = new DBWriter();
                        //writer.writeText(userAnnotation);
                    }
                })
                .show();
    }
}