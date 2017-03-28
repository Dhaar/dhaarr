package com.example.boopathyraj.lims;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup extends AppCompatActivity {
    Button b1, b2;
    EditText e1, e2, e3, e4, e5;
    String name,rollno,email,password,confirm;
      private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


            e1 = (EditText) findViewById(R.id.name);
            e2 = (EditText) findViewById(R.id.rollno);
            e3 = (EditText) findViewById(R.id.email);
            e4 = (EditText) findViewById(R.id.password);
            e5 = (EditText) findViewById(R.id.confirm);

            b1 = (Button) findViewById(R.id.register);

            b2 = (Button) findViewById(R.id.cancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                name = e1.getText().toString();

                rollno=e2.getText().toString();

                email = e3.getText().toString();

                password = e4.getText().toString();

                confirm = e5.getText().toString();

                if( !validate()) {
                    Toast.makeText(getApplicationContext(),
                            "Register again", Toast.LENGTH_SHORT).show();
                }
                else

                {
                  /*  ProgressDialog progressDialog = new ProgressDialog(Signup.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("....Uploading Information...");
                    progressDialog.show();*/
                    uploadFile(name, rollno, email, password, confirm);
                    startActivity(new Intent(Signup.this, Signin.class));
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Signin.class));

            }
        });
    }

    public void uploadFile(String name,String rollno,String email,String password,String confirm) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")
                .build();
        ApiService service = build.create(ApiService.class);


        Call<ResponseBody> call =  service.onSignUp(name,rollno,email,password,confirm);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body().toString()+":Successful");
                    System.out.println("Url:"+response.raw().request().url());
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

        if (name.isEmpty() || name.length() < 3) {
            e1.setError("at least 3 characters");
            valid = false;
        } else {
            e1.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e3.setError("enter a valid email address");
            valid = false;
        } else {
            e3.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            e4.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            e4.setError(null);
        }
        if(password.equals(confirm))
        {
            e5.setError(null);
        }
        else
        {
            e5.setError("Password and Confirm password should be same");
            valid=false;
        }

        return valid;
    }
}

