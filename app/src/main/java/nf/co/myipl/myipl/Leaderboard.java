package nf.co.myipl.myipl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class Leaderboard extends AppCompatActivity {

    TextView myPoints;
    private Double getPoints = 111.11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        setSupportActionBar(toolbar);

        myPoints = findViewById(R.id.myPoints);
        myPoints.setText("Your Points : " + getPoints);

        ListView leaderboard = findViewById(R.id.leaderboard);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

