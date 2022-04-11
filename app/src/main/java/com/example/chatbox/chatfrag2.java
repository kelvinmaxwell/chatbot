package com.example.chatbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatfrag2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatfrag2 extends Fragment implements previousmanualadapter2.ContactsAdapterListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<get_set> contactList;
    private previousmanualadapter2 mAdapter;
    private RecyclerView recyclerView;
    View view;

    public chatfrag2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chatfrag2.
     */
    // TODO: Rename and change types and number of parameters
    public static chatfrag2 newInstance(String param1, String param2) {
        chatfrag2 fragment = new chatfrag2();
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
        view = inflater.inflate(R.layout.fragment_manualfrag2, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new previousmanualadapter2(getContext(), contactList, this);

        // white background notification bar


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        fetchContacts();
        return view;
    }


    private void fetchContacts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
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

                Toast.makeText(getContext(), "check connection " , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                String sql = "SELECT `question`,`answer` ,count(`question`) as `count` FROM `transactions` WHERE  Length(`answer`) > 100 and  `chats`='chats' group by(`question`) order by `count` DESC limit 10";

                params.put("action", "get_data");
                params.put("database", getResources().getString(R.string.database));
                params.put("sql", sql);




                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onContactSelected(get_set contact) {

    }
}