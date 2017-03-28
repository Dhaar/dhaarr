package com.example.boopathyraj.lims;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Search1 extends AppCompatActivity {
ArrayAdapter<String> dataAdapter;

    String name,category;
    EditText tx1;
    //Spinner s1;

    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);

        tx1 = (EditText) findViewById(R.id.bookname);
        //tx2 = (EditText) findViewById(R.id.category);
        b1 = (Button) findViewById(R.id.button);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent,View view, int position,long id){
                String item=parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        final List<String> categories = new ArrayList<String>();
        categories.add(" SELECT DEPARTMENT ");
        categories.add("CSE");
        categories.add("MECH");
        categories.add("ECE");
        categories.add("CIVIL");
        categories.add("IT");





        dataAdapter=new ArrayAdapter<String>(Search1.this,android.R.layout.simple_spinner_item,categories);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Search1.this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = tx1.getText().toString();
                //category = tx2.getText().toString();


                //category=categories.getText().toString();

                category = spinner.getSelectedItem().toString();

                // Spinner Drop down elements
               /* Intent intent = new Intent(Search1.this,Display.class);
                intent.putExtra("search",tx3.getText().toString());
                startActivity(intent);*/

                Intent intent = new Intent(Search1.this,Display.class);
                intent.putExtra("hint",name);
                intent.putExtra("cate",category);
                startActivity(intent);


       //         uploadFile(name, category);
            }
        });
    }

/*   public void uploadFile(String name,String category){

        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2")
                .build();
        ApiService service = build.create(ApiService.class);


        Call<ResponseBody> call =  service.onSearch1(name,category);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String responseValue = response.body().string();
                        System.out.println(responseValue);

                       // String x[]=responseValue.split("##");

                        tx3.setText(responseValue);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }*/
        }
