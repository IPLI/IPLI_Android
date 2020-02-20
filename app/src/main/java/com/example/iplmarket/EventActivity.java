package com.example.iplmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventActivity extends Activity {
    String user_name;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);

        user_name = getIntent().getStringExtra("user_name");

        ImageView imageView1 = findViewById(R.id.image1);
        imageView1.setImageResource(R.drawable.cong_event);

        ImageView imageView2 = findViewById(R.id.image2);
        imageView2.setImageResource(R.drawable.freshfruit_event);

        ImageView imageView3=findViewById(R.id.image3);
        imageView3.setImageResource(R.drawable.burger_event);

        ImageView imageView4=findViewById(R.id.image4);
        imageView4.setImageResource(R.drawable.margarita_event);

        ImageView imageView5=findViewById(R.id.image5);
        imageView5.setImageResource(R.drawable.ideas_event);

        Button plan = findViewById(R.id.button1);

        Button main = findViewById(R.id.button2);

        Button cart = findViewById(R.id.button3);

        FloatingActionButton fabButton = findViewById(R.id.fabButton);

        plan.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent planact = new Intent(EventActivity.this,PlanActivity.class);
                planact.putExtra("user_name", user_name);
                startActivity(planact);
                finish();
            }
        });


        main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mainact = new Intent(EventActivity.this,GridActivity.class);
                mainact.putExtra("user_name", user_name);
                finish();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, Popup_socket_connect.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
                finish();
            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, Popup_socket_connect.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
                finish();
            }
        });
    }


}
