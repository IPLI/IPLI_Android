package com.example.iplmarket;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Popup extends AppCompatActivity {

    TextView content_text, item_info_text, popup_exit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상단 안테나 바를 표시하지 않고 전체화면을 사용하는 코드지만 popup이므로 소용x
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_popup);    //activity_popup에서 content찾음

        Intent intent = getIntent();
        content_text = (TextView) findViewById(R.id.title_text_popup);
        content_text.setText(intent.getExtras().getString("content_text")+" 상품정보");

        item_info_text = findViewById(R.id.item_info_text);
        item_info_text.setMovementMethod(new ScrollingMovementMethod());
        item_info_text.setText(intent.getExtras().getString("item_info"));

        popup_exit_button = findViewById(R.id.popup_exit_button);
        popup_exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}