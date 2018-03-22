package nf.co.myipl.myipl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class PredictionAdapter extends ArrayAdapter<String> {

    private final Context ctx;
    private final ArrayList<String> username;
    private final ArrayList<String> umatch1;
    private final ArrayList<String> umatch2;

    PredictionAdapter(Context ctx, ArrayList<String> username, ArrayList<String> umatch1, ArrayList<String> umatch2) {
        super(ctx, R.layout.adapter_prediction, username);
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
            rootView = LayoutInflater.from(ctx).inflate(R.layout.adapter_prediction, parent, false);

        TextView name = rootView.findViewById(R.id.rank);
        TextView match1 = rootView.findViewById(R.id.username);
        TextView match2 = rootView.findViewById(R.id.points);

        name.setText(username.get(position));
        match1.setText(umatch1.get(position));
        match2.setText(umatch2.get(position));

        return rootView;
    }
}