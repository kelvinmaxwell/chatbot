package com.example.chatbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    Boolean isAllFabsVisible;

    TextView passwordtxt,phonetxt,emailtxtx,nametxt;
    RadioButton genderradioButton;
    RadioGroup radioGroup;
    Button submit;
    String selected;
    ConstraintLayout rootlayout;
    public CardView loadingcardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        passwordtxt=findViewById(R.id.passwordtxt);
        phonetxt=findViewById(R.id.phonetxt);
        emailtxtx=findViewById(R.id.emailtxt);
        nametxt=findViewById(R.id.usernametxt);
        submit=findViewById(R.id.submitbtnreg);
        rootlayout = findViewById(R.id.mainview);
        loadingcardview=findViewById(R.id.loadingcardview);
        loadingcardview.setVisibility(View.GONE);



        radioGroup=findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioMale:
                        selected="Male";
                        break;
                    case R.id.radioFemale:
                        selected="Female";
                        break;
                    default:
                        selected=null;
                }
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(selected==null||passwordtxt.getText().toString().equalsIgnoreCase("")||emailtxtx.getText().toString().equalsIgnoreCase("")||phonetxt.getText().toString().equalsIgnoreCase("")||nametxt.getText().toString().equalsIgnoreCase("")){
                    showsnakbar("check details");
                }
                else{
                    register();
                }


            }
        });
    }


    public void register(){
        loadingcardview.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        System.out.println("maxi"+response);
                        loadingcardview.setVisibility(View.GONE);
                        try {

                            JSONObject obj = new JSONObject(response);


                            String message=obj.optString("message");
                            showsnakbar(message);

                            Intent i=new Intent(registration.this,loginactivity.class);
                            startActivity(i);

                            finish();


                        }catch (Exception e) {
                            Log.d("Tag",e.getMessage());


                        }




                        // adding contacts to contacts list


                        // refreshing recycler view

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                loadingcardview.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "check connection " , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();




                params.put("action", "registration");
                params.put("database", getResources().getString(R.string.database));
                params.put("username", nametxt.getText().toString());
                params.put("password",passwordtxt.getText().toString());
                params.put("email",emailtxtx.getText().toString());
                params.put("gender",selected);



                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    public void showsnakbar(String message){
        Snackbar snackbar = Snackbar.make(rootlayout , message, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }
}