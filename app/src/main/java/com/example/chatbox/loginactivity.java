package com.example.chatbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class loginactivity extends AppCompatActivity {
    public CardView loadingcardview;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public TextView register;


    public List<String> names;


    private ArrayList<get_set_category> category_arraylist;
    private RecyclerView recyclerView;
    private List<get_set> contactList;
    ConstraintLayout rootlayout;
    public EditText username,password;


Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        loginbtn=findViewById(R.id.loginbtn);
        username=findViewById(R.id.usernametxt);
        password=findViewById(R.id.password);

        loadingcardview=findViewById(R.id.loadingcardview);
        loadingcardview.setVisibility(View.GONE);
        rootlayout=findViewById(R.id.mainlayout);

        register=findViewById(R.id.textView9);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(loginactivity.this,registration.class);
                startActivity(i);
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);




               login();


    }

    public void login() {
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_category();
            }
        });
    }


            public void get_category() {
                loadingcardview.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loadingcardview.setVisibility(View.GONE);
                                Log.d("strrrrr", ">>" + response);

                                try {

                                    JSONObject obj = new JSONObject(response);
                                    showsnakbar(obj.optString("message"));
                                    if (obj.optString("error").equals("false")) {

                                        category_arraylist = new ArrayList<>();
                                        JSONObject  dataArray = obj.getJSONObject("user");



                                        JSONObject dataobj = obj.getJSONObject("user");



                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("email", dataobj.getString("email"));
                                        editor.commit();



                                                Intent k = new Intent(loginactivity.this, menuactivity.class);
                                                startActivity(k);






                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loadingcardview.setVisibility(View.GONE);
                                //displaying the error in toast if occurrs

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

//                        String imageData=imageToString(bitmap);


                        params.put("action", "login");
                        params.put("database", getResources().getString(R.string.database));
                        params.put("password", password.getText().toString());
                        params.put("username", username.getText().toString());
                        return params;
                    }
                };
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            }


    public void showsnakbar(String message){
        Snackbar snackbar = Snackbar.make(rootlayout , message, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }


        }