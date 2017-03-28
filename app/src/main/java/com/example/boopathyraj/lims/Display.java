package com.example.boopathyraj.lims;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Display extends AppCompatActivity {

    String myJSON;

    private static final String TAG_RESULT="result";
    private static final String TAG_NAME = "bookname";
    private static final String TAG_AVAILABILITY = "availability";
    private static final String TAG_RACK ="rack";
    private static final String TAG_AUTHOR ="author";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    String name = "";
    String category= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x= getText(position).toString();
                Toast.makeText(getApplicationContext(),x, Toast.LENGTH_SHORT).show();
            }
        });
        personList = new ArrayList<HashMap<String, String>>();
        if (getIntent() != null) {
            name  = getIntent().getExtras().get("hint").toString();
            category = getIntent().getExtras().get("cate").toString();
            System.out.println("NameValue"+name);
            System.out.println("CategoryValue"+category);
        }
        //showList(jsonResponse,jsonResponse1);
        uploadFile (name,category);

    }


    protected void showList(String responseValue){
        try {
            JSONObject jsonObj  = new JSONObject(responseValue);
            peoples = jsonObj.getJSONArray(TAG_RESULT);
            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String name1 = c.getString(TAG_NAME);
                String availability = c.getString(TAG_AVAILABILITY);
                String rack = c.getString(TAG_RACK);
                String author=c.getString(TAG_AUTHOR);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_NAME,name1);
                persons.put(TAG_AVAILABILITY,availability);
                persons.put(TAG_RACK,rack);
                persons.put(TAG_AUTHOR,author);

                personList.add(persons);
                System.out.println("List:"+personList);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Display.this, personList, R.layout.list_view,
                   new String[]{TAG_NAME,TAG_AVAILABILITY,TAG_RACK,TAG_AUTHOR},

                    new int[]{R.id.name, R.id.availability, R.id.rack,R.id.author}
            );

            list.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void uploadFile(String name,String category){

        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")

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

                        showList(responseValue);

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


    }

}