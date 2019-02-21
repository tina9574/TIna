package com.example.master.ace_login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class SignInActivity extends AppCompatActivity {
    //SendDataToServer 할때 필요한 변수들
    public int SGI_RSP = 3;         //이 response가 2개 이상일 경우에 필요하다
    public int responseCode=0;
    public int seq_num = 0;
    //SendDataToServer 할때 필요한 변수들

    Button btn_sign_in;
    EditText signin_name, signin_password;

    String Email;
    String Password;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sign in 누르면 Main 화면으로 이동
        setContentView(R.layout.activity_signin);

        //permission 요청
        // Activity에서 실행하는경우
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE }, 1);
        //permission 요청

        signin_name = (EditText)findViewById(R.id.signin_name);
        signin_password = (EditText)findViewById(R.id.signin_password);
        btn_sign_in = (Button)findViewById(R.id.btn_sign_in);


        ((Button) btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = signin_name.getText().toString();
                String userPassword = signin_password.getText().toString();
                if(userID.equals(""))
                {
                    Toast.makeText(SignInActivity.this, "please fill in the e-mail", Toast.LENGTH_SHORT).show();
                    signin_name.requestFocus();
                }
                else if(userPassword.equals(""))
                {
                    Toast.makeText(SignInActivity.this, "please fill in the password", Toast.LENGTH_SHORT).show();
                    signin_password.requestFocus();
                }
                else {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerSignInCheck(v);
                    //SendDataToServer사용하는 구간 여기까지
                    if(responseCode == 1)
                    {
                        //로그인 성공
                        Toast.makeText(SignInActivity.this, "SignIn!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        //이 Activity에서 MainActivity로 값을 넘겨줌
                        intent.putExtra("seq_num",seq_num);
                        startActivity(intent);
                    }
                    else
                    {
                        //로그인 실패
                        Toast.makeText(SignInActivity.this, "Incorrect User Information", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    // Account 누르면 Sign up 화면으로 이동
    public void onClick_Sign_up(View view){

        Intent intent = new Intent( SignInActivity.this , SignUpActivity.class);
        startActivity( intent );
    }

    // Forgot 누르면 Forgotten password Change 화면으로 이동
    public void onClick_forgot(View view){

        {
            Intent intent = new Intent(SignInActivity.this , ForgottenActivity.class);
            startActivity(intent);
        }
    }

    public void SendDataToServerSignInCheck(View v) {

        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//
        Email = signin_name.getText().toString();
        Password = signin_password.getText().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("E-mail" , Email);
            post_dict.put("Password", Password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//

        if (post_dict.length() > 0) {
            try
            {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict),"sign_in_check").get());
                //section 4//

                try
                {
                    //section 5 받을 값만 받으면 된다 ()안에 값은 서버랑 일치 시키도록해야함
                    responseCode = obj.getInt("responseCode");
                    seq_num = obj.getInt("seq_num");
                    //section 5
                }
                catch (JSONException e)
                {
                    Log.i("teamA", "JSONError : " + e.toString());
                }
            }
            catch (Exception e)
            {
                Log.i("teamA",e.toString());
            }
        }
    }
}
