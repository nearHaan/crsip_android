package com.duk.crsipandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.duk.crsipandroid.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static TextInputEditText et_name, et_username, et_email, et_phone_number, et_password, et_confirm_password;
    private static TextInputLayout et_layout_name, et_layout_username, et_layout_email, et_layout_phone_number, et_layout_password, et_layout_confirm_password;
    private static MaterialButton btn_cancel, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        if (v.getId() == R.id.btn_register){
            if (validate()){
                finish();
            }
        } else if (v.getId() == R.id.btn_register_cancel) {
            //
        }
    }

    private boolean validate(){
        String name = et_name.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String phone_number = et_phone_number.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm_password = et_confirm_password.getText().toString().trim();
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
        }
        return true;
    }
}