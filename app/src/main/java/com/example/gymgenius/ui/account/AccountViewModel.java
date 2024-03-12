package com.example.gymgenius.ui.account;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.gymgenius.data.repository.AccountRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccountViewModel extends ViewModel {

    private final AccountRepository accountRepository;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    public AccountViewModel() {
        accountRepository = new AccountRepository();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void saveUserData(String name, String email) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            AccountView userModel = new AccountView(name, email);

            String uid = currentUser.getUid();

            // Crear la nueva colección y agregar el UID como documento
            Map<String, Object> userData = new HashMap<>();
            userData.put("uid", uid);

            mFirestore.collection(userId)
                    .document(uid)
                    .set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Éxito al guardar los datos
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar el error
                        }
                    });
        } else {
            // El usuario no está autenticado
        }

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
