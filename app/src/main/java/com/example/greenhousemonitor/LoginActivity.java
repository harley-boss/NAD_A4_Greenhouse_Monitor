/**
 * Project: NAD A4
 * File: LoginActivity.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: First activity a user is presented with requiring them to login
 */

package com.example.greenhousemonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

/**
 * Class: LoginActivity
 * Extends: AppCompatActivity
 * Implements: View.OnClickListener
 * Descr: Class that is used to handle the login of the user
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 100;
    private FirebaseAuth mAuth;
    private EditText userName;
    private EditText password;
    private Button login;

    /**
     * Method: onCreate
     * @param savedInstanceState
     * Descr: is called upon instantiation of the class
     * Returns: void
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setNotificationBarColor();
        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.edUserName);
        password = findViewById(R.id.edPassword);
        login = findViewById(R.id.btnLogin);
        userName.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        login.setOnClickListener(this);
        login.setEnabled(false);
    }

    /**
     * Method: setNotificationBarColor
     * Descr: Changes the notification bar color to match that of the app
     * Returns: void
     */
    private void setNotificationBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.edit_line));
    }

    /**
     * Watcher used to monitor the text field where a user can enter their credentials
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (userName.getText().length() > 0 && password.getText().length() > 0) {
                login.setEnabled(true);
            } else {
                login.setEnabled(false);
            }
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    };

    /**
     * Method: onClick
     * @param v
     * Descr: Handles the on click event of the submit button in the app
     * Returns: void
     */
    @Override
    public void onClick(View v) {
        mAuth.signInWithEmailAndPassword(userName.getText().toString(), password.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (fbUser == null) {
                            UiHelper.showToast(LoginActivity.this, "Failed to login");
                            return;
                        }
                        fbUser.getIdToken(true)
                            .addOnSuccessListener(
                                new OnSuccessListener<GetTokenResult>() {
                                    @Override
                                    public void onSuccess(GetTokenResult result) {
                                        String idToken = result.getToken();
                                        User user = new User(userName.getText().toString(), password.getText().toString());
                                        user.setToken(idToken);
                                        user.setUserId(fbUser.getUid());
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("result", REQUEST_CODE);
                                        returnIntent.putExtra("user", user);
                                        setResult(Activity.RESULT_OK, returnIntent);
                                        finish();
                                    }});

                    } else {
                        UiHelper.showToast(LoginActivity.this, "Failed to login");
                    }
                }
            });
    }
}
