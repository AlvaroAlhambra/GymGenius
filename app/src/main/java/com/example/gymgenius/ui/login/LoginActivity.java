package com.example.gymgenius.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gymgenius.MainActivity;
import com.example.gymgenius.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LoginViewModel loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.loginBtn;
        final Button registerButton = binding.registerBtn;

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Firebase Auth
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    try {
                                        showHome(mAuth.getCurrentUser().getEmail().toString());
                                    }catch (NullPointerException npe){
                                        Log.d("CreateUser", "Null Pointer Exception: "
                                                + Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                }else {
                                    showAlert(task);
                                }
                            }
                        });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Firebase Auth
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                                usernameEditText.getText().toString(),
                                passwordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    try {
                                        showHome(mAuth.getCurrentUser().getEmail().toString());
                                    }catch (NullPointerException npe){
                                        Log.d("AccessUser", "Null Pointer Exception: "
                                                + Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                }else {
                                    showAlert(task);
                                }
                            }
                        });
            }
        });
    }
    private void showHome(String email){
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class)
                .putExtra("enail", email);

        startActivity(intentMain);
    }

    private void showAlert(Task task){
        // Manejo de errores de Firebase Firestore
        Log.d("EmailCheck", "Datos incorrectos: "
                + Objects.requireNonNull(task.getException()).getMessage());
        Toast.makeText(LoginActivity.this,"Datos incorrectos", Toast.LENGTH_SHORT).show();
    }
}