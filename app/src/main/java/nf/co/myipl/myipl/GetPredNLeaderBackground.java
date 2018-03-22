package nf.co.myipl.myipl;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class GetPredNLeaderBackground extends AsyncTask<String,Void,Object[]> {
    private Context ctx;
    String method;

    GetPredNLeaderBackground(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPostExecute(Object[] temp) {

        if(method.equals("PREDICTION")) {
            Log.d("GPLB postEXE ", method);
            ArrayList<String> username = (ArrayList<String>) temp[0];
            ArrayList<String> match1 = (ArrayList<String>) temp[1];
            ArrayList<String> match2 = (ArrayList<String>) temp[2];
            PredictionAdapter adapter = new PredictionAdapter(ctx, username, match1, match2);
            Predictions.listView.setAdapter(adapter);
        }
        else if(method.equals("LEADERBOARD"))
        {
            Log.d("GPLB postEXE ", method);
            ArrayList<String> rank = (ArrayList<String>) temp[0];
            ArrayList<String> username = (ArrayList<String>) temp[1];
            ArrayList<String> points = (ArrayList<String>) temp[2];
            LeaderboardAdapter adapter = new LeaderboardAdapter(ctx, rank, username, points);
            Leaderboard.leaderboard.setAdapter(adapter);
        }
    }

    protected Object[] doInBackground(String... params) {
        String line = "";
        HttpURLConnection httpURLConnection;
        String prediction_url = "http://bd2841dc.ngrok.io/myipl/player/predictions";
        String leaderboard_url = "http://bd2841dc.ngrok.io/myipl/player/leaderboard";
        String response_api = "";
        String data = "";
        method = params[0];
        Log.d("GPLB method? ", method);
        if (method.equals("PREDICTION")) {
            Log.d("GPLB doInB ", method);
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
                        + "{\"userId\":\"ashutoxh\"," + "\"match1\":null," + "\"match2\":\"RCB\"},"
                        + "{\"userId\":\"skawes\"," + "\"match1\":MI," + "\"match2\":\"RCB\"},"
                        + "{\"userId\":\"saurabh727\"," + "\"match1\":KKR," + "\"match2\":null},"
                        + "{\"userId\":\"jigar181\"," + "\"match1\":MI," + "\"match2\":\"RCB\"}"
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
                return new Object[]{name, match1, match2};
            }
        }
        else if (method.equals("LEADERBOARD"))
        {
            Log.d("GPLB doInB ", method);
            ArrayList<String> rank = new ArrayList<>();
            ArrayList<String> username = new ArrayList<>();
            ArrayList<String> points = new ArrayList<>();
            int count = 0;

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
                data = "{\"action\":\"success\"," + "\"leaderboard\":" + "["
                        + "{\"userId\":\"ashutoxh\"," + "\"points\":100.25},"
                        + "{\"userId\":\"skawes\"," + "\"points\":100},"
                        + "{\"userId\":\"saurabh727\"," + "\"points\":100},"
                        + "{\"userId\":\"jigar181\"," + "\"points\":100}"
                        + "]" + "}";
                Log.d("ADAPTER DATA ", data);

                JSONObject userData = new JSONObject(data);
                JSONArray jsonArray = userData.getJSONArray("leaderboard");
                Log.d("JSONARRAYYY ", "" + jsonArray);
                response_api = userData.getString("action");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject arrayData = jsonArray.getJSONObject(i);
                    username.add(arrayData.getString("userId"));
                    points.add(arrayData.getString("points"));
                    rank.add("" + (++count));
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
                return new Object[]{rank, username, points};
            }

        }
        return null;
    }

}
