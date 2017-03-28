package com.example.boopathyraj.lims;

import android.content.Intent;
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

public class Password extends AppCompatActivity {

    Button b1,b2;
    EditText e1, e2, e3, e4;
    String email,current,newpass,confrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        e1 = (EditText) findViewById(R.id.email);
        e2 = (EditText) findViewById(R.id.current);
        e3 = (EditText) findViewById(R.id.newpassword);
        e4 = (EditText) findViewById(R.id.confirm);

        b1 = (Button) findViewById(R.id.ok);

        b2 = (Button) findViewById(R.id.cancel);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                email = e1.getText().toString();

                current=e2.getText().toString();

                newpass  = e3.getText().toString();

                confrm = e4.getText().toString();


                if( !validate()) {
                    Toast.makeText(getApplicationContext(),
                            "Please check your details again", Toast.LENGTH_SHORT).show();
                }
                else

                {
                  /*  ProgressDialog progressDialog = new ProgressDialog(Signup.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("....Uploading Information...");
                    progressDialog.show();*/
                    uploadFile( email, current,newpass);
                  //  startActivity(new Intent(Signup.this, Signin.class));
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Password.this, Navigation.class));

            }
        });

    }
    public void uploadFile(String email,String current,String newpass) {

        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")
                .build();
        ApiService service = build.create(ApiService.class);


        Call<ResponseBody> call =  service.onPass(email,current,newpass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String responseValue = response.body().string();
                        if (responseValue.equals("true")) {

                            Toast.makeText(getApplicationContext(),
                                    "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(Signin.this,Navigation.class));


                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Something Wrong", Toast.LENGTH_SHORT).show();

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
            e3.setError("enter a valid email address");
            valid = false;
        } else {
            e1.setError(null);
        }

        if (current.isEmpty() || current.length() < 4 || current.length() > 15) {
            e2.setError("Please check  your current password");
            valid = false;
        } else {
            e2.setError(null);
        }


        if (newpass.isEmpty() || newpass.length() < 4 || newpass.length() > 15) {
            e3.setError("password should be 4 to 15 letters");
            valid = false;
        } else {
            e3.setError(null);
        }

        if(newpass.equals(confrm))
        {
            e4.setError(null);
        }
        else
        {
            e4.setError("New Password and Confirm password should be same");
            valid=false;
        }

        return valid;
    }
}

