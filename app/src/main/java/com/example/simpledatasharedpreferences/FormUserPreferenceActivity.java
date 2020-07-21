package com.example.simpledatasharedpreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FormUserPreferenceActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtEmail, edtPhone, edtAge;
    private RadioGroup rgLoveMu;
    private RadioButton rbYes, rbNo;
    private Button btnSave;

    public static final String EXTRA_TYPE_FORM = "extra_type_form";
    public static final String EXTRA_RESULT = "extra_result";
    public static final int RESULT_CODE = 101;

    public static final int TYPE_ADD = 1;
    public static final int TYPE_EDIT = 2;

    private final String FIELD_REQUIRED = "field must be filled";
    private final String FIELD_DIGIT_ONLY = "field must be numeric value";
    private final String FIELD_IS_NOT_VALID = "email invalid";

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user_preference);

        edtName = findViewById(R.id.edt_text_name);
        edtEmail = findViewById(R.id.edt_text_email);
        edtPhone = findViewById(R.id.edt_text_phone);
        edtAge = findViewById(R.id.edt_text_age);
        rgLoveMu = findViewById(R.id.rg_islove);
        rbYes = findViewById(R.id.rb_yes);
        rbNo = findViewById(R.id.rb_no);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        userModel = getIntent().getParcelableExtra("USER");
        int formType = getIntent().getIntExtra(EXTRA_TYPE_FORM, 0);

        String actionBarTitle = "";
        String btnTitle = "";

        switch (formType){
            case TYPE_ADD:
                actionBarTitle = "Add new";
                btnTitle = "Save";
                break;
            case TYPE_EDIT:
                actionBarTitle = "Edit";
                btnTitle = "Update";
                break;
        }

        showPreferenceInForm();

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setText(btnTitle);
    }

    private void showPreferenceInForm() {

            edtName.setText(userModel.getName());
            edtEmail.setText(userModel.getEmail());
            edtAge.setText(userModel.getAge());
            edtPhone.setText(userModel.getPhoneNumber());
            if (userModel.isLove()){
                rbYes.setChecked(true);
            }else {
                rbNo.setChecked(true);
            }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save){
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String age = edtAge.getText().toString().trim();
            String phoneNumber = edtPhone.getText().toString().trim();
            boolean isLoveMu = rgLoveMu.getCheckedRadioButtonId() == R.id.rb_yes;

            if (TextUtils.isEmpty(name)){
                edtName.setError(FIELD_REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(email)){
                edtEmail.setError(FIELD_REQUIRED);
                return;
            }

            if (!isValidEmail(email)){
                edtEmail.setError(FIELD_IS_NOT_VALID);
                return;
            }

            if (TextUtils.isEmpty(age)){
                edtAge.setError(FIELD_REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(phoneNumber)){
                edtPhone.setError(FIELD_REQUIRED);
                return;
            }

            if (!TextUtils.isDigitsOnly(phoneNumber)){
                edtPhone.setError(FIELD_DIGIT_ONLY);
                return;
            }

            saveUser(name, email, age, phoneNumber, isLoveMu);

            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_RESULT, userModel);
            setResult(RESULT_CODE, resultIntent);

            finish();
        }
    }

    private void saveUser(String name, String email, String age, String phoneNumber, boolean isLoveMu){
        UserPreferences userPreferences = new UserPreferences(this);

        userModel.setName(name);
        userModel.setEmail(email);
        userModel.setAge(Integer.parseInt(age));
        userModel.setPhoneNumber(phoneNumber);
        userModel.setLove(isLoveMu);

        userPreferences.setUser(userModel);
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}