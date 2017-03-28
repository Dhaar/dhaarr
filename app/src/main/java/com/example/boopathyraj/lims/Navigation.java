package com.example.boopathyraj.lims;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    Context context = null;
    Button renw;
    TextView book;
    String bid;
    String rollno;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        SharedPreferences sp = getSharedPreferences("rollno", Context.MODE_PRIVATE);
        rollno = sp.getString("rollno", null);

        Toast.makeText(Navigation.this, rollno, Toast.LENGTH_SHORT).show();

        //book = (TextView) findViewById(R.id.bookid);
        setSupportActionBar(toolbar);
        context = this;

        //bid = book.getText().toString();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // Button renw=(Button) findViewById(R.id.renew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

     /*   Fragment fragment = new Fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();*/
        uploadFile(rollno);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       /* int id = item.getItemId();

        Fragment fragment =null;
        if (id == R.id.nav_profile) {
            // Handle the camera action
            fragment= new FragmentCamera();

        } else if (id == R.id.edit) {

        } else if (id == R.id.search) {

          Intent intent=new Intent(this, Search.class);
            this.startActivity(intent);


        } else if (id == R.id.logout) {

        //    startActivity(new Intent(Navigation.this, Signin.class));


        }*/

        switch (item.getItemId()) {

            case R.id.nav_profile:
                // fragment= new FragmentCamera();
                break;

            case R.id.edit:
                startActivity(new Intent(Navigation.this, Password.class));
                break;
            case R.id.search:
                startActivity(new Intent(Navigation.this, Search1.class));
                break;
            case R.id.logout:
                startActivity(new Intent(Navigation.this, Signin.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


/*       FragmentCamera fragment= new FragmentCamera();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void uploadFile(String rollno) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

// Change base URL to your upload server URL.
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService service = build.create(ApiService.class);

        Call<List<BookResponse>> call = service.getBooks(rollno);


        call.enqueue(new Callback<List<BookResponse>>() {

            @Override
            public void onResponse(Call<List<BookResponse>> call, Response<List<BookResponse>> response) {
                if (response.isSuccessful()) {

                    onRecycler(response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        sendChatMessage(response.body().get(i).getBookid(),response.body().get(i).getBookname());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookResponse>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    public void onRecycler(final List<BookResponse> bookResponses) {
        recyclerView.setLayoutManager(new LinearLayoutManager(Navigation.this));
        ExampleAdapter adapter = new ExampleAdapter(context, bookResponses);
        recyclerView.setAdapter(adapter);


    }

    public void sendChatMessage(String title,String messages) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService api = build.create(ApiService.class);
        String token = SharedPrefManager.getInstance(Navigation.this).getDeviceToken();
        Call<ResponseBody> responseBodyCall = api.sendChat(token, title, messages);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.v("Response", response.body().toString());
                    System.out.println("url:" + response.raw().request().url());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Navigation Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.boopathyraj.lims/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Navigation Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.boopathyraj.lims/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}











