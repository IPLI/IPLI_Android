package com.example.iplmarket;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends Activity {
    FloatingActionButton fabButton;
    String user_name;
    private int[] imageIDs = new int[]{
            R.drawable.blanket,
            R.drawable.bear,
            R.drawable.tree,
            R.drawable.cake,
            R.drawable.jelly,
            R.drawable.orange,
            R.drawable.study,
            R.drawable.tea,
    };

    /** Called when the activity is first created. */

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = getIntent().getStringExtra("user_name");

        Button plan = findViewById(R.id.button1);

        Button event = findViewById(R.id.button4);

        Button cart = findViewById(R.id.button3);

        fabButton = findViewById(R.id.fabButton);

        plan.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent planact = new Intent(GridActivity.this,PlanActivity.class);
                planact.putExtra("user_name", user_name);
                startActivity(planact);

            }
        });

        event.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent eveact = new Intent(GridActivity.this,EventActivity.class);
                eveact.putExtra("user_name", user_name);
                startActivity(eveact);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridActivity.this, Popup_socket_connect.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridActivity.this, Popup_socket_connect.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

        /**사진들을 보여줄 그리드 뷰의 어댑터 객체를 정의하고 이 뷰의 어댑터로 설정해줍니다.**/
        GridView gridViewImages = findViewById(R.id.grid_recyclerview);
        GridAdapter gridadapter = new GridAdapter(this, imageIDs);
        gridViewImages.setAdapter(gridadapter);
    }


    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000)
        {
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis()-time<2000)
            finish();
    }
}