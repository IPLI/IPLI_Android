package com.example.iplmarket;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class List_interface extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<item_class> items = new ArrayList<>();
    public String item;
    private final MyHandler handler = new MyHandler(this);
    private TextView user_id_view, cart_price_view;
    private Button pay_button;
    private String json_string;
    private DecimalFormat myFormatter;
    private ReceiveThread reciv_thread;
    public static Activity List_interface_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List_interface_activity = List_interface.this;  //다른 액티비티에서 종료하기 위해 자기 자신을 지칭하는 변수 생성

        myFormatter = new DecimalFormat("###,###원");

        setContentView(R.layout.activity_list); //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        String id = getIntent().getStringExtra("user_name");

        user_id_view = findViewById(R.id.user_id);
        user_id_view.setText(id+"님의 장바구니");

        cart_price_view = findViewById(R.id.cart_price_textview);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pay_button = findViewById(R.id.pay_button);

//        items.add(new item_class("1", "코카콜라", "https://i.imgur.com/TCDdCWy.png", "asdf", 1500, 2));
//        items.add(new item_class("2", "코카콜라", "https://i.imgur.com/TCDdCWy.png", "asdf", 1500, 2));
        pay_button.setEnabled(false);

        Client c1 = new Client();   //쓰레드 시작
        c1.start();


        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Popup_paycheck.class);
                intent.putExtra("item_list", items);
                view.getContext().startActivity(intent);
            }
        });
        // specify an adapter
        mAdapter = new MyAdapter(items);
        recyclerView.setAdapter(mAdapter);
    }


    public void add_remove_item(Message msg) {  //아이템 추가, 삭제 함수
        item = (String)msg.obj;
        Log.d("junjun sended by thread", item);

        if(msg.what == 1) { //여러개 넘어오는거면 왓을 개수로 설정하고 폴문으로 들어온 수만큼 반복
            boolean check = false;  //포문 삭제하고 처음에 체크는 폴스니까 하나도없음 나중에 하나라도 들어오면 체크는 트루 모두 삭제되면 폴스

            for (int i = 0; i < mAdapter.getItemCount(); i++)    //포이치 문 가능?
            {
                if (items.get(i).getNum().equals(item)) { // items에서 코카콜라 검색
                    Log.d("junjun", item + "수량증가");
                    check = true;   //items에서 코카콜라가 있다면 표시해둠
                    items.get(i).addQuantity(msg.arg1);
                    items.get(i).setTotalPrice();
                    mAdapter.notifyItemChanged(i);    //어뎁터에 data가 바뀌었다고 전달
                    cart_price_view.setText("총 금액: " + myFormatter.format(item_class.getCart_price()));
                    break;
                }
            }
            if (!check) {    //만약 items에 콜라가 없다면
                GetItem getItem = new GetItem(msg.arg1);
                getItem.execute(item);
                Log.d("junjun", item + " 생성");
            }

            Log.d("junjun cart price", item_class.getCart_price()+"");
        }
        else if(msg.what == 0){
            int count = mAdapter.getItemCount();
            if(count >0)
            {
                for(int i=0; i<count; i++)
                {
                    if(items.get(i).getNum().equals(item)) //코카콜라 view가 있을경우
                    {
                        if(items.get(i).getQuantity() <= msg.arg1) {   //수량이 빼는 수와 같다면 뷰를 삭제
                            items.get(i).zeroQuantity(msg.arg1);
                            items.remove(i);
                            mAdapter.notifyItemRemoved(i);
                            cart_price_view.setText("총 금액: " + myFormatter.format(item_class.getCart_price()));
                            //뷰를 삭제하면 lastpos를 1개 줄임(새로 생성하면 애니메이션 적용하게)
                            ((MyAdapter) mAdapter).setLastPosition(mAdapter.getItemCount()-1);
                            break;
                        }
                        else {  //수량이 빼는것보다 많다면 그만큼 뺌
                            items.get(i).decQuantity(msg.arg1);
                            items.get(i).setTotalPrice();
                            mAdapter.notifyDataSetChanged();
                            cart_price_view.setText("총 금액: " + myFormatter.format(item_class.getCart_price()));
                            break;
                        }
                    }
                }
            }
        }
        else{
            Log.d("junjun receive error", "조회되지 않는 상품");
        }
        if(item_class.getCart_price() == 0)
            pay_button.setEnabled(false);
        else
            pay_button.setEnabled(true);
    }

    private static class MyHandler extends Handler{
        private final WeakReference<List_interface> mActivity;
        MyHandler(List_interface activity){
            mActivity = new WeakReference<List_interface>(activity);        }

        @Override
        public void handleMessage(Message msg){
            if(msg.what < 2)
            {
                List_interface activity = mActivity.get();
                Log.d("junjun msg", msg.obj.toString());
                //Log.d("junjun activity", activity.toString());
                if (activity != null) {
                    activity.add_remove_item(msg);

                    if(item_class.getCart_price() == 0)
                        activity.pay_button.setEnabled(false);
                    else
                        activity.pay_button.setEnabled(true);
                }
            }
            else if(msg.what == 2)
            {
                Intent intent = new Intent(List_interface_activity, Popup_badrequest.class);
                List_interface_activity.startActivity(intent);
                item_class.setCart_price(0);
            }
            else if(msg.what == 4)
            {
                Intent intent = new Intent(List_interface_activity, Popup_item_error.class);
                List_interface_activity.startActivity(intent);
            }
            else
                Log.d("junjun msg error", "확인되지 않은 부호");
        }
    }

    class Client extends Thread{
        //private String IP = "220.69.208.235";
        private String IP = LoginActivity.IP;
        public String socketip = getIntent().getStringExtra("ip");

        @Override
        public void run()
        {
            try
            {
                Socket socket = new Socket(IP, 8080);
                if(socket.isClosed()) {
                    Log.d("junjun socket", "socket connect failed");
                }
                else{
                    Log.d("junjun socket", "socket connected");
                    reciv_thread = new ReceiveThread(handler);
                    reciv_thread.setSocket(socket);
                    reciv_thread.setIp(socketip);
                    reciv_thread.start();
                    Log.d("jun"," "+this.isAlive());
                }
            }
            catch (IOException e){
                e.printStackTrace();
                Log.d("junjun connection error", "error");
            }

        }
    }

    private class GetItem extends AsyncTask<String, Void, String> {

        //private String IP = "220.69.208.235";
        private String IP = LoginActivity.IP;
        private String PHP = "getitem.php";
        int quantity;

        GetItem(int quantity){
            this.quantity = quantity;
        }
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            StringBuffer param = new StringBuffer();
            param.append("item_num").append("=").append(strings[0]);

            Log.d("junjun params", param.toString());
            try{
                // 서버 연결
                URL server = new URL("http://"+IP+"/"+PHP);

                //====[1] : urlConnection 설정====
                //해당 주소로 접속하고, 단일 HTTP접속을 하기위해 캐스트함
                HttpURLConnection urlConnection = (HttpURLConnection) server.openConnection();
                urlConnection.setConnectTimeout(1000);
                urlConnection.setReadTimeout(3000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                //====[2] : 파라미터 전달, 데이터 읽어오기====
                OutputStream outs = urlConnection.getOutputStream();
                outs.write(param.toString().getBytes("UTF-8"));
                outs.flush();
                outs.close();

                //====[3] : 연결 요청 확인====
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    StringBuffer sb = new StringBuffer();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    data = sb.toString();
                    Log.d("junjun ReadResult: ", data);
                    urlConnection.disconnect();
                }
                else
                    Log.e("junjun", "연결 실패");
                return data.trim();

            }catch(Exception e)
            {
                return new String("Exception" + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null)
                ;
            else
            {
                json_string = result;
                Log.d("junjun json", json_string);
                create_item(quantity);
                if(item_class.getCart_price() == 0)
                    pay_button.setEnabled(false);
                else
                    pay_button.setEnabled(true);
            }
        }
    }

    @SuppressLint("CheckResult")
    private void create_item(int quantity){ //json 가공
        String TAG_JSON = "item";
        String TAG_NUMBER = "number";
        String TAG_ENGN = "engname";
        String TAG_KORN = "korname";
        String TAG_INFO = "info";
        String TAG_IMG = "image";
        String TAG_PRICE = "price";
        try{
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            jsonObject = jsonArray.getJSONObject(0);

            String num = jsonObject.getString(TAG_NUMBER);
            String eng_name = jsonObject.getString(TAG_ENGN);
            String kor_name = jsonObject.getString(TAG_KORN);
            String info = jsonObject.getString(TAG_INFO);
            String img_link = jsonObject.getString(TAG_IMG);
            int price = Integer.parseInt(jsonObject.getString(TAG_PRICE));

            item_class ic = new item_class(num, kor_name, img_link, info, price, quantity);
            items.add(ic);
            mAdapter.notifyDataSetChanged();
            cart_price_view.setText("총 금액: " +myFormatter.format(ic.getCart_price()));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000)
        {
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis()-time<2000) {
            Intent intent = new Intent(List_interface.this, Popup_out_cart.class);
            startActivity(intent);
        }
    }

}
