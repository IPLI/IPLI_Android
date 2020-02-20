package com.example.iplmarket;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Popup_out_cart extends AppCompatActivity {

    TextView yes_button, no_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_out_cart);    //activity_popup에서 content찾음


        yes_button = findViewById(R.id.out_yes_button);
        no_button = findViewById(R.id.out_no_button);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List_interface temp = (List_interface) List_interface.List_interface_activity;
                item_class.setCart_price(0);
                finish();
                temp.finish();
            }
        });
    }
}