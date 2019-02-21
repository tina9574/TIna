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

import java.util.ArrayList;
import java.util.Calendar;

public class InfoChangeActivity extends AppCompatActivity {
    final int MIC_RSP = 10;
    final int IPC_RSP = 9;
    final int PCM_RSP = 7;
    public int responseHeader=0;
    public int responseCode=0;
    public int seq_num = 0;

    Spinner month_spn, day_spn, year_spn;
    ArrayList<String> years;
    EditText email,user_name,user_tel,new_password,reconfirm_pw;
    Button CompleteButton;
    Button btn_pw_change;
    private AlertDialog dialog;

    String Email;
    String Name;
    String Birth;
    String Phone;
    String NewPassword;

    public void onClick_signout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
        dialog = builder.setMessage("Are you sure to want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(InfoChangeActivity.this, SignInActivity.class);
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

        //layout - java 연결 ( 화면)
        setContentView(R.layout.activity_info_change);

        //seq_num 매칭
        seq_num = MainActivity.seq_num;

        //리소스 매칭 ( java코드 - xml)
        month_spn = (Spinner) findViewById(R.id.month);
        day_spn = (Spinner) findViewById(R.id.day);
        year_spn = (Spinner) findViewById(R.id.year);
        user_name = (EditText)findViewById(R.id.signin_name);
        user_tel = (EditText)findViewById(R.id.signup_tel);
        new_password = (EditText)findViewById(R.id.new_password);
        reconfirm_pw = (EditText)findViewById(R.id.reconfirm_pw);
        CompleteButton = (Button)findViewById(R.id.Complete);
        btn_pw_change = (Button)findViewById(R.id.btn_pw_change);


        //monthAdapter 어뎁터 선언
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dayAdapter 선언
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //yearAdapter 선언
        years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("YYYY");
        for (int i = 1910; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        //어뎁터 등록
        month_spn.setAdapter(monthAdapter);
        month_spn.setEnabled(false);
        day_spn.setAdapter(dayAdapter);
        day_spn.setEnabled(false);
        year_spn.setAdapter(yearAdapter);
        year_spn.setEnabled(false);


        //Change now 버튼
        CompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = user_name.getText().toString();
                String userPhone = user_tel.getText().toString();
                // if 빈칸 > 채워달라"1"


                if(userName.equals(""))
                {
                    Toast.makeText(InfoChangeActivity.this, "please fill in the name", Toast.LENGTH_SHORT).show();
                    user_name.requestFocus();
                }

                //PhoneNumber check
                else if(userPhone.equals(""))
                {
                    Toast.makeText(InfoChangeActivity.this, "please fill in the phone number", Toast.LENGTH_SHORT).show();
                    user_tel.requestFocus();
                }
                else
                {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerInfoChangeCheck(v);
                    //SendDataToServer사용하는 구간 여기까지

                    if(responseHeader == MIC_RSP) {
                        if (responseCode == 1) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                            dialog = builder.setMessage("success membership information change")
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
                            //정보가 수정되지 않음
                            Toast.makeText(InfoChangeActivity.this, "fail membership information change", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btn_pw_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Newpassword = new_password.getText().toString();
                String R_Newpassword = reconfirm_pw.getText().toString();


                if(Newpassword.equals(""))
                {
                    Toast.makeText(InfoChangeActivity.this, "please fill in the password", Toast.LENGTH_SHORT).show();
                    new_password.requestFocus();
                }

                else if (R_Newpassword.equals(""))
                {
                    Toast.makeText(InfoChangeActivity.this, "please fill in the reconfirm password", Toast.LENGTH_SHORT).show();
                    reconfirm_pw.requestFocus();
                }

                else if (!Newpassword.equals(R_Newpassword))
                {
                    Toast.makeText(InfoChangeActivity.this, "Confirm your password", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerPWChangeCheck(v);
                    //SendDataToServer사용하는 구간 여기까지

                    if(responseHeader == PCM_RSP) {
                        if (responseCode == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InfoChangeActivity.this);
                            dialog = builder.setMessage("Success password change")
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
                            //정보가 수정되지 않음
                            Toast.makeText(InfoChangeActivity.this, "Fail password change", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    // 회원정보변경
    public void SendDataToServerInfoChangeCheck(View v) {
        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마


        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//

        Name = user_name.getText().toString();
        Phone = user_tel.getText().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("seq_num" , seq_num);
            post_dict.put("Name" , Name);
            post_dict.put("Tel" , Phone);

        } catch (JSONException e) {
            Log.i("teamA","infoChangeError :: "+ e.toString());
            e.printStackTrace();
        }
        //section 3 여기까지//


        if (post_dict.length() > 0) {
            try
            {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict),"changeInformation").get());
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
            }Log.i("teamA1", responseHeader + "  " + responseCode);
        }
    }


    // 비밀번호변경
    public void  SendDataToServerPWChangeCheck(View v) {
        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//
        NewPassword = new_password.getText().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("seq_num", seq_num);
            post_dict.put("newPassword", NewPassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//

        if (post_dict.length() > 0) {
            try {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict), "changePassword_myprofile").get());
                //section 4//

                try {
                    //section 5 받을 값만 받으면 된다 ()안에 값은 서버랑 일치 시키도록해야함
                    responseHeader = obj.getInt("responseHeader");
                    responseCode = obj.getInt("responseCode");
                    //section 5
                } catch (JSONException e) {
                    Log.i("teamA", "JSONError : " + e.toString());
                }
            } catch (Exception e) {
                Log.i("teamA", e.toString());
            }
            Log.i("teamA1", "responseHeader :: " + responseHeader + " responseCode :: " + responseCode  );
        }
    }
}