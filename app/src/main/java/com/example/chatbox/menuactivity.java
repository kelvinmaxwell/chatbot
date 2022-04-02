package com.example.chatbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.chatbox.databinding.ActivityMenuactivityBinding;

public class menuactivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMenuactivityBinding binding;
    public TextView chatbot,manual,tickets,admin;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);
        chatbot=findViewById(R.id.chatbot);
        manual=findViewById(R.id.manual);
        tickets=findViewById(R.id.tickets);
        admin=findViewById(R.id.admin);





        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(menuactivity.this,mytickets.class);
                startActivity(i);
            }
        });

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(menuactivity.this,Manualform.class);
                startActivity(i);
            }
        });


        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(menuactivity.this,MainActivity.class);
                startActivity(i);
            }
        });


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(menuactivity.this,admintickets.class);
                startActivity(i);
            }
        });






        sharedpreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);




    }


}