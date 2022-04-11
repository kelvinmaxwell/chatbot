package com.example.chatbox;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatfrag1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatfrag1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    public String input;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;

    // creating a variable for array list and adapter class.
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;

    public chatfrag1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chatfrag1.
     */
    // TODO: Rename and change types and number of parameters
    public static chatfrag1 newInstance(String param1, String param2) {
        chatfrag1 fragment = new chatfrag1();
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

        view = inflater.inflate(R.layout.fragment_chatfrag1, container, false);

        chatsRV = view.findViewById(R.id.idRVChats);
        sendMsgIB = view.findViewById(R.id.idIBSend);
        userMsgEdt = view.findViewById(R.id.idEdtMessage);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();

        input="#";


        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),Manualform.class);
                startActivity(i);
            }
        });






        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the message entered
                // by user is empty or not.
                if (userMsgEdt.getText().toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(getContext(), "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(input.equalsIgnoreCase("#")){
                    messageModalArrayList.clear();
                    messageRVAdapter.notifyDataSetChanged();
                }

                // calling a method to send message
                // to our bot to get response.

                input=userMsgEdt.getText().toString();
                fetchdetails(input);



                // below line we are setting text in our edit text as empty
                userMsgEdt.setText("");
            }
        });

        // on below line we are initialing our adapter class and passing our array lit to it.
        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, getContext());


        fetchdetails("hey SHA");
        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);


        // below line is to set layout
        // manager to our recycler view.
        chatsRV.setLayoutManager(linearLayoutManager);

        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(messageRVAdapter);
        // Inflate the layout for this fragment
        return view;
    }


    private void sendMessage(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();


        // url for our brain
        // make sure to add mshape for uid.
        // make sure to add your url.
//        String url = "http://api.brainshop.ai/get?bid=160754&key=5q0XwQwrnJb0UsGd&uid=40&msg='"+userMsg+"'" ;
        String url="http://192.168.1.101:8080/cvt/chatbot.php";
        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // in on response method we are extracting data
                    // from json response and adding this response to our array list.
                    String botResponse = response.getString("cnt");
                    messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));
                    chatsRV.scrollToPosition(messageModalArrayList.size()-1);

                    // notifying our adapter as data changed.
                    messageRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

                    // handling error response from bot.
                    messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error handling.

                System.out.println(error);
                messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                Toast.makeText(getContext(), "No response from the bot.."+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("id","1");




                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }



    private void fetchdetails(String userMsg) {
        if(userMsg.equalsIgnoreCase("hey SHA")||userMsg.equalsIgnoreCase("what is your name"))
        {

        }
        else{
            messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
            messageRVAdapter.notifyDataSetChanged();
            chatsRV.scrollToPosition(messageModalArrayList.size()-1);
        }



        String user = sharedpreferences.getString("email",null)+"810" ;

        String url="http://api.brainshop.ai/get?bid=160754&key=5q0XwQwrnJb0UsGd&uid='"+user+"'&msg='"+userMsg+"'";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            // in on response method we are extracting data
                            // from json response and adding this response to our array list.
                            JSONObject obj = new JSONObject(response);
                            String botResponse = obj.getString("cnt");
                            if (botResponse.contains("my friend")){
                                fetchdetails("what is your name");
                            }
                            else{

                              if(botResponse.contains("Hi,")){

                                }
                              else
                              {
                                  register(userMsg, botResponse);
                              }
                                messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));
                            }

                            chatsRV.scrollToPosition(messageModalArrayList.size()-1);
                            // notifying our adapter as data changed.
                            messageRVAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                            // handling error response from bot.
                            messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                            messageRVAdapter.notifyDataSetChanged();
                            chatsRV.scrollToPosition(messageModalArrayList.size()-1);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error handling.

                System.out.println(error);
                messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                Toast.makeText(getContext(), "No response from the bot.."+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                params.put("id",userMsg);




                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }




    public void register(String question,String answer){

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

                            JSONObject obj = new JSONObject(response);


                            String message=obj.optString("message");





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

                Toast.makeText(getContext(), "check connection " , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();


                String sql= "INSERT INTO `transactions`( `user`,`question`, `answer`,`chats`) VALUES ('"+sharedpreferences.getString("email",null)+"','"+question+"','"+answer+"','chats')";

                params.put("action", "get_data");
                params.put("sql", sql);
                params.put("database", getResources().getString(R.string.database));




                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }
}