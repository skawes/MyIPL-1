package nf.co.myipl.myipl;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class fetchData extends AsyncTask<String, Void, String> {


    static Toast predictionToast, oopsToast, alreadyToast;
    Context ctx;
    char[] time;

    fetchData(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String response_api = "";
        String KEY = "EY33OBS3S2U9";
        String dateTime;
        String statusTime;
        //char[] date;
        try {
            URL url = new URL("http://api.timezonedb.com/v2/get-time-zone?key=" + KEY + "&format=json&by=zone&zone=Asia/Kolkata");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String data = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
            JSONObject JO = new JSONObject(data);
            dateTime = JO.getString("formatted");
            //dateTime = "2018-02-21 12:00:00";
            // date = dateTime.substring(0, 10).toCharArray();
            time = dateTime.substring(11, 19).toCharArray();
            //Log.d("fetchData TIMEEE ","" + time);
            httpURLConnection.disconnect();
            inputStream.close();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ARRAYYYYYYYYY : ", "Value : " + Integer.parseInt(("" + time[0]) + ("" + time[1])));
        if (Integer.parseInt(("" + time[0]) + ("" + time[1])) >= 14) {
            statusTime = "dead";
        } else {
            statusTime = "alive";
        }
        Log.d("fetchData : ", statusTime);
        String prediction_url = "http://bd2841dc.ngrok.io/myipl/player/prediction";
        if (!statusTime.equals("dead")) {
            HttpURLConnection httpURLConnection;
            JSONObject postData = new JSONObject();
            try {
                Log.d("fetchData PARAMS ", "0 " + params[0] + " 1 " + params[1] + " 2 " + params[2]);
                postData.accumulate("userId", params[0]);
                if (params[1].equals("match1"))
                    postData.accumulate("match1", params[2]);
                else
                    postData.accumulate("match2", params[2]);

                httpURLConnection = (HttpURLConnection) new URL(prediction_url).openConnection();

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json; charset=UTF-8");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));

                bufferedWriter.write(postData.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String data = "";
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data += line;
                }
                Log.v("fetchData : ", "DATA : " + data);
                JSONObject JO = new JSONObject(data);
                Log.v("fetchData : ", "DATA : " + JO.toString());
                response_api = JO.getString("action");
                Log.v("fetchData : ", "Response : " + response_api);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (statusTime.equals("alive")) {
            if (response_api.equals("success")) {
                return "alive";
            } else if (response_api.equals("failure"))
                return "recorded";
            else
                return "Sorry, Our servers are facing some issues.Please try again later";
        } else if (statusTime.equals("dead"))
            return "dead";

        return null;
    }


    @Override
    protected void onPostExecute(String status) {
        time = null;
        PostLoginActivity.progressBar.setVisibility(View.GONE);
        if (status == "recorded") {
            alreadyToast = Toast.makeText(ctx, "Your prediction for this match is already recorded.", Toast.LENGTH_SHORT);
            alreadyToast.show();
        } else if (status == "dead") {
            oopsToast = Toast.makeText(ctx, "Predictions disabled.", Toast.LENGTH_SHORT);
            oopsToast.show();
        } else if (status == "alive") {
            predictionToast = Toast.makeText(ctx, "Your prediction for this match is recorded.", Toast.LENGTH_SHORT);
            predictionToast.show();
        } else
            Toast.makeText(ctx, status, Toast.LENGTH_SHORT).show();
    }

}
