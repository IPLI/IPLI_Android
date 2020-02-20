package com.example.iplmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlanActivity extends Activity {
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_activity);

        user_name = getIntent().getStringExtra("user_name");

        ImageView imageView = findViewById(R.id.imv);
        imageView.setImageResource(R.drawable.preview);

        Button event = findViewById(R.id.button4);

        Button main = findViewById(R.id.button2);

        Button cart = findViewById(R.id.button3);

        FloatingActionButton fabButton = findViewById(R.id.fabButton);

        event.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent eveact = new Intent(PlanActivity.this,EventActivity.class);
                eveact.putExtra("user_name", user_name);
                finish();
                startActivity(eveact);
            }
        });

        main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mainact = new Intent(PlanActivity.this,GridActivity.class);
                mainact.putExtra("user_name", user_name);
                finish();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanActivity.this, Popup_socket_connect.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
                finish();
            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanActivity.this, Popup_socket_connect.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
                finish();
            }
        });
    }

}
