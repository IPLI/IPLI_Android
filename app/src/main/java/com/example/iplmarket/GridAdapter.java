package com.example.iplmarket;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private int[] imageIDs;


    public GridAdapter(Context context, int[] imageIDs) {
        super();
        this.context = context;
        this.imageIDs = imageIDs;

    }

    @Override
    public int getCount() {
        return (null != imageIDs) ? imageIDs.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(null!=convertView)
            imageView=(ImageView)convertView;
        else{
            //이미지 뷰의 비트맵을 정의하고 크기를 일정하게 맞춰주도록 한다

            Bitmap bmp= BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bmp=Bitmap.createScaledBitmap(bmp, 320, 340, false);

            //이미지 뷰를 정의합니다 비트맵 객체를 뷰에 지정할 이미지에 정의해준다
            imageView=new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(bmp);

            //사진 항목들의 클릭을 처리할 객체 정의 클릭 리스너로 설정
            ImageClickListener imageViewClickListener=new ImageClickListener(context, imageIDs[position]);
            imageView.setOnClickListener(imageViewClickListener);
        }
        return imageView;
    }
}