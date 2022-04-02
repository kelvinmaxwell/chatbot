package com.example.chatbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admintickets extends AppCompatActivity implements mybookingsadapter.ContactsAdapterListener {

    private List<get_set> contactList;
    private mybookingsadapter mAdapter;
    private RecyclerView recyclerView;
    public TextView descrption, email,register,bot,admin,tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admintickets);

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new mybookingsadapter(this, contactList, this);

        // white background notification bar


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
          recyclerView.setAdapter(mAdapter);





        fetchContacts();


    }
        private void fetchContacts() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (response == null) {
                                Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            System.out.println("maxi"+response);
                            try {
                                Log.d("JSON", response);

                                JSONObject eventObject = new JSONObject(response);
                                JSONArray projectNameArray = eventObject.getJSONArray("data");
                                String error_status=eventObject.getString("status");
                                System.out.println(error_status);
                                List<get_set> items = new Gson().fromJson(projectNameArray.toString(), new TypeToken<List<get_set>>() {
                                }.getType());
                                contactList.clear();
                                contactList.addAll(items);
                                mAdapter.notifyDataSetChanged();

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

                    String sql = "SELECT `ticketnumber` as `ticketnumber`, `number` as `email`, `status`, `description` FROM `transactions` ";

                    params.put("action", "get_data");
                    params.put("database", getResources().getString(R.string.database));
                    params.put("sql", sql);




                    return params;
                }
            };

            MyApplication.getInstance().addToRequestQueue(stringRequest);
        }



        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    // todo: goto back activity from here

//                    Intent intent = new Intent(mybookings.this,mainfragment.class);
//                    startActivity(intent);
//                    finish();
                    return true;



                default:
                    return super.onOptionsItemSelected(item);
            }}


    @Override
    public void onContactSelected(get_set contact) {

        Intent i=new Intent(admintickets.this,adminansactivity.class);
        i.putExtra("id",contact.getticketnumber());
        startActivity(i);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}