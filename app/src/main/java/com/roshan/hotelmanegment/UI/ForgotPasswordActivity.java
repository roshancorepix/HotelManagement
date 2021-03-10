package com.roshan.hotelmanegment.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.installations.Utils;
import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.Utils.StatusBarUtils;
import com.roshan.hotelmanegment.Utils.Util;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button getPassButton;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.changeStatusBarColor(this, getWindow(), R.color.colorPrimary);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.et_email);
        getPassButton = findViewById(R.id.btn_get_pass);
        closeButton = findViewById(R.id.close_button);

        getPassButton.setOnClickListener(v -> checkValidation());
        closeButton.setOnClickListener(v -> finish());
    }

    private void checkValidation() {
        if (!CommonFunction.isNetworkConnected(this)) {
            CommonFunction.showToastMessage(this, Util.NO_INTERNET);
        } else {
            getPassword();
        }
    }

    private void getPassword() {
        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email should not empty");
            emailEditText.requestFocus();
        } else if (!CommonFunction.isValidEmail(email)) {
            emailEditText.setError("Invalid email address please check");
            emailEditText.requestFocus();
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                CommonFunction.showToastMessage(ForgotPasswordActivity.this,
                                        "Link has been send. Please check your email.");
                                finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            CommonFunction.showToastMessage(ForgotPasswordActivity.this, e.getLocalizedMessage());
                        }
                    });
        }
    }
}