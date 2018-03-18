package nf.co.myipl.myipl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Register extends AppCompatActivity {

    static ProgressBar progressBar;
    EditText e_name, e_password, e_contact, e_username;
    String name, password, contact, username;
    CheckBox showPassword;
    //   static TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e_name = findViewById(R.id.name);
        e_password = findViewById(R.id.password);
        e_contact = findViewById(R.id.contact);
        e_username = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar);
        showPassword = findViewById(R.id.showPassword);
        e_name.setText(null);
        e_password.setText(null);
        e_contact.setText(null);
        e_username.setText(null);
        // test = findViewById(R.id.test);
    }
    public void onClickShowPassword(View view) {
        if (!showPassword.isChecked()) {
            e_password.setTransformationMethod(new PasswordTransformationMethod());
        } else {
            e_password.setTransformationMethod(null);
        }
    }

    public void reguser(View view) {


        name = e_name.getText().toString().toUpperCase();
        password = e_password.getText().toString();
        contact = e_contact.getText().toString().toUpperCase();
        username = e_username.getText().toString();

        String method = "register";

        if (name.isEmpty() || !name.matches("[a-z A-Z]+")) {
            e_name.setError("Name cannot be empty");
            return;
        }
        if (name.length() > 99) {
            e_name.setError("Name too long");
            return;
        }
        if (username.length() < 3 || username.length() > 12) {
            e_username.setError("Username must be 3 to 12 character long");
            return;
        }
        if (!username.matches("[0-9a-zA-z_]+")) {
            e_username.setError("Only alphabets, numbers and _ are allowed ");
            return;
        }
        if (password.length() < 6) {
            e_password.setError("Password must be at least 6 characters long");
            return;
        }
        if (password.length() > 30) {
            e_password.setError("Password too long");
            return;
        }
        if (!contact.matches("[0-9]+") || contact.length() > 10 || contact.length() < 8) {
            e_contact.setError("Contact must be valid 8 to 10 digit");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, name, username, password, contact);
    }

}

