package nf.co.myipl.myipl;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class GetPredictionBackground extends AsyncTask<Void,Void,Object[]> {
    private Context ctx;

    GetPredictionBackground(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPostExecute(Object[] temp) {

        ArrayList<String> name = (ArrayList<String>) temp[0];
        ArrayList<String> match1 = (ArrayList<String>) temp[1];
        ArrayList<String> match2 = (ArrayList<String>) temp[2];
        Adapter adapter = new Adapter(ctx, name, match1, match2);

        Predictions.listView.setAdapter(adapter);
    }

    protected Object[] doInBackground(Void... aVoid) {
        String line = "";
        HttpURLConnection httpURLConnection;
            String prediction_url = "http://bd2841dc.ngrok.io/myipl/player/predictions";
            String leaderboard_url = "http://bd2841dc.ngrok.io/myipl/player/leaderboard";
            String response_api = "";
            String data = "";
            ArrayList<String> name = new ArrayList<String>();
            ArrayList<String> match1 = new ArrayList<String>();
            ArrayList<String> match2 = new ArrayList<String>();

            try {

//                httpURLConnection = (HttpURLConnection) new URL(prediction_url).openConnection();
//
//                httpURLConnection.setRequestMethod("GET");
//
//                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                httpURLConnection.setRequestProperty("Accept", "application/json; charset=UTF-8");
//                httpURLConnection.setDoInput(true);
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                while (line != null) {
//                    line = bufferedReader.readLine();
//                    data += line;
//                }
                data = "{\"action\":\"success\"," + "\"predictions\":" + "["
                        + "{\"name\":\"ashutosh\"," + "\"match1\":null," + "\"match2\":\"RCB\"},"
                        + "{\"name\":\"awes\"," + "\"match1\":MI," + "\"match2\":\"RCB\"},"
                        + "{\"name\":\"saurabh_\"," + "\"match1\":KKR," + "\"match2\":null},"
                        + "{\"name\":\"jigar181\"," + "\"match1\":MI," + "\"match2\":\"RCB\"}"
                        + "]" + "}";
                Log.d("ADAPTER DATA ", data);

                JSONObject userData = new JSONObject(data);
                JSONArray jsonArray = userData.getJSONArray("predictions");
                Log.d("JSONARRAYYY ", "" + jsonArray);
                response_api = userData.getString("action");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject arrayData = jsonArray.getJSONObject(i);
                    name.add(arrayData.getString("userId"));

                    if (arrayData.getString("match1").equals("null"))
                        match1.add("--");
                    else
                        match1.add(arrayData.getString("match1"));
                    if (arrayData.getString("match2").equals("null"))
                        match2.add("--");
                    else
                        match2.add(arrayData.getString("match2"));

                }

//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();

            } catch (JSONException e) {
                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
            }
            if (response_api.equals("success")) {
                return new Object[]{name,match1,match2};
            }
            else
                Toast.makeText(ctx,"Sorry, Our servers are facing some issues.Please try again later",Toast.LENGTH_SHORT).show();
        return null;
    }

}
