package com.example.chatbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class Manualform extends AppCompatActivity {
    public TextView descrption, email,name,subject;
    public Spinner issuetype,priority;
    public Button submit;
    public SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String[] country = { "India", "USA", "China", "Japan", "Other"};
    String[] country2 = { "India", "USA", "China", "Japan", "Other"};

    String[] isst={ "SLS issue", "SiS issue", "Submission issues(Assignments,projects)", "Online classes issues", "Log in to University site issue","other"};
    String[] prioritys={ "HIGH", "MEDIUM", "LOW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manualform);
        Toast.makeText(getApplicationContext(), "manual system", Toast.LENGTH_SHORT).show();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Manual form");

        issuetype=findViewById(R.id.spinner);
        priority=findViewById(R.id.spinner2);



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,isst);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        issuetype.setAdapter(aa);


        ArrayAdapter arr2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,prioritys);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        priority.setAdapter(arr2);


        issuetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        name=findViewById(R.id.name);
        subject=findViewById(R.id.subject);

        descrption=findViewById(R.id.textView4);
        email=findViewById(R.id.textView5);

        sharedpreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        submit=findViewById(R.id.button);


        myToolbar.setTitleTextColor(Color.WHITE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserselecteditem();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent i=new Intent(Manualform.this,menuactivity.class);
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


                String sql = "INSERT INTO `transactions`(`number`, `description`,`user`,`name`,`subject`,`issuetype`,`priority`) VALUES ('"+email.getText().toString()+"','"+descrption.getText().toString()+"','"+sharedpreferences.getString("email",null)+"','"+name.getText().toString()+"','"+subject.getText().toString()+"','"+issuetype.getSelectedItem().toString()+"','"+priority.getSelectedItem().toString()+"') ";

                params.put("action", "insert_data");
                params.put("database", getResources().getString(R.string.database));
                params.put("sql", sql);


                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }



}