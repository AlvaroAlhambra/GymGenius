package com.example.gymgenius.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.gymgenius.MainActivity;

import com.example.gymgenius.databinding.ActivityLoginBinding;


public class LoginView extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.loginBtn;
        final Button registerButton = binding.registerBtn;

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.createUserWithEmailAndPassword(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

                usernameEditText.getText().clear();
                passwordEditText.getText().clear();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.signInWithEmailAndPassword(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

                usernameEditText.getText().clear();
                passwordEditText.getText().clear();
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {
        loginViewModel.getUserEmail().observe(this, email -> {
            if (email != null) {
                showHome();
            }
        });

        loginViewModel.getLoginSuccess().observe(this, loginSuccess -> {
            if (!loginSuccess) {
                showAlert(loginViewModel.getErrorMessage().getValue());
            }
        });
    }

    private void showHome() {
        Intent intentMain = new Intent(LoginView.this, MainActivity.class);
        startActivity(intentMain);
    }

    private void showAlert(String errorMessage) {
        // Manejo de errores de Firebase Auth
        Log.d("Authentication", "Authentication failed: " + errorMessage);
        Toast.makeText(LoginView.this, "Authentication failed", Toast.LENGTH_SHORT).show();
    }
}
