package com.example.iplmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;



public class LoginActivity extends AppCompatActivity {
    //static public String IP = "220.69.208.235";     //이 IP 바꾸면 사용하는곳 모두 바뀜
    static public String IP = "172.20.10.8";     //이 IP 바꾸면 사용하는곳 모두 바뀜
    TextInputLayout id_textLayout, pw_TextLayout, port_textLayout;
    TextInputEditText id_textField, pw_textField, port_textField;
    TextView create_account_text;
    boolean id_input_check=false, pw_input_check=false;
    Button login_button;

    InputMethodManager imm;
    LinearLayout top_layout;

    public static Activity Login_Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Login_Activity = LoginActivity.this;

        setContentView(R.layout.activity_login);

        id_textLayout = findViewById(R.id.id_textLayout);
        pw_TextLayout = findViewById(R.id.pw_textLayout);

        id_textField =  findViewById(R.id.id_textField);
        pw_textField = findViewById(R.id.pw_textField);


        login_button = findViewById(R.id.login_button);

        create_account_text = findViewById(R.id.create_account_button);


        //===========버튼 활성화 이벤트===============
        login_button.setEnabled(false); //버튼을 비활성화
        id_textField.addTextChangedListener(new TextWatcher() { //Edittext의 텍스트 변화 감지
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}  //입력 전
            @Override
            public void afterTextChanged(Editable editable) {}  //입력 후
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //입력 중
                id_input_check = !(TextUtils.isEmpty(charSequence));   //id 공백체크
                set_button();   //버튼 활성화/비활성화
                //Log.d("jun",charSequence.toString()+"test id");
            }
        });

        pw_textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //입력상태일때 작동
                pw_input_check = !(TextUtils.isEmpty(charSequence));   //pw 공백체크
                set_button();   //버튼 활성화/비활성화

            }
        });
        //============================================


        //============키보드내리기, 커서설정===============
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        top_layout = (LinearLayout)findViewById(R.id.top_layout);
        top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(top_layout.getWindowToken(),0);
            }
        });
        //=================================================

        //============버튼클릭 키보드내림=============
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(top_layout.getWindowToken(),0);

                String id_text = id_textField.getText().toString();
                String pw_text = pw_textField.getText().toString();

                Login_Background loginBackground = new Login_Background(LoginActivity.this, id_textLayout, pw_TextLayout, port_textLayout, id_textField, pw_textField, port_textField);
                loginBackground.execute(id_text, pw_text);

            }
        });
        //=============================================

        create_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 레이아웃으로 넘어감(로그인 엑티비티는 종료 x)
                Intent intent = new Intent(LoginActivity.this, create_account.class);
                startActivity(intent);
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

    public void set_button()
    {
        if (id_input_check && pw_input_check)    //id와 pw가 모두 입력되어있다면
            login_button.setEnabled(true);
        else
            login_button.setEnabled(false);
    }


}

