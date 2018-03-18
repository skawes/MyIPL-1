package nf.co.myipl.myipl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

class Adapter extends ArrayAdapter<String> {

    private final Context ctx;
    private final ArrayList<String> username;
    private final ArrayList<String> umatch1;
    private final ArrayList<String> umatch2;

    Adapter(Context ctx, ArrayList<String> username, ArrayList<String> umatch1, ArrayList<String> umatch2) {
        super(ctx, R.layout.activity_adapter, username);
        this.ctx = ctx;
        this.username = username;
        this.umatch1 = umatch1;
        this.umatch2 = umatch2;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View rootView = convertView;

        if (rootView == null)
            rootView = LayoutInflater.from(ctx).inflate(R.layout.activity_adapter, parent, false);

        TextView name = rootView.findViewById(R.id.username);
        TextView match1 = rootView.findViewById(R.id.umatch1);
        TextView match2 = rootView.findViewById(R.id.umatch2);

        name.setText(username.get(position));
        match1.setText(umatch1.get(position));
        match2.setText(umatch2.get(position));

        return rootView;
    }
}