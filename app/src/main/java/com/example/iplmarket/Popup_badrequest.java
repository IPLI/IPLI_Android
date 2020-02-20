package com.example.iplmarket;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Popup_badrequest extends AppCompatActivity {

    TextView popup_exit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_badrequest);    //activity_popup에서 content찾음


        popup_exit_button = findViewById(R.id.bad_yes_button);

        popup_exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List_interface temp = (List_interface) List_interface.List_interface_activity;
                finish();
                temp.finish();
            }
        });
    }
}