package com.example.chatbox;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link manualfrag1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class manualfrag1 extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public TextView descrption, email,name,subject;
    public Spinner issuetype,priority;
    public Button submit;
    public SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String[] country = { "India", "USA", "China", "Japan", "Other"};
    String[] country2 = { "India", "USA", "China", "Japan", "Other"};

    String[] isst={ "SLS issue", "SiS issue", "Submission issues(Assignments,projects)", "Online classes issues", "Log in to University site issue","other"};
    String[] prioritys={ "HIGH", "MEDIUM", "LOW"};
    private List<get_set> contactList;
    private mybookingsadapter mAdapter;
    private RecyclerView recyclerView;

    View view;

    public manualfrag1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment manualfrag1.
     */
    // TODO: Rename and change types and number of parameters
    public static manualfrag1 newInstance(String param1, String param2) {
        manualfrag1 fragment = new manualfrag1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manualfrag1, container, false);

        issuetype=view.findViewById(R.id.spinner);
        priority=view.findViewById(R.id.spinner2);



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,isst);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        issuetype.setAdapter(aa);


        ArrayAdapter arr2 = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,prioritys);
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


        name=view.findViewById(R.id.name);
        subject=view.findViewById(R.id.subject);

        descrption=view.findViewById(R.id.textView4);
        email=view.findViewById(R.id.textView5);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        submit=view.findViewById(R.id.button);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserselecteditem();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent i=new Intent(getContext(),menuactivity.class);
                        startActivity(i);
                    }
                }, 1000);

            }
        });




        // Inflate the layout for this fragment
        return view;
    }





    private void inserselecteditem() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        System.out.println("maxi"+response);
                        Toast.makeText(getContext(), "SAVED", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(getContext(), "check connection " , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();


                String sql = "INSERT INTO `transactions`(`number`, `description`,`user`,`name`,`subject`,`issuetype`,`priority`,`category`) VALUES ('"+email.getText().toString()+"','"+descrption.getText().toString()+"','"+sharedpreferences.getString("email",null)+"','"+name.getText().toString()+"','"+subject.getText().toString()+"','"+issuetype.getSelectedItem().toString()+"','"+priority.getSelectedItem().toString()+"','manual') ";

                params.put("action", "insert_data");
                params.put("database", getResources().getString(R.string.database));
                params.put("sql", sql);


                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


}