package com.example.master.ace_login;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.jar.Attributes;

public class SendDataToServer extends AsyncTask<String,String,String> {

    //상수선언
    //user management
    final int SGU_RSP = 1;
    final int UVC_RSP = 2;
    final int EMC_RSP = 4;
    final int SGI_RSP = 3;

    final int FPU_RSP = 5;
    final int NPU_RSP = 6;
    final int PCM_RSP = 7;
    final int MWD_RSP = 8;
    final int IPC_RSP = 9;
    final int MIC_RSP = 10;
    //7

    //sensor management
    final int SMR_RSP = 11;
    final int SAS_RSP = 12;
    final int SDD_RSP = 13;
    final int SLV_RSP = 14;

    //data management
    final int SII_RSP = 15;
    final int RAD_RSP = 16;
    final int HII_RSP = 17;
    final int RHD_RSP = 18;

    //data monitoring
    final int AII_RSP = 19;
    final int RAV_RSP = 20;
    final int HSI_RSP = 21;
    final int RHV_RSP = 22;
    final int VAI_RSP = 23;
    final int HAD_RSP = 24;
    final int VHI_RSP = 25;
    final int HHD_RSP = 26;

    final int SUCCESS = 1;
    final int FAIL = 0;

    private int responseCode = 0;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    public int returnResponseCode()
    {
        return responseCode;
    }

    // 데이터를 보내는 부분 서버로
    @Override
    protected String doInBackground(String... params) {

        String JsonResponse = null;
        String JsonDATA = params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://teama-iot.calit2.net/"+params[1]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
//response data
            Log.i("teamA",JsonResponse);
               /* try {
//send to post execute
                    return JsonResponse;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;*/

            return JsonResponse;

        } catch (IOException e) {
            Log.i("teamA",e.toString());
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("teamA", "Error closing stream", e);
                }
            }
        }
        return null;
    }

    // 서버로부터 데이터를 받는 부분
    @Override
    protected void onPostExecute(String s) {
        //초기화도 같이 할것
        JSONObject obj = null;
        int responseHeader = 0;
        //int responseCode = 0;
        String Email="";
        String Name=""; ///////////////////////////////////////////////////////////////////////////////////////////////////////확인하
        String Tel = "";
        String Birth = "";

        try
        {
            obj = new JSONObject(s);
            responseHeader = obj.getInt("responseHeader");
        }
        catch (JSONException e) {
            Log.i("teamA", "JSONError : " + e.toString());
        }

        //여기서 부터 작업 하기
        if(responseHeader == 4)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA1",responseHeader + " " + responseCode);



        }
        if(responseHeader == SGU_RSP)
        {
            //추가적으로 더 받아야 할 부부 받기
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","SGU_RSP ::  " + responseHeader + " " + responseCode);



        }
        else if(responseHeader == UVC_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","UVC_RSP ::  " + responseHeader + " " + responseCode);



        }
        /*else if(responseHeader == FPU_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","FPU_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }

        else if(responseHeader == NPU_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","NPU_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }*/


       /* else if(responseHeader == MWD_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }


            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }       */                                                                                                // 완료
        else if(responseHeader == IPC_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
                Email = obj.getString("Email");
                Name = obj.getString("Name");
                Tel = obj.getString("Tel");
                Birth = obj.getString("Birth");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","IPC_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        /*else if(responseHeader == MIC_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","MIC_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }                                                                                                            //////////// 일단 여기까지 끝
*/
        //sensor management
        else if(responseHeader == SMR_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","SMR_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == SAS_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","SAS_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == SDD_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","SDD_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == SLV_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","SLV_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }


        //data management
        else if(responseHeader == SII_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","SII_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == RAD_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","RAD_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == HII_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","HII_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == RHD_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","RHD_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }


        // data monitoring
        else if(responseHeader == AII_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","AII_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == RAV_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","RAV_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == HSI_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","HSI_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == RHV_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","RHV_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == VAI_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","VAI_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == HAD_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","HAD_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == VHI_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","VHI_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
        else if(responseHeader == HHD_RSP)
        {
            try
            {
                //받아야 할 값을을 받는거
                responseCode = obj.getInt("responseCode");
            }
            catch (JSONException e)
            {
                Log.i("teamA", "JSONError : " + e.toString());
            }
            Log.i("teamA","HHD_RSP ::  " + responseHeader + " " + responseCode);

            // 처리
            if(responseHeader == 1)
            {
                //1은 성공
            }
            else
            {
                //실패
            }

        }
    }



}