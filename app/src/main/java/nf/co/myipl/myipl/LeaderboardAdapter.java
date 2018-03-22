package nf.co.myipl.myipl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardAdapter extends ArrayAdapter<String> {

    private final Context ctx;
    private final ArrayList<String> rank;
    private final ArrayList<String> uname;
    private final ArrayList<String> upoints;

    LeaderboardAdapter(Context ctx, ArrayList<String> rank, ArrayList<String> uname, ArrayList<String> upoints) {
        super(ctx, R.layout.adapter_prediction, uname);
        this.ctx = ctx;
        this.rank = rank;
        this.uname = uname;
        this.upoints = upoints;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View rootView = convertView;

        if (rootView == null)
            rootView = LayoutInflater.from(ctx).inflate(R.layout.adapter_leaderboard, parent, false);

        TextView rankT = rootView.findViewById(R.id.rank);
        TextView username = rootView.findViewById(R.id.username);
        TextView points = rootView.findViewById(R.id.points);

        rankT.setText(rank.get(position));
        username.setText(uname.get(position));
        points.setText(upoints.get(position));

        return rootView;
    }

}
