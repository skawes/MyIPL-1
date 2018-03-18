package nf.co.myipl.myipl;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PostLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static Button team1a, team1b, team2a, team2b, leaderboardbtn, predictionbtn;
    static TextView VS1, VS2;
    static Toast exitToast, networkToast;
    static ProgressBar progressBar;
    String userName = "", userID = "";
    private long back_pressed;

    public static boolean connection(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() &&
                connectivityManager.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postlogin);
        Bundle bundle;
        Toast.makeText(this, "Welcome " + MainActivity.sharedPreferences.getString("username", null), Toast.LENGTH_SHORT).show();
        bundle = getIntent().getExtras();
        userName = bundle.getString("username");
        userID = bundle.getString("userId");
        Log.d("POSTLOGIN ", userName + " " + userID);
        MainActivity.sharedPreferences.edit().putString("status", "Alive").commit();
        Log.d("POSTLOGIN STATUS ", "status: " + MainActivity.sharedPreferences.getString("status", null));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView loggedInUserName = header.findViewById(R.id.nav_header_name);
        TextView getPoints = header.findViewById(R.id.nav_header_points);
        loggedInUserName.setText(userName);
        getPoints.setText(userID);

//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//       int screenHeight = displayMetrics.heightPixels;
//        int screenWidth = displayMetrics.widthPixels;

        team1a = findViewById(R.id.team1a);
        team1b = findViewById(R.id.team1b);
        team2a = findViewById(R.id.team2a);
        team2b = findViewById(R.id.team2b);
        leaderboardbtn = findViewById(R.id.btn_leaderboard);
        predictionbtn = findViewById(R.id.btn_prediction);
        VS1 = findViewById(R.id.VS1);
        VS2 = findViewById(R.id.VS2);
        progressBar = findViewById(R.id.progressBar);

