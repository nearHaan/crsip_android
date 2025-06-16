package com.duk.crsipandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.utils.DBHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static TextInputEditText et_name, et_username, et_email, et_phone_number, et_password, et_confirm_password;
    private static TextInputLayout et_layout_name, et_layout_username, et_layout_email, et_layout_phone_number, et_layout_password, et_layout_confirm_password;
    private static MaterialButton btn_cancel, btn_register;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_register);
        dbHandler = new DBHandler(this);

        initViews();
    }

    private void initViews(){
        et_name = findViewById(R.id.et_reg_name);
        et_username = findViewById(R.id.et_reg_username);
        et_email = findViewById(R.id.et_reg_email);
        et_phone_number = findViewById(R.id.et_reg_phone_number);
        et_password = findViewById(R.id.et_reg_password);
        et_confirm_password = findViewById(R.id.et_reg_confirm_password);
        et_layout_name =  findViewById(R.id.et_reg_layout_name);
        et_layout_username = findViewById(R.id.et_reg_layout_username);
        et_layout_email = findViewById(R.id.et_reg_layout_email);
        et_layout_phone_number = findViewById(R.id.et_reg_layout_phone_number);
        et_layout_password = findViewById(R.id.et_reg_layout_password);
        et_layout_confirm_password = findViewById(R.id.et_reg_layout_confirm_password);
        btn_register = findViewById(R.id.btn_register);
        btn_cancel = findViewById(R.id.btn_register_cancel);
        btn_register.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String phone_number = et_phone_number.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm_password = et_confirm_password.getText().toString().trim();
        if (v.getId() == R.id.btn_register){
            if (validate(name, username, email, phone_number, password, confirm_password)){
                dbHandler.addUser(name, username, email, phone_number, password);
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (v.getId() == R.id.btn_register_cancel) {
            finish();
        }
    }

    private boolean validate(String name, String username, String email, String phone_number, String password, String confirm_password){
        String cant_be_empty = "This field cannot be empty";
        boolean flag = false;
        if (name.isEmpty()){
            et_layout_name.setError(cant_be_empty);
            flag = true;
        } else {
            et_layout_name.setErrorEnabled(false);
        }if (username.isEmpty()){
            et_layout_username.setError(cant_be_empty);
            flag = true;
        } else {
            et_layout_username.setErrorEnabled(false);
        }if (email.isEmpty()){
            et_layout_email.setError(cant_be_empty);
            flag = true;
        } else {
            et_layout_email.setErrorEnabled(false);
        }if (phone_number.isEmpty()){
            et_layout_phone_number.setError(cant_be_empty);
            flag = true;
        } else {
            et_layout_phone_number.setErrorEnabled(false);
        }if (password.isEmpty()){
            et_layout_password.setError(cant_be_empty);
            flag = true;
        } else {
            et_layout_password.setErrorEnabled(false);
        }if (confirm_password.isEmpty()){
            et_layout_confirm_password.setError(cant_be_empty);
            flag = true;
        } else {
            et_layout_confirm_password.setErrorEnabled(false);
        }
        if (flag){
            return false;
        } if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")){
            et_email.requestFocus();
            et_layout_email.setError("Enter a valid email address");
            return false;
        } else {
            et_layout_email.setErrorEnabled(false);
        } if (!phone_number.matches("^[6-9]+[\\d]{9}$")){
            et_phone_number.requestFocus();
            et_layout_phone_number.setError("Enter a valid phone number");
            return false;
        } else {
            et_layout_phone_number.setErrorEnabled(false);
        } if (!dbHandler.ifUsernameUnique(username)){
            et_layout_username.setError("Username already exists");
            return false;
        } else {
            et_layout_username.setErrorEnabled(false);
        } if (!dbHandler.ifPhoneUnique(phone_number)){
            et_layout_phone_number.setError("Phone number already exists");
            return false;
        } else {
            et_layout_phone_number.setErrorEnabled(false);
        } if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[#@$._])[A-Za-z\\d#@$._]{8,}$")){
            et_password.setText("");
            et_confirm_password.setText("");
            et_layout_password.setError("The password must be a minimum of 8 characters and contain at least one letter, one digit, and one special character (#, $, @, or _). Only these characters are permitted");
            return false;
        } else {
            et_layout_password.setErrorEnabled(false);
        }
        if (!password.equals(confirm_password)){
            et_password.requestFocus();
            et_password.setText("");
            et_confirm_password.setText("");
            et_layout_confirm_password.setError("Confirm Password does not match");
            return false;
        } else {
            et_layout_confirm_password.setErrorEnabled(false);
        }
        return true;
    }
}