package com.example.iplmarket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Popup_socket_connect extends AppCompatActivity {

    TextView yes_button, no_button;
    EditText socket;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_name = getIntent().getStringExtra("user_name");

        setContentView(R.layout.activity_popup_socket);    //activity_popup에서 content찾음

        socket = findViewById(R.id.socket_connect_text);
        yes_button = findViewById(R.id.socket_yes_button);
        no_button = findViewById(R.id.socket_no_button);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), List_interface.class); //페이먼트 클래스로 인텐트
                intent.putExtra("user_name", user_name);
                intent.putExtra("ip", socket.getText().toString());
                startActivity(intent);

                finish();
            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}