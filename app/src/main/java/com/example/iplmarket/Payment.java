package com.example.iplmarket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DecimalFormat;
import java.util.ArrayList;


public class Payment extends AppCompatActivity {    //결제완료 화면

    private RecyclerView recyclerview;
    private TextView payment_price_text;
    private Button payment_check_button;
    private DecimalFormat myFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        List_interface list_interface_temp = (List_interface) List_interface.List_interface_activity;
        list_interface_temp.finish();   //장바구니 액티비티 종료

        myFormatter = new DecimalFormat("###,###원");

        recyclerview = findViewById(R.id.recycler_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ExpandableListAdapter.item_class> data = new ArrayList<>();

        ExpandableListAdapter.item_class places = new ExpandableListAdapter.item_class(ExpandableListAdapter.HEADER, "구매내역",null,0,0);
        places.invisibleChildren = new ArrayList<>();

        ArrayList<item_class> item_list = getIntent().getParcelableArrayListExtra("item_list");
        for(item_class temp : item_list)
            places.invisibleChildren.add(new ExpandableListAdapter.item_class(ExpandableListAdapter.CHILD, temp.getContent_text(), temp.getImage_link(), temp.getPrice(), temp.getQuantity()));

        data.add(places);

        ExpandableListAdapter expAdapter = new ExpandableListAdapter(data);
        recyclerview.setAdapter(expAdapter);

        payment_price_text = findViewById(R.id.payment_price_textview);
        payment_price_text.setText("결제 금액: "+ myFormatter.format(item_class.getCart_price()));

        payment_check_button = findViewById(R.id.payment_check_button);
        payment_check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_class.setCart_price(0);
                finish();
            }
        });
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

