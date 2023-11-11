package com.example.gymgenius.ui.login;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.gymgenius.ui.login.LoginView;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getUserEmail() {
        return userEmail;
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> handleAuthenticationResult(task));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signInWithEmailAndPassword(String email, String password) {
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> handleAuthenticationResult(task));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAuthenticationResult(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            loginSuccess.setValue(true);
            // Obtén el correo electrónico y actualiza el LiveData
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            userEmail.setValue(email);
        } else {
            loginSuccess.setValue(false);
            errorMessage.setValue(task.getException().getMessage());
        }
    }
}

