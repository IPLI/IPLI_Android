package com.example.iplmarket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;


public class ImageActivity extends Activity {
    private void setImage(ImageView imv)
    {
        Bundle extras = getIntent().getExtras();
        //확대되는 이미지의 소스를 인텐트드로부터 읽어들이고 그것을 이미지뷰의 이미지 리소스로 설정해준다
        int imageID = extras.getInt("image ID");
        imv.setImageResource(imageID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_image);

        //이미지 뷰 설정 확대되는 이미지를 보여주기 위함
        ImageView imv =findViewById(R.id.imageViewer);
        setImage(imv);
    }

}
