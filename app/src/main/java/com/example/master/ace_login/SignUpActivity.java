package com.example.master.ace_login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    //SendDataToServer 할때 필요한 변수들
    final int EMC_RSP = 4;
    final int UVC_RSP = 2;
    final int SGU_RSP = 1;
    public int responseHeader=0;
    public int responseCode=0;
    public String authenticode ="";
    //SendDataToServer 할때 필요한 변수들

    Spinner month_spn, day_spn, year_spn;
    ArrayList<String> years;
    EditText user_email, user_password, user_name, reconfirm_pw, user_tel, Email_authenticode;
    Button ToastButton;
    Button btn_check;
    Button btn_authenticode;
    RadioGroup GenderView;

    private AlertDialog dialog;

    String Email;
    // String EmailAuthenticode;
    String Password;
    String Name;
    String Gender;
    String Birth;
    String Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout - java 연결 ( 화면)
        setContentView(R.layout.activity_signup);

        //리소스 매칭 ( java코드 - xml)
        month_spn = (Spinner) findViewById(R.id.month);
        day_spn = (Spinner) findViewById(R.id.day);
        year_spn = (Spinner) findViewById(R.id.year);
        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        user_name = (EditText) findViewById(R.id.signin_name);
        reconfirm_pw = (EditText) findViewById(R.id.reconfirm_pw);
        user_tel = (EditText) findViewById(R.id.signup_tel);
        Email_authenticode = (EditText) findViewById(R.id.Email_authenticode);
        GenderView = (RadioGroup) findViewById(R.id.genderGroup);


        ToastButton = (Button) findViewById(R.id.Complete);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_authenticode = (Button) findViewById(R.id.btn_authenticode);


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
        day_spn.setAdapter(dayAdapter);
        year_spn.setAdapter(yearAdapter);

        //Check 버튼
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = user_email.getText().toString();

                if (userID.equals("")) {
                    Toast.makeText(SignUpActivity.this, "please fill in the e-mail", Toast.LENGTH_SHORT).show();
                    user_email.requestFocus();
                }
                else if (userID.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    dialog = builder.setMessage(" E-mail is exist ")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    dialog.show();
                }
                else {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerEmailCheck(v);
                    //SendDataToServer사용하는 구간 여기까지

                    if(responseHeader == EMC_RSP)
                    {
                        if(responseCode == 1)
                        {
                            //이메일 No 존재 (회원가입 가능)
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            dialog = builder.setMessage(" Receive Authenticode from your E-mail ")
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).create();
                            dialog.show();
                        }
                        else
                        {
                            //회원가입 불가능
                            Toast.makeText(SignUpActivity.this, "E-mail is exist", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

        //Authenticode check
        btn_authenticode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailAuthenticode = Email_authenticode.getText().toString();

                if (EmailAuthenticode.equals("")) {
                    Toast.makeText(SignUpActivity.this, "please fill in the Authenticode", Toast.LENGTH_SHORT).show();
                    Email_authenticode.requestFocus();
                }
                else if (EmailAuthenticode.equals(authenticode)) {
                    user_email.setEnabled(false);
                    Email_authenticode.setEnabled(false);
                    btn_check.setEnabled(false);
                    btn_authenticode.setEnabled(false);
                }
                else {
//                    //SendDataToServer 사용하는 구간
//                    SendDataToServerAuthenticodeCheck(v);
//                    //SendDataToServer사용하는 구간 여기까지
                    if (!authenticode.equals(Email_authenticode.getText().toString()))
                    {
                        // 인증코드가 틀림
                        Toast.makeText(SignUpActivity.this, "Incorrect Authenticode", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // 인증코드 확인 성공
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        dialog = builder.setMessage("Correct Authenticode")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create();
                        dialog.show();
                    }
                }
            }
        });


        //Sign up now 버튼
        ToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPassword = user_password.getText().toString();
                String reconfirmPw = reconfirm_pw.getText().toString();
                String userName = user_name.getText().toString();
                String userPhone = user_tel.getText().toString();
                //String birth = month_spn.getSelectedItem().toString();

                // if 빈칸 > 채워달라"1"

                if (userPassword.equals("")) {
                    Toast.makeText(SignUpActivity.this, "please fill in the password", Toast.LENGTH_SHORT).show();
                    user_password.requestFocus();
                }
                else if (reconfirmPw.equals("")) {
                    Toast.makeText(SignUpActivity.this, "please fill in the reconfirm_password", Toast.LENGTH_SHORT).show();
                    reconfirm_pw.requestFocus();
                }
                else if (userName.equals("")) {
                    Toast.makeText(SignUpActivity.this, "please fill in the name", Toast.LENGTH_SHORT).show();
                    user_name.requestFocus();
                }
                else if (!userPassword.equals(reconfirmPw)) {
                    Toast.makeText(SignUpActivity.this, "Confirm your password", Toast.LENGTH_SHORT).show();
                }
//                //birth month check
//                else if (birth.equals(""))
//                {
//                    if(month_spn.getSelectedItem().toString() == ("MM"))
//                    Toast.makeText(SignUpActivity.this, "please check a birth month", Toast.LENGTH_SHORT).show();
//                    month_spn.requestFocus();
//                }
                // month_spn.getSelectedItem().toString() != "MM"

                //PhoneNumber check
                else if (userPhone.equals("")) {
                    Toast.makeText(SignUpActivity.this, "please fill in the phone number", Toast.LENGTH_SHORT).show();
                    user_tel.requestFocus();
                }
                else
                    {
                    //SendDataToServer 사용하는 구간
                    SendDataToServerSignUpCheck(v);
                    //SendDataToServer사용하는 구간 여기까지

                    if(responseHeader == SGU_RSP)
                    {
                        if (responseCode == 1)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            dialog = builder.setMessage("Success sign up")
                                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .create();
                            dialog.show();
                        }
                        else
                        {
                            //회원가입 불가능
                            Toast.makeText(SignUpActivity.this, "Fail sign up", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    ///////////////////////////////////////////////서버로 데이터 전송할 때 사용//////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// !!!!!!!!!!!!중요!!!!!!!!!!!!!!! //직접 고쳐야 하는 부분////////////////////////////////////////////////////////////////////
//모든 데이터 베이스로 전송해야 하는 Activity코드에는 이 함수가 필수적인데 1개 이상이여도 상관이 없다 SignUpCheck 이 부분은 내가 준 파일을 보고 참고할것/////

   // Email existing Check
    public void SendDataToServerEmailCheck(View v) {

        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//
        Email = user_email.getText().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("E-mail", Email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//

        if (post_dict.length() > 0) {
            try {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict), "EmailAuthenticode").get());
               //section 4//

                try {
                    //section 5 받을 값만 받으면 된다 ()안에 값은 서버랑 일치 시키도록해야함
                    responseHeader = obj.getInt("responseHeader");
                    responseCode = obj.getInt("responseCode");
                    authenticode = obj.getString("authenticode");

                    //section 5
                } catch (JSONException e) {
                    Log.i("teamA", "JSONError : " + e.toString());
                }
            } catch (Exception e) {
                Log.i("teamA", e.toString());
            }
            Log.i("teamA1","signUpAc :: responseHeaer :: " + responseHeader + " responseCode :: " + responseCode  + "  "+authenticode  );
        }
    }
//
//    // E-mail AuthenticodeCheck Json
//    public void SendDataToServerAuthenticodeCheck(View v) {
//
//        //section 0 여기 건들지마
//        JSONObject obj = new JSONObject();
//        SendDataToServer sendDataToServer = new SendDataToServer();
//        //section 0 여기 건들지마
//
//        // section 1  여기서 부터//
//        //보낼 데이터를 전부 String으로 변환하는 구간//
//        EmailAuthenticode = Email_authenticode.getText().toString();
//        //section 1 여기까지 //
//
//        //section 2 여기는 고치치마라//
//        JSONObject post_dict = new JSONObject();
//        //section 2 여기 까지//
//
//        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
//        try {
//            post_dict.put("Email authenticode", EmailAuthenticode);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //section 3 여기까지//
//
//        if (post_dict.length() > 0) {
//            try {
//                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
//                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict), "아아아아아아아ㅏ아아아").get());
//                //section 4//
//
//                try {
//                    //section 5 받을 값만 받으면 된다 ()안에 값은 서버랑 일치 시키도록해야함
//                    responseHeader = obj.getInt("responseHeader");
//                    responseCode = obj.getInt("responseCode");
//                    //section 5
//                } catch (JSONException e) {
//                    Log.i("teamA", "JSONError : " + e.toString());
//                }
//            } catch (Exception e) {
//                Log.i("teamA", e.toString());
//            }
//        }
//    }


    // Sign Up Json
    public void SendDataToServerSignUpCheck(View v) {

        //section 0 여기 건들지마
        JSONObject obj = new JSONObject();
        SendDataToServer sendDataToServer = new SendDataToServer();
        //section 0 여기 건들지마

        // section 1  여기서 부터//
        //보낼 데이터를 전부 String으로 변환하는 구간//
        RadioButton selectedGender;
        String userBirth;

        Password = user_password.getText().toString();
        Name = user_name.getText().toString();
        selectedGender = (RadioButton) findViewById(GenderView.getCheckedRadioButtonId());
        Gender = selectedGender.getText().toString();
        char rGender = Gender.charAt(0);
        userBirth = year_spn.getSelectedItem().toString() + month_spn.getSelectedItem() + day_spn.getSelectedItem();
        Birth = userBirth;
        Log.i("teamA", "Birth ::     " + Birth);
        Log.i("teamA", "Gender ::     " + rGender);
        Phone = user_tel.getText().toString();
        //section 1 여기까지 //

        //section 2 여기는 고치치마라//
        JSONObject post_dict = new JSONObject();
        //section 2 여기 까지//

        //section 3 보내야 하는 값 만큼 매치시켜줘서 보내면됨//
        try {
            post_dict.put("E-mail", user_email.getText().toString() );
            post_dict.put("Password", Password);
            post_dict.put("Name", Name);
            post_dict.put("Gender", rGender);
            post_dict.put("Birth", Birth);
            post_dict.put("Tel", Phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //section 3 여기까지//

        if (post_dict.length() > 0) {
            try {
                //section 4   "signUpCheck 라고 되어있는 부분을 승배가 준 파일로 고쳐서 보낼것 //
                obj = new JSONObject(sendDataToServer.execute(String.valueOf(post_dict), "signUpCheck").get());
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
            Log.i("teamA1","signUpAc :: responseHeader :: " + responseHeader + " responseCode :: " + responseCode  + "  "+authenticode  );
        }
    }
}



