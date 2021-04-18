package com.roshan.hotelmanegment.UI.UiFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.SharedPreference.Preference;
import com.roshan.hotelmanegment.UI.MainActivity;
import com.roshan.hotelmanegment.Utils.Util;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private EditText passwordEditText, usernameEditText, emailEditText, mobileNumberEditText;
    private TextView passwordToggle;
    private Button signInButton;
    private boolean isPasswordHide = true;
    private FirebaseAuth mAuth;
    private ProgressBar sigInProgressbar;
    private FirebaseUser currentUser;
    DatabaseReference reference;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        bindID(view);

        // Initialise variables
        FirebaseApp.initializeApp(getActivity());
        mAuth = FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("app_user");

        //init SharedPreference
        Preference.init(getActivity());

        // click
        passwordToggle.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        Log.e(TAG, "currentUser: " + currentUser);
        if (currentUser != null) {
            startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    private void bindID(View view) {
        passwordToggle = view.findViewById(R.id.tv_pass_toggle);
        passwordEditText = view.findViewById(R.id.et_pass);
        usernameEditText = view.findViewById(R.id.et_username);
        emailEditText = view.findViewById(R.id.et_email);
        mobileNumberEditText = view.findViewById(R.id.et_mobile_no);
        signInButton = view.findViewById(R.id.btn_signIn);
        sigInProgressbar = view.findViewById(R.id.progressbar);
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
            case R.id.btn_signIn:
                if (!CommonFunction.isNetworkConnected(getActivity())) {
                    CommonFunction.showToastMessage(getActivity(), Util.NO_INTERNET);
                } else {
                    checkValidate();
                }
                break;

            default:
                break;
        }
    }

    private void checkValidate() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String mobile = mobileNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username should not empty");
            usernameEditText.requestFocus();
        } else if (username.length() < Util.USERNAME_LENGTH) {
            usernameEditText.setError("Username should be more then 6 character");
            usernameEditText.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email should not empty");
            emailEditText.requestFocus();
        } else if (!CommonFunction.isValidEmail(email)) {
            emailEditText.setError("Invalid email address please check");
            emailEditText.requestFocus();
        } else if (TextUtils.isEmpty(mobile)) {
            mobileNumberEditText.setError("Mobile number should not empty");
            mobileNumberEditText.requestFocus();
        } else if (mobile.length() != Util.MOBILE_NUM_LENGTH) {
            mobileNumberEditText.setError("Mobile number must be 10 digits");
            mobileNumberEditText.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password should not empty");
            passwordEditText.requestFocus();
        } else if (CommonFunction.validate(password)) {
            passwordEditText.setError("Password must contains 1 digits, 1 uppercase character, and 1 lowercase character and 1 spacial symbol, length should be 6-20 characters");
            passwordEditText.requestFocus();
        } else {
            sigInProgressbar.setVisibility(View.VISIBLE);
            signInButton.setEnabled(false);
            signUpUser(username, email, mobile,password);
        }
    }

    private void signUpUser(String username,String email, String mobile,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sigInProgressbar.setVisibility(View.GONE);
                            signInButton.setEnabled(true);
                            CommonFunction.showToastMessage(getActivity(), "User signIn Success");
                            String uID = mAuth.getCurrentUser().getUid();
                            storeUserDetail(uID, username, email, mobile);
                            storeUserDetailInSP(username, email, mobile);
                            startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"Error: "+e.getLocalizedMessage());
                    }
                });
    }

    private void storeUserDetailInSP(String username, String email, String mobile) {
        Preference.setUserName(username);
        Preference.setUserEmail(email);
        Preference.setUserMobileNo(mobile);
    }

    private void storeUserDetail(String uID, String username, String email, String mobile) {
        User user = new User(uID,username, email, mobile);
        reference.child(uID).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.e(TAG,"User Added successfully");
            }else {
                Log.e(TAG,"Some error"+task.getException().getMessage());
            }

        })
        .addOnFailureListener(e -> {
            Log.e(TAG,"Error: "+e.getMessage());
        });
    }
}