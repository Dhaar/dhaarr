package com.example.boopathyraj.lims;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signin extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;

    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        b1=(Button)findViewById(R.id.btn_login);
        b2=(Button)findViewById(R.id.btn_register);
        ed1=(EditText)findViewById(R.id.email);
        ed2=(EditText)findViewById(R.id.password);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                email=ed1.getText().toString();
                password=ed2.getText().toString();


                if( !validate()) {
                    Toast.makeText(getApplicationContext(),
                            "Check your email and password", Toast.LENGTH_SHORT).show();
                }
                else

                {
                    uploadFile(email,password);

                     //startActivity(new Intent(Signup.this, Signin.class));
                }
            }
        });


            b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this,Signup.class));
            }
        });
    }
    public void uploadFile(String email,String password) {

// Change base URL to your upload server URL.
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")
                .build();
        ApiService service = build.create(ApiService.class);


        Call<ResponseBody> call =  service.onSignIn(email,password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                            String responseValue = response.body().string();
                        System.out.println("responseValue"+responseValue);
                        String[] split = responseValue.split(",");
                        if (split[0].trim().equals("true")) {
                            SharedPreferences.Editor prefs = getSharedPreferences("rollno", MODE_PRIVATE).edit();
                            prefs.putString("rollno", split[1]);
                            prefs.commit();
                            Toast.makeText(getApplicationContext(), split[1]+
                                    "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signin.this,Navigation.class));


                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Login Credentials", Toast.LENGTH_SHORT).show();

                        }
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

    public boolean validate() {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ed1.setError("enter a valid email address");
            valid = false;
        } else {
            ed1.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            ed2.setError("Please check your password");
            valid = false;
        } else {
            ed2.setError(null);
        }

        return valid;
    }
}
