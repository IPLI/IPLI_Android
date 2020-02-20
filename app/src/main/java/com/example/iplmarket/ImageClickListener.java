package com.example.iplmarket;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ImageClickListener implements OnClickListener {
    private Context context;

    private int imageID;

    public ImageClickListener(Context context, int imageID)
    {
        this.context=context;
        this.imageID=imageID;
    }

    public void onClick(View v)
    {
        //확대된 이미지를 보여주는 액티비티를 실행하기 위해 인텐트 객체 정의
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image ID", imageID);
        context.startActivity(intent);
    }

}
