package com.example.gymgenius.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymgenius.data.repository.AccountRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountViewModel extends ViewModel {

    private final AccountRepository accountRepository;
    private final LiveData<String> userData;

    public AccountViewModel() {
        accountRepository = new AccountRepository();
        userData = accountRepository.getUserData();
        // Puedes inicializar y cargar datos adicionales según sea necesario
    }

    public LiveData<String> getUserData() {
        return userData;
    }

    public void updateUserData(String newText) {
        accountRepository.updateUserData(newText);
    }

    // Método para obtener el correo electrónico del usuario actual
    public String getUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        } else {
            return null;
        }
    }

}
