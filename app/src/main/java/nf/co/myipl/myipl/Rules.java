package nf.co.myipl.myipl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;

public class Rules extends AppCompatActivity {
    private KeyListener listener;
    private EditText ruleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        setSupportActionBar(toolbar);

        ruleText = findViewById(R.id.ruleText);

        listener = ruleText.getKeyListener(); // Save the default KeyListener!!!
        ruleText.setKeyListener(null); // Disable input
        ruleText.setFocusable(false);
        ruleText.setCursorVisible(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void gotoleaderboard(View view) {
        Intent intent = new Intent(this, Leaderboard.class);
        startActivity(intent);
    }

    public void gotoprediction(View view) {
        Intent intent = new Intent(this, Predictions.class);
        startActivity(intent);
    }

}
