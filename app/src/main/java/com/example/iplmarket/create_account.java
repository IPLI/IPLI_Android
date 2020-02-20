package com.example.iplmarket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class create_account extends AppCompatActivity {
    //===================================================================================
    //private String IP = "220.69.208.235";
    private String IP = LoginActivity.IP;
    private String PHP = "datatoserver.php";
    //===================================================================================
    LinearLayout create_account_layout;
    TextInputEditText id_text, pw_text, pw_text2, name_text;
    TextInputLayout id_textLyaout, pw_textLyaout, pw_textLyaout2, name_textLyaout;
    String ID, PW, NAME;
    Button create_account_button;
    InputMethodManager imm;

    Boolean id_input_check=false, pw_input_check=false, pw_input_check2=false, name_input_check=false;


    String email_regExp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,5}";

    // 비밀번호 유효성 검사식 1 : 숫자, 특수문자가 포함되야함
    String pw_regExp = "^([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,-,_,~])|([!,@,#,$,%,^,&,*,?,-,_,~].*[a-zA-Z0-9])";
    // 정규표현식 컴파일
    Pattern id_pattern_symbol = Pattern.compile(email_regExp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //위젯 바인딩
        create_account_layout = findViewById(R.id.create_account_layout);
        id_text = findViewById(R.id.id_textField);
        pw_text = findViewById(R.id.pw_textField);
        pw_text2 = findViewById(R.id.pw_textField2);
        name_text = findViewById(R.id.name_textField);

        id_textLyaout = findViewById(R.id.id_textLayout);
        pw_textLyaout = findViewById(R.id.pw_textLayout);
        pw_textLyaout2 = findViewById(R.id.pw_textLayout2);
        name_textLyaout = findViewById(R.id.name_textLayout);

        create_account_button = findViewById(R.id.create_account_button);
        //===========

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);   //키보드 내리기하려고 해둔거

        create_account_button.setEnabled(false);

        id_text.addTextChangedListener(new TextWatcher() { //ID - Edittext의 텍스트 변화 감지
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}  //입력 전
            @Override
            public void afterTextChanged(Editable editable) {  //입력 후
                if (!id_input_check) {  //조건에 맞지 않을경우 에러메시지 표시
                    id_textLyaout.setErrorEnabled(true);
                    id_textLyaout.setError("이메일 형식과 다릅니다.");
                }else { //맞으면 에러 삭제
                    id_textLyaout.setErrorEnabled(false);
                    id_textLyaout.setError(null);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //입력 중
                // 문자 매칭
                Matcher id_matcher_symbol = id_pattern_symbol.matcher(charSequence);
                id_input_check = id_matcher_symbol.find();  // true:유효성검사 통과
                Log.d("jun id", String.valueOf(id_input_check));
                set_button();
            }
        });

        pw_text.addTextChangedListener(new TextWatcher() { //PW - Edittext의 텍스트 변화 감지
            Pattern pw_pattern_symbol = Pattern.compile(pw_regExp);

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}  //입력 전
            @Override
            public void afterTextChanged(Editable editable) {  //입력 후
                if (!pw_input_check) {
                    pw_textLyaout.setErrorEnabled(true);
                    pw_textLyaout.setError("비밀번호 형식과 다릅니다.");
                }else {
                    pw_textLyaout.setErrorEnabled(false);
                    pw_textLyaout.setError(null);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //입력 중
                Matcher pw_matcher_symbol = pw_pattern_symbol.matcher(charSequence);
                pw_input_check = pw_matcher_symbol.find();
                Log.d("jun pw", String.valueOf(pw_input_check));
                set_button();
            }
        });
        pw_text2.addTextChangedListener(new TextWatcher() { //PW확인 - Edittext의 텍스트 변화 감지
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}  //입력 전
            @Override
            public void afterTextChanged(Editable editable) {
                if (!pw_input_check2) {
                    pw_textLyaout2.setErrorEnabled(true);
                    pw_textLyaout2.setError("비밀번호가 다릅니다.");
                }else {
                    pw_textLyaout2.setErrorEnabled(false);
                    pw_textLyaout2.setError(null);
                }
            }  //입력 후
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //입력 중
                pw_input_check2 = (charSequence.toString().equals(pw_text.getText().toString()));
                Log.d("jun pw2", String.valueOf(pw_input_check2));
                set_button();
            }
        });
        name_text.addTextChangedListener(new TextWatcher() { //NAME - Edittext의 텍스트 변화 감지
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}  //입력 전
            @Override
            public void afterTextChanged(Editable editable) {}  //입력 후
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //입력 중
                name_input_check = !(TextUtils.isEmpty(charSequence));
                Log.d("jun name", String.valueOf(name_input_check));
                set_button();
            }
        });

        // 계정생성 버튼 클릭 이벤트 처리
        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(create_account_button.getWindowToken(), 0);

                ID = id_text.getText().toString();
                PW = pw_text.getText().toString();
                NAME = name_text.getText().toString();

                //Async 객체 생성
                postAccount send_account_to_server = new postAccount();
                //execute로 실행
                send_account_to_server.execute(ID, PW, NAME);
            }
        });
    }

    public void linearOnClick(View v){  //화면 클릭하면 키보드 내려가게
        imm.hideSoftInputFromWindow(create_account_layout.getWindowToken(),0);
    }

    class postAccount extends AsyncTask<String, Void, String>{  //회원가입 진행 스레드

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer userDATA = new StringBuffer();
            userDATA.append("id").append("=").append(strings[0]).append("&");
            userDATA.append("pw").append("=").append(strings[1]).append("&");
            userDATA.append("name").append("=").append(strings[2]);
            Log.d("junjun parameter", userDATA.toString());

            try{ //예외처리 안해줄시 오류발생해서 해줘야함
                URL server =  new URL("http://"+IP+"/"+PHP);    //URL 클래스의 생성자로 주소를 넘겨줌
                Log.d("junjun URL", server.toString());

                //====[1] : urlConnection 설정====
                //해당 주소로 접속하고, 단일 HTTP접속을 하기위해 캐스트함
                HttpURLConnection urlConnection = (HttpURLConnection)server.openConnection();
                urlConnection.setRequestMethod("POST"); //전달 방식 설정 (post, get) get이 기본값
                urlConnection.setDoInput(true);  //InputStream으로 응답 헤더와 미시지를 읽어들이겠다는 옵션을 정의
                urlConnection.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션을 정의
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  //요청 헤더값 설정

                //====[2] : 파라미터 전달, 데이터 읽어오기====
                String StringacURL = userDATA.toString();   //StringBuffer로 저장된 파라미터정보를 String으로 저장
                OutputStream os = urlConnection.getOutputStream();
                os.write(StringacURL.getBytes("UTF-8"));   //UTF-8로 인코딩 가능. 하지만 한글이 전달안될수도있음.
                os.flush(); //스트림의 버퍼를 비워줌
                os.close(); //스트림을 닫음

                //====[3] : 연결 요청 확인====
                //BufferedReader에서 빼온 값을 저장할 변수 line과 line의 값을 합칠 변수 page
                String line;
                StringBuffer page = new StringBuffer("");
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    Log.d("junjun sendData", "HTTP_OK");
                    //====[4] : 읽어온 결과물 확인====
                    //요청한 URL의 출력물을 BufferedReader로 전달받음
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    //라인을 받아와 합친 후 BufferedReader종료, 액티비티 종료
                    while((line = br.readLine()) != null)
                        page.append(line);
                    br.close();
                }
                else
                    Log.d("junjun sendData", "fail");

                urlConnection.disconnect();
                return page.toString();
            }
            catch(Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result){
            Log.d("junjun result", "Result: " + result);
            finish();
        }
    }

    public void set_button()
    {
        if (id_input_check && pw_input_check && pw_input_check2 && name_input_check)
            create_account_button.setEnabled(true);
        else
            create_account_button.setEnabled(false);
    }
}