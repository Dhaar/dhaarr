package com.example.boopathyraj.lims;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by admin on 14-03-2017.
 */

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.PaymentHistoryViewHolder> {

    public Context context;
    public List<BookResponse> list;

    public ExampleAdapter(Context context, List<BookResponse> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_details, parent, false);
        return new PaymentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PaymentHistoryViewHolder holder, final int position) {

        holder.bookname.setText(list.get(position).getBookname());
        holder.author.setText(list.get(position).getAuthor());
        holder.bookid.setText(list.get(position).getBookid());

        //SharedPreferences.Editor prefs = context.getSharedPreferences("bookid", context.MODE_PRIVATE).edit();
        //prefs.putString("bookid",list.get(position).getBookid());
        //prefs.commit();
        holder.renw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rolno=holder.rollno;
                String book_id=list.get(position).getBookid();
                uploadFile(rolno,book_id);
                //Toast.makeText(context, list.get(position).getBookid(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, holder.rollno, Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView bookname;
        TextView author;
        TextView bookid;
        String rollno, book_id;
        Button renw;
        //TextView status;


        public PaymentHistoryViewHolder(View itemView) {
            super(itemView);
            bookname = (TextView) itemView.findViewById(R.id.bookname);
            author = (TextView) itemView.findViewById(R.id.author);
            bookid = (TextView) itemView.findViewById(R.id.bookid);
            //status= (TextView) itemView.findViewById(R.id.status);
            SharedPreferences sp = context.getSharedPreferences("rollno", Context.MODE_PRIVATE);
            rollno = sp.getString("rollno", null);
            renw = (Button) itemView.findViewById(R.id.renew);


        }
    }

    public void uploadFile(String rolno, String book_id) {

        //Gson gson = new GsonBuilder()
        //.setLenient()
        //.create();

// Change base URL to your upload server URL.
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://192.168.43.127")
                .build();
        ApiService service = build.create(ApiService.class);


        Call<ResponseBody> call = service.onsend(rolno, book_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseValue = response.body().string();
                        System.out.println(responseValue);
                        Toast.makeText(context, responseValue, Toast.LENGTH_SHORT).show();
                        if (responseValue.trim() == "Yes") {
                            Toast.makeText(context, "Renewal Done Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Renewal can be done only once", Toast.LENGTH_SHORT).show();
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
}