package com.example.iplmarket;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login_Background extends AsyncTask<String, Void, String> { //로그인 작업을 수행하는 비동기 스레드
    //===================================================================================
    //private String IP = "220.69.208.235";
    private String IP = LoginActivity.IP;
    private String PHP = "login.php";
    //===================================================================================

    private Context context;

    private TextInputLayout id_textLayout;
    private TextInputLayout pw_textLayout;
    private TextInputEditText id_textField;
    private TextInputEditText pw_textField;
    private TextInputLayout port_textLayout;
    private TextInputEditText port_textField;

    public Login_Background(Context context, TextInputLayout id_textLayout, TextInputLayout pw_textLayout, TextInputLayout port_textLayout, TextInputEditText id_textField, TextInputEditText pw_textField, TextInputEditText port_textField){
        this.context=context;
        this.id_textLayout = id_textLayout;
        this.pw_textLayout = pw_textLayout;
        this.port_textLayout = port_textLayout;
        this.id_textField = id_textField;
        this.pw_textField = pw_textField;
        this.port_textField = port_textField;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = null;
        StringBuffer param = new StringBuffer();
        param.append("id").append("=").append(strings[0]).append("&");
        param.append("pw").append("=").append(strings[1]);

        Log.d("junjun params", param.toString());
        try{
            URL server = new URL("http://"+IP+"/"+PHP);
            //====[1] : urlConnection 설정====
            HttpURLConnection urlConnection = (HttpURLConnection) server.openConnection();
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
                //====[4] : 읽어온 결과물 확인====
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                data = sb.toString();
                Log.d("junjun ReadResult: ", data);
            }
            else
                Log.e("junjun", "연결 실패");

            urlConnection.disconnect();
            return data;

        }catch(Exception e)
        {
            Log.e("junjun ex","Exception" + e.getMessage());
            return "Exception" + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {   //백그라운드 작업 수행 후 실행되는 메서드
        super.onPostExecute(result);

        Log.d("junjun sendedResult: ", result);
        if (result.contains("10")) {    //로그인 성공
            result = result.substring(3);
            Log.d("junjun : ", result);
            Intent intent = new Intent(context, GridActivity.class);
            intent.putExtra("user_name", result);
            context.startActivity(intent);
            //((Activity)context).finish();
            Log.d("junjun login0: ", "성공" + result);
        }
        else if (result.equals("00")) { //아이디 불일치
            Log.e("junjun login1: ", "에러:::Can not find ID" + "code:" + result);
            id_textLayout.setError("아이디가 잘못되었습니다.");
            pw_textLayout.setError(null);
            Toast.makeText(context, "아이디가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else if (result.equals("01")) { //비밀번호 불일치
            Log.e("junjun login2: ", "에러:::Can not find PW" + "code:" + result);
            pw_textLayout.setError("비밀번호가 잘못되었습니다.");
            id_textLayout.setError(null);
            Toast.makeText(context, "비밀번호 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else    //그 이외 에러
            Log.e("junjun login3: ", "에러:::ERROR" + "code:" + result);
    }
}
