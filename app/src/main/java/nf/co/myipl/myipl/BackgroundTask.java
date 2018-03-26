package nf.co.myipl.myipl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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

public class BackgroundTask extends AsyncTask<String, Void, String> {

    static Toast backgroundToast;
    String loggedInUserName = "", loggedInUserId = "";
    private Context ctx;

    BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }


    public static boolean connection(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() &&
                connectivityManager.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    protected String doInBackground(String... params) {

        String reg_url = "http://bd2841dc.ngrok.io/myipl/player/register";
        String log_url = "http://bd2841dc.ngrok.io/myipl/player/login";
        String response_api = "";
        String method = params[0];
        String name = params[1];
        String username = params[2];
        String password = params[3];
        String contact = params[4];
        String errorMessage = "";

        if (method.equals("register")) {
            HttpURLConnection httpURLConnection;
            if (connection(ctx)) {
                JSONObject postData = new JSONObject();
                try {
                    postData.accumulate("name", name);
                    postData.accumulate("userId", username);
                    postData.accumulate("password", password);
                    postData.accumulate("contactNumber", contact);

                    httpURLConnection = (HttpURLConnection) new URL(reg_url).openConnection();

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
                    Log.v("BG Thread(REGISTER) : ", "DATA : " + data);
                    JSONObject JO = new JSONObject(data);
                    Log.v("BG Thread(REGISTER) : ", "DATA : " + JO.toString());
                    response_api = JO.getString("action");
                    Log.v("BG Thread(REGISTER) : ", "Response : " + response_api);
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

                if (response_api.equals("success")) {
                    loggedInUserName = name;
                    loggedInUserId = username;
                    return "Registration Successful";
                } else if (response_api.equals("failure"))
                    return "Username already exists";
                else
                    return "Registration is now closed.";

            } else
                return "Please check your Internet Connection";
        } else if (method.equals("login")) {
//            HttpURLConnection httpURLConnection;
//            if (connection(ctx)) {
//                JSONObject postData = new JSONObject();
//                try {
//                    postData.accumulate("userId", username);
//                    postData.accumulate("password", password);
//                    Log.d("BG THREAD","User : " +username + " Pass : " +password);
//                    httpURLConnection = (HttpURLConnection) new URL(log_url).openConnection();
//
//                    httpURLConnection.setRequestMethod("POST");
//
//                    httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                    httpURLConnection.setRequestProperty("Accept", "application/json; charset=UTF-8");
//
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    OutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
//
//                    bufferedWriter.write(postData.toString());
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    os.close();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String data = "";
//                    String line = "";
//                    while (line != null) {
//                        line = bufferedReader.readLine();
//                        data += line;
//                    }
//                    Log.v("BG Thread(LOGIN) : ", "DATA : " + data);
//                    JSONObject JO = new JSONObject(data);
//                    Log.v("BG Thread(LOGIN) : ", "DATA : " + JO.toString());
//                    response_api = JO.getString("action");
//                    Log.v("BG Thread(LOGIN) : ", "Response : " + response_api);
//                    errorMessage = JO.getString("errorMessage");
//                    Log.v("BG Thread(LOGIN) : ", "Error Message : " + errorMessage);
//                    name = JO.getString("name");
//                    Log.v("BG Thread(LOGIN) : ", "Name : " + name);
//                    username = JO.getString("userId");
//                    Log.v("BG Thread(LOGIN) : ", "UserId : " + username);
//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (ProtocolException e) {
//                    e.printStackTrace();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (response_api.equals("success")) {
//                    loggedInUserName = name;
//                    loggedInUserId = username;
//                    return "Login Successful";
//                }
//                else if (response_api.equals("failure"))
//                {
//                    if(errorMessage.equals("Incorrect password"))
//                        return "Incorrect password";
//                    else if(errorMessage.equals("Incorrect result size: expected 1, actual 0"))
//                        return "User doesn't exist";
//                    else if(errorMessage.equals("Incorrect result size: expected 1, actual 0"))
//                        return "User doesn't exist";
//                }
//                else
//                    return "Sorry, Our servers are facing some issues.Please try again later";
//            } else
//                return "Please check your Internet Connection";
            Log.d("BG THREAD", "User : " + username + " Pass : " + password);
            if (username.equals("admin") && password.equals("admin")) {
                loggedInUserName = "ASHUTOSH SINGH";
                loggedInUserId = "ashu";
                return "Login Successful";
            } else
                return "Invalid";
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        //      backgroundToast = Toast.makeText(ctx, "Welcome " + loggedInUserName, Toast.LENGTH_SHORT);
        if (MainActivity.progressBar != null)
            MainActivity.progressBar.setVisibility(View.GONE);
        if (Register.progressBar != null)
            Register.progressBar.setVisibility(View.GONE);

        if (result.equals("Login Successful") || result.equals("Registration Successful")) {
            Log.d("BG THREAD", "Result : " + loggedInUserName + " " + loggedInUserId);
            MainActivity.sharedPreferences.edit().putString("username", loggedInUserName).apply();
            MainActivity.sharedPreferences.edit().putString("userId", loggedInUserId).apply();
            Log.d("TEST SHARED STATIC ", " " + MainActivity.sharedPreferences.getString("username", null));
            Intent intent = new Intent(ctx, PostLoginActivity.class);
            intent.putExtra("username", this.loggedInUserName);
            intent.putExtra("userId", this.loggedInUserId);
            ctx.startActivity(intent);
            // backgroundToast.show();

        } else {
            backgroundToast = Toast.makeText(ctx, result, Toast.LENGTH_SHORT);
            backgroundToast.show();
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
