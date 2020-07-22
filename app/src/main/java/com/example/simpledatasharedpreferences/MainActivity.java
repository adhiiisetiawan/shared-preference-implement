package com.example.simpledatasharedpreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvName, tvAge, tvEmail, tvPhone, tvIsLoveMU;
    private Button btnSave;
    private UserPreferences userPreferences;

    private boolean isPreferenceEmpty = false;
    private UserModel userModel;

    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvAge = findViewById(R.id.tv_age);
        tvPhone = findViewById(R.id.tv_phone_number);
        tvIsLoveMU = findViewById(R.id.tv_isloveMU);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("User Preference");
        }

        userPreferences = new UserPreferences(this);

        //showexisting
        showExistingPreference();
    }

    private void showExistingPreference(){
        userModel = userPreferences.getUser();
        populateView(userModel);
        checkForm(userModel);
    }

    private void populateView(UserModel userModel){
        tvName.setText(userModel.getName().isEmpty() ? "Not Found" : userModel.getName());
        tvAge.setText(String.valueOf(userModel.getAge()).isEmpty() ? "Not Found" : String.valueOf(userModel.getAge()));
        tvIsLoveMU.setText(userModel.isLove() ? "Yes" : "No");
        tvEmail.setText(userModel.getEmail().isEmpty() ? "Not Found" : userModel.getEmail());
        tvPhone.setText(userModel.getPhoneNumber().isEmpty() ? "Not Found" : userModel.getPhoneNumber());
    }

    private void checkForm(UserModel userModel){
        if (!userModel.getName().isEmpty()) {
            btnSave.setText(getString(R.string.change));
            isPreferenceEmpty = false;
        } else {
            btnSave.setText(getString(R.string.save));
            isPreferenceEmpty = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            Intent intent = new Intent(MainActivity.this, FormUserPreferenceActivity.class);
            if (isPreferenceEmpty) {
                intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_ADD);
                intent.putExtra("USER", userModel);
            } else {
                intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_EDIT);
                intent.putExtra("USER", userModel);
            }
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == FormUserPreferenceActivity.RESULT_CODE) {
                userModel = data.getParcelableExtra(FormUserPreferenceActivity.EXTRA_RESULT);
                populateView(userModel);
                checkForm(userModel);
            }
        }
    }
}