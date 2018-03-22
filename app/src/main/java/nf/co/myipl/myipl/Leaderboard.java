package nf.co.myipl.myipl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class Leaderboard extends AppCompatActivity {

    static ListView leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        setSupportActionBar(toolbar);

        leaderboard = findViewById(R.id.leaderboard);

        GetPredNLeaderBackground getPredictionBackground = new GetPredNLeaderBackground(this);
        getPredictionBackground.execute("LEADERBOARD");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

