package com.example.master.ace_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgottenActivity extends AppCompatActivity {
    //SendDataToServer 할때 필요한 변수들
    final int FPU_RSP = 5;
    final int NPU_RSP = 6;        //이 response가 2개 이상일 경우에 필요하다
    public int responseHeader=0;
    public int responseCode=0;
    public int seq_num = 0;
    //SendDataToServer 할때 필요한 변수들

    private AlertDialog dialog;

    EditText email_address;
    Button btn_ok;
    Button btn_send;
    EditText forgotten_email,authenticode,new_pw;

    String Email;
    String EmailAuthenticode;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten);
        email_address = (EditText)findViewById(R.id.email_address);

        //리소스 매칭 ( java코드 - xml)
        forgotten_email = (EditText)findViewById(R.id.forgotten_email);
        authenticode = (EditText)findViewById(R.id.authenticode);
        new_pw = (EditText)findViewById(R.id.new_pw);



        //finish 눌러서 종료
        btn_ok=(Button)findViewById(R.id.btn_ok);
        btn_send=(Button)findViewById(R.id.btn_send);


        // authenticode 받기 위해 이메일 입력하하는 버튼
        ((Button) btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String useremail = email_address.getText().toString();

                    if(useremail.equals(""))
                    {
                        Toast.makeText(ForgottenActivity.this, "please fill in the e-mail", Toast.LENGTH_SHORT).show();
                        email_address.requestFocus();
                    }
                    else{

                        //SendDataToServer 사용하는 구간
                        SendDataToServerForgottenCheck(v);
                        //SendDataToServer사용하는 구간 여기까지

                        if(responseHeader == FPU_RSP) {

                            if (responseCode == 1)
                            {
                            Toast.makeText(ForgottenActivity.this, "Send Authenticode to your E-mail", Toast.LENGTH_SHORT).show();
                            }
                            else {
                            Toast.makeText(ForgottenActivity.this, "E-mail is not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });



        //비밀번호 변경 버튼
        ((Button) btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_email = forgotten_email.getText().toString();
                String authentication = authenticode.getText().toString();
                String new_password = new_pw.getText().toString();

                if (user_email.equals(""))
                {
                    Toast.makeText(ForgottenActivity.this, "please fill in the e-mail", Toast.LENGTH_SHORT).show();
                    forgotten_email.requestFocus();
                }

                else if (authentication.equals(""))
                {
                    Toast.makeText(ForgottenActivity.this, "please fill in the authenticode", Toast.LENGTH_SHORT).show();
                    authenticode.requestFocus();
                }

                else if (new_password.equals(""))
                {
                    Toast.makeText(ForgottenActivity.this, "please fill in the new_password", Toast.LENGTH_SHORT).show();
                    new_pw.requestFocus();
                }
                else
                {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerNewPwCheck(v);
                    //SendDataToServer사용하는 구간 여기까지

                    if(responseHeader == NPU_RSP) {
                        if (responseCode == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgottenActivity.this);
                            dialog = builder.setMessage("success to create New_Password")
                                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .create();
                            dialog.show();
                        }
                        else {
                            //비밀번호 변경 불가능
                            Toast.makeText(ForgottenActivity.this, "fail to create New_Password", Toast.LENGTH_SHORT).show();
                    }

                    }
                }

            }
        });

    }

    public void SendDataToServerForgottenCheck(View v) {
        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//
        Email = email_address.getText().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("E-mail" , Email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//


        if (post_dict.length() > 0) {
            try
            {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict),"emailCheck").get());
                //section 4//

                try
                {
                    //section 5 받을 값만 받으면 된다 ()안에 값은 서버랑 일치 시키도록해야함
                    responseHeader = obj.getInt("responseHeader");
                    responseCode = obj.getInt("responseCode");
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
            Log.i("teamA1","forgotten password::" + responseHeader + "  " + responseCode);
        }
    }

    public void SendDataToServerNewPwCheck(View v) {

        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//

        Email = email_address.getText().toString();
        EmailAuthenticode = authenticode.getText().toString();
        Password = new_pw.getText().toString();

        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("E-mail" , Email);
            post_dict.put("Email authenticode", EmailAuthenticode);
            post_dict.put("Password", Password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//


        if (post_dict.length() > 0) {
            try
            {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict),"changePassword").get());
                //section 4//

                try
                {
                    //section 5 받을 값만 받으면 된다 ()안에 값은 서버랑 일치 시키도록해야함
                    responseHeader = obj.getInt("responseHeader");
                    responseCode = obj.getInt("responseCode");
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
            Log.i("teamA1","forgotten password::" + responseHeader + "  " + responseCode);
        }
    }


}






