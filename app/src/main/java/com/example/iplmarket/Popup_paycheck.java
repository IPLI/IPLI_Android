package com.example.iplmarket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Popup_paycheck extends AppCompatActivity {

    TextView yes_button, no_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_paycheck);    //activity_popup에서 content찾음

        yes_button = findViewById(R.id.popup_yes_button);
        no_button = findViewById(R.id.popup_no_button);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<item_class> item_list = getIntent().getParcelableArrayListExtra("item_list");
                Intent intent = new Intent(getApplicationContext(), Payment.class); //페이먼트 클래스로 인텐트
                intent.putExtra("item_list", item_list);
                intent.putExtra("user_name", getIntent().getStringExtra("user_name"));
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

    public boolean onTouchEvent(MotionEvent e) {    //바깥쪽 레이어 클릭 시 안닫히게 설정
        return !(e.getAction() == MotionEvent.ACTION_OUTSIDE);
    }
}