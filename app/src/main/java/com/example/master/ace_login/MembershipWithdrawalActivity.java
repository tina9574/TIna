package com.example.master.ace_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MembershipWithdrawalActivity extends AppCompatActivity {
    //SendDataToServer 할때 필요한 변수들
    final int MWD_RSP = 8;
    public int responseHeader=0;
    public int responseCode=0;
    public int seq_num = 0;
    //SendDataToServer 할때 필요한 변수들

    private AlertDialog dialog;
    Spinner reason_spn;
    Button btn_check;
    Button btn_withdrawal;
    EditText mem_password;

    String Password;
    String Reason;

    public void onClick_signout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MembershipWithdrawalActivity.this);
        dialog = builder.setMessage("Are you sure to want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MembershipWithdrawalActivity.this, SignInActivity.class);
                        startActivity(intent);

                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_withdrawal);

        //seq_num 매칭
        seq_num = MainActivity.seq_num;

        //리소스 매칭 ( java코드 - xml)
        reason_spn = (Spinner) findViewById(R.id.withdrawal_reason);
        btn_check = (Button)findViewById(R.id.btn_check);
        btn_withdrawal = (Button)findViewById(R.id.btn_withdrawal);
        mem_password = (EditText)findViewById(R.id.mem_password);


        //withdrawal_reasonAdapter 어뎁터 선언
        ArrayAdapter<CharSequence> withdrawal_reasonAdapter = ArrayAdapter.createFromResource(this, R.array.withdrawal_reason, android.R.layout.simple_spinner_item);
        withdrawal_reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //어뎁터 등록
        reason_spn.setAdapter(withdrawal_reasonAdapter);


        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mem_password.equals(""))
                {
                    Toast.makeText(MembershipWithdrawalActivity.this, "please fill in the e-mail", Toast.LENGTH_SHORT).show();
                    mem_password.requestFocus();
                }
                else {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerIdCancelCheck(v);

                    if(responseCode == 1)
                    {
                        //IDCancellation 성공
                        AlertDialog.Builder builder = new AlertDialog.Builder(MembershipWithdrawalActivity.this);
                        dialog = builder.setMessage(" Are you sure to want to withdraw? ")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent( MembershipWithdrawalActivity.this, SignInActivity.class);
                                        startActivity(intent);

                                        finishAffinity();

                                    }
                                }).create();
                        dialog.show();
                    }
                    else
                    {
                        //비밀번호 맞지 않음
                        Toast.makeText(MembershipWithdrawalActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }
                //SendDataToServer사용하는 구간 여기까지

            }
        });
    }

    public void SendDataToServerIdCancelCheck(View v) {
        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//
        Password = mem_password.getText().toString();
        Reason = reason_spn.getSelectedItem().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("Password", Password);
            post_dict.put("Reason", Reason);
            post_dict.put("seq_num", seq_num);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//

        if (post_dict.length() > 0) {
            try
            {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict),"realIDCancellation").get());
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

        }
    }
}
