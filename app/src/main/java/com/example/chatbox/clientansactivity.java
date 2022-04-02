package com.example.chatbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class clientansactivity extends AppCompatActivity {
    public Button btn;
    public RatingBar ratings;
    public TextView txt;
    public String id;
    public TextView descrption, email,register,bot,admin,tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientansactivity);
        btn=findViewById(R.id.button2);
        ratings=findViewById(R.id.ratingBar);
        txt=findViewById(R.id.solution);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        selectitem();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserselecteditem();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent i=new Intent(clientansactivity.this,menuactivity.class);
                        startActivity(i);
                    }
                }, 1000);
            }
        });





    }

    private void inserselecteditem() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        System.out.println("maxi"+response);
                        Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();
                        try {
                            Log.d("JSON", response);

                            JSONObject eventObject = new JSONObject(response);
                            JSONArray projectNameArray = eventObject.getJSONArray("data");
                            for (int i = 0; i <= projectNameArray.length(); i++) {
                                JSONObject obj = projectNameArray.getJSONObject(i);





                                //Log.d("Dropdown",Dropdown.get(i));  this, R.layout.spinnertxt, makedata);
                                //        adaptermake.setDropDownViewResource(R.layout.itembgtxt
                            }

                            String error_status=eventObject.getString("status");
                            System.out.println(error_status);




                        } catch (Exception e) {
                            Log.d("Tag",e.getMessage());


                        }




                        // adding contacts to contacts list



                        // refreshing recycler view

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json

                Toast.makeText(getApplicationContext(), "check connection " , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();


                String sql = "UPDATE `transactions` SET `ratings`='"+String.valueOf(ratings.getRating())+"'  WHERE `ticketnumber`='"+id+"'";
                params.put("action", "insert_data");
                params.put("database", getResources().getString(R.string.database));
                params.put("sql", sql);


                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private void selectitem() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        System.out.println("maxi"+response);
                        Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();
                        try {
                            Log.d("JSON", response);

                            JSONObject eventObject = new JSONObject(response);
                            JSONArray projectNameArray = eventObject.getJSONArray("data");
                            for (int i = 0; i <= projectNameArray.length(); i++) {
                                JSONObject obj = projectNameArray.getJSONObject(i);



                                txt.setText(obj.getString("description")+": solution:"+obj.getString("solution"));

                                //Log.d("Dropdown",Dropdown.get(i));  this, R.layout.spinnertxt, makedata);
                                //        adaptermake.setDropDownViewResource(R.layout.itembgtxt
                            }




                        } catch (Exception e) {
                            Log.d("Tag",e.getMessage());


                        }




                        // adding contacts to contacts list



                        // refreshing recycler view

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json

                Toast.makeText(getApplicationContext(), "check connection " , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();


                String sql = "select * from `transactions` where `ticketnumber`='"+id+"'";

                params.put("action", "get_data");
                params.put("database", getResources().getString(R.string.database));
                params.put("sql", sql);


                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


}