package com.roshan.hotelmanegment.UI.UiFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.ForgotPasswordActivity;
import com.roshan.hotelmanegment.UI.MainActivity;
import com.roshan.hotelmanegment.Utils.Util;


public class LogInFragment extends Fragment implements View.OnClickListener {

    private TextView passwordToggle, forgetPassButton;
    private EditText passwordEditText, emailEditText;
    private Button loginButton;
    private boolean isPasswordHide = true;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgressbar;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        bindID(view);
        mAuth = FirebaseAuth.getInstance();
        // password Toggle
        passwordToggle.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgetPassButton.setOnClickListener(this);

        return view;
    }

    private void bindID(View view) {
        passwordToggle = view.findViewById(R.id.tv_pass_toggle);
        passwordEditText = view.findViewById(R.id.et_pass);
        emailEditText = view.findViewById(R.id.et_email);
        loginButton = view.findViewById(R.id.btn_login);
        loginProgressbar = view.findViewById(R.id.login_progressbar);
        forgetPassButton = view.findViewById(R.id.tv_forgot_pass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pass_toggle:
                if (!isPasswordHide) {
                    isPasswordHide = true;
                    passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                    passwordEditText.setSelection(passwordEditText.length());
                    passwordToggle.setText("SHOW");

                } else {
                    isPasswordHide = false;
                    passwordEditText.setTransformationMethod(null);
                    passwordEditText.setSelection(passwordEditText.length());
                    passwordToggle.setText("HIDE");
                }
                break;

            case R.id.btn_login:
                if (!CommonFunction.isNetworkConnected(getActivity())) {
                    CommonFunction.showToastMessage(getActivity(), Util.NO_INTERNET);
                } else {
                    validation();
                }
                break;

            case R.id.tv_forgot_pass:
                startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
                break;
            default:
                break;
        }
    }

    private void validation() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email should not empty");
            emailEditText.requestFocus();
        } else if (!CommonFunction.isValidEmail(email)) {
            emailEditText.setError("Invalid email address please check");
            emailEditText.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password should not empty");
            passwordEditText.requestFocus();
        } else {
            loginProgressbar.setVisibility(View.VISIBLE);
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loginProgressbar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }else {
                            loginProgressbar.setVisibility(View.GONE);
                            CommonFunction.showToastMessage(getActivity(), "Login failed");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginProgressbar.setVisibility(View.GONE);
                        CommonFunction.showToastMessage(getActivity(), e.getLocalizedMessage());
                    }
                });
    }
}