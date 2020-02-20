package com.example.iplmarket;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {   //장바구니
    private ArrayList<item_class> datalist;
    private DecimalFormat myFormatter = new DecimalFormat("###,###원");

    private int lastPosition = -1;

    public int getLastPosition() { return lastPosition; }
    public void setLastPosition(int lastPosition) { this.lastPosition = lastPosition; }

    //데이터셋을 가져올 수 있게 생성자 생성
    public MyAdapter(ArrayList<item_class> items) {
        this.datalist = items;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView quantity_text, content_text, price_text;
        private ImageView image;

        MyViewHolder(View v) {
            super(v);
            //위젯을 찾아서 바인딩
            image = v.findViewById(R.id.image);
            quantity_text = v.findViewById(R.id.quantity_text);
            content_text = v.findViewById(R.id.content_text);
            price_text = v.findViewById(R.id.price_text);
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {   //view가 클릭됬을경우 감지   //클릭되었을 경우 db접속도 고려
                    Intent intent = new Intent(v.getContext(), Popup.class);    //해당 class가 아니라 v.getContext()인 이유는 view에서 액티비티 실행해야해서
                    //intent.putExtra("content_text", content_text.getText().toString()); //intent로 데이터 전달

                    intent.putExtra("content_text", datalist.get(getAdapterPosition()).getContent_text()); //intent로 데이터 전달
                    intent.putExtra("item_info", datalist.get(getAdapterPosition()).getInfo());
                    v.getContext().startActivity(intent);   //view에서 intent한 액티비티를 시작
                }
            });
        }
    }
    // 새로운 view를 생성
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_list_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //view의 콘텐츠를 재배치할때마다 실행됨됨
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - 데이터셋에서 인덱스가 position인 데이터를 불러옴
        // - view의 콘텐츠를 바꿈
        holder.content_text.setText(datalist.get(position).getContent_text());
        holder.quantity_text.setText("수량: " + datalist.get(position).getQuantity());

        Glide.with(holder.image.getContext()).load(datalist.get(position).getImage_link()).into(holder.image);
        holder.price_text.setText("가격: " + myFormatter.format(datalist.get(position).getTotalPrice()) );

        Log.d("junjun", "pos: " + position + "    lastPos: " + lastPosition);
        setAnimation(holder.itemView, position);
        Log.d("junjun", "afterLastPos: " + lastPosition);
    }

    // 데이터셋의 크기를 반환
    @Override
    public int getItemCount() {
        return datalist.size();
    }

    private void setAnimation(View viewToAnimate, int position) //애니메이션 지정
    {
        if(position > lastPosition) //새로 생성된 뷰만 애니메이션 나타나게
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;    //현재 pos를 lastpos에다 넣어서 마지막 뷰의 위치를 저장
        }
    }
}