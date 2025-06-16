package com.duk.crsipandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.utils.DBHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static LinearLayout login_root_layout;
    public static TextInputEditText et_phone_number, et_password;
    public static TextInputLayout et_layout_phone_number ,et_layout_password;
    public static MaterialButton btn_register, btn_forgot_password, btn_login;

    private SharedPreferences sharedPreferences;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this);
        dbHandler.getAllUsers();
        initViews();
//        handleFirebase();
        checkLogin();
    }

    void initViews(){
        et_phone_number = findViewById(R.id.et_phone_number);
        et_password = findViewById(R.id.et_password);
        et_layout_phone_number = findViewById(R.id.et_layout_phone_number);
        et_layout_password = findViewById(R.id.et_layout_password);
        btn_forgot_password = findViewById(R.id.btn_forgot_password);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        login_root_layout = findViewById(R.id.login_root_layout);
        btn_login.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("CRISP", MODE_PRIVATE);
    }

//    void handleFirebase(){
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w("FCM", "Fetching FCM token failed", task.getException());
//                        return;
//                    }
//                    String token = task.getResult();
//                    Log.d("FCM", "Token: " + token);
//                });
//    }

    void checkLogin(){
        String phoneNumber = sharedPreferences.getString("phone_number", "");
        if (!phoneNumber.isEmpty()){
            String password = sharedPreferences.getString("password", null);
            validateCredentials(phoneNumber, password);
        }
    }

    boolean validateCredentials(String phoneNumber, String password){
        if (dbHandler.validateUser(phoneNumber, password)){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
            return true;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("phone_number");
        editor.remove("password");
        editor.apply();
        return false;
    }

    boolean validateTextBox(){
        String ph_text = et_phone_number.getText().toString().trim();
        String pass_text = et_password.getText().toString().trim();
        if(!ph_text.matches("^[6-9]+[0-9]{9}$")) {
            //et_layout_phone_number.setError("Invalid phone number. Try again");
            Snackbar.make(login_root_layout, "Please enter a valid phone number", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (pass_text.isEmpty()) {
            //et_layout_password.setError("Please enter a valid password");
            Snackbar.make(login_root_layout, "Please enter a valid password", Snackbar.LENGTH_SHORT).show();
            return false;
        }
//        else {
//            et_layout_password.setErrorEnabled(false);
//        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            if (validateTextBox()){
                String phoneNumber = et_phone_number.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (validateCredentials(phoneNumber, password)){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phone_number", phoneNumber);
                    editor.putString("password", password);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    et_password.setText("");
                    et_password.requestFocus();
                    Snackbar.make(login_root_layout, "Invalid Credentials. Try again", Snackbar.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}