//        team1a.setWidth(screenWidth / 3);
//        team1b.setWidth(screenWidth / 3);
//        team2a.setWidth(screenWidth / 3);
//        team2b.setWidth(screenWidth / 3);
//        leaderboardbtn.setWidth(screenWidth / 3);
//        predictionbtn.setWidth(screenWidth / 3);
//
//        team1a.setHeight(screenHeight / 7);
//        team1b.setHeight(screenHeight / 7);
//        team2a.setHeight(screenHeight / 7);
//        team2b.setHeight(screenHeight / 7);

        String[] to = {"MI", "RCB", "KKR", "CSK"};
        setMatchIcon(to);
    }

    public void givePrediction2a(View view) {
        if (connection(this)) {
            progressBar.setVisibility(View.VISIBLE);
            team2a.setClickable(false);
            team2b.setClickable(false);
            team1a.setClickable(false);
            team1b.setClickable(false);
            team2b.postDelayed(new Runnable() {
                @Override
                public void run() {
                    team2a.setClickable(true);
                    team2b.setClickable(true);
                    team1b.setClickable(true);
                    team1a.setClickable(true);
                }
            }, 1000);

            if (BackgroundTask.backgroundToast != null)
                BackgroundTask.backgroundToast.cancel();
            fetchData process = new fetchData(this);
            process.execute(userID, "match1", team2a.getText().toString());
        } else {
            networkToast = Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT);
            networkToast.show();
        }
    }

    public void givePrediction2b(View view) {
        if (connection(this)) {
            progressBar.setVisibility(View.VISIBLE);
            team2a.setClickable(false);
            team2b.setClickable(false);
            team1a.setClickable(false);
            team1b.setClickable(false);
            team2b.postDelayed(new Runnable() {
                @Override
                public void run() {
                    team2a.setClickable(true);
                    team2b.setClickable(true);
                    team1b.setClickable(true);
                    team1a.setClickable(true);
                }
            }, 1000);

            if (BackgroundTask.backgroundToast != null)
                BackgroundTask.backgroundToast.cancel();
            fetchData process = new fetchData(this);
            process.execute(userID, "match1", team2b.getText().toString());
        } else {
            networkToast = Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT);
            networkToast.show();
        }
    }

    public void givePrediction1a(View view) {
        if (connection(this)) {
            progressBar.setVisibility(View.VISIBLE);
            team1a.setClickable(false);
            team1b.setClickable(false);
            team2a.setClickable(false);
            team2b.setClickable(false);

            team1a.postDelayed(new Runnable() {
                @Override
                public void run() {
                    team2a.setClickable(true);
                    team2b.setClickable(true);
                    team1b.setClickable(true);
                    team1a.setClickable(true);
                }
            }, 1000);

            if (BackgroundTask.backgroundToast != null)
                BackgroundTask.backgroundToast.cancel();
            fetchData process = new fetchData(this);
            process.execute(userID, "match2", team1a.getText().toString());
        } else {
            networkToast = Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT);
            networkToast.show();
        }
    }

    public void givePrediction1b(View view) {
        if (connection(this)) {
            progressBar.setVisibility(View.VISIBLE);
            team1a.setClickable(false);
            team1b.setClickable(false);
            team2a.setClickable(false);
            team2b.setClickable(false);

            team1b.postDelayed(new Runnable() {
                @Override
                public void run() {
                    team2a.setClickable(true);
                    team2b.setClickable(true);
                    team1b.setClickable(true);
                    team1a.setClickable(true);
                }
            }, 1000);

            if (BackgroundTask.backgroundToast != null)
                BackgroundTask.backgroundToast.cancel();
            fetchData process = new fetchData(this);
            process.execute(userID, "match2", team1b.getText().toString());
        } else {
            networkToast = Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT);
            networkToast.show();
        }
    }


    @Override
    protected void onDestroy() {
        if (BackgroundTask.backgroundToast != null)
            BackgroundTask.backgroundToast.cancel();
        if (networkToast != null)
            networkToast.cancel();
        if (fetchData.predictionToast != null)
            fetchData.predictionToast.cancel();
        if (fetchData.oopsToast != null)
            fetchData.oopsToast.cancel();
        if (fetchData.alreadyToast != null)
            fetchData.alreadyToast.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.finishAffinity();
                exitToast.cancel();
            }
        } else {
            exitToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            exitToast.show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Log.d("LOGOUTTTT ", "Username " + userName + " UserId " + userID);
            MainActivity.sharedPreferences.edit().putString("status", "LOGOUT").commit();
            Log.d("LOGOUTTT SHARED ", "status: " + MainActivity.sharedPreferences.getString("status", null));
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("status", "LOGOUT");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoleaderboard(View view) {

        Intent intent = new Intent(this, Leaderboard.class);
        startActivity(intent);
    }

    public void gotoprediction(View view) {
        Intent intent = new Intent(this, Predictions.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_share) {
            String shareBody = "https://blahblahblah";

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_rules) {
            Intent intent = new Intent(this, Rules.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_abtus) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setMatchIcon(String team[]) {
        Button[] btn = {team2a, team2b, team1a, team1b};
        for (int i = 0; i < team.length; i++) {
            if (team[i] != null) {
                switch (team[i]) {
                    case "MI": {
                        btn[i].setBackgroundResource(R.mipmap.mi);
                        btn[i].setText("MI");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "RCB": {
                        btn[i].setBackgroundResource(R.mipmap.rcb);
                        btn[i].setText("RCB");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "KKR": {
                        btn[i].setBackgroundResource(R.mipmap.kkr);
                        btn[i].setText("KKR");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "CSK": {
                        btn[i].setBackgroundResource(R.mipmap.csk);
                        btn[i].setText("CSK");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "RR": {
                        btn[i].setBackgroundResource(R.mipmap.rr);
                        btn[i].setText("RR");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "SRH": {
                        btn[i].setBackgroundResource(R.mipmap.srh);
                        btn[i].setText("SRH");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "DD": {
                        btn[i].setBackgroundResource(R.mipmap.dd);
                        btn[i].setText("DD");
                        btn[i].setTextSize(0);
                        break;
                    }
                    case "KXIP": {
                        btn[i].setBackgroundResource(R.mipmap.kxip);
                        btn[i].setText("KXIP");
                        btn[i].setTextSize(0);
                        break;
                    }
                }
            } else {
                team1a.setVisibility(View.INVISIBLE);
                team1b.setVisibility(View.INVISIBLE);
                VS1.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }
}
