package com.example.gymgenius.ui.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymgenius.R; // Asegúrate de que esta referencia sea correcta según tu proyecto
import com.example.gymgenius.databinding.FragmentAccountBinding;

public class AccountView extends Fragment {

    private FragmentAccountBinding binding;
    private SharedPreferences preferences;
    private AccountViewModel accountViewModel;
    private String name;
    private String email;

    public AccountView() {
    }

    // Constructor con parámetros
    public AccountView(String name, String email) {
        this.name = name;
        this.email = email;
    }
    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        // Actualiza la interfaz de usuario con el nombre de usuario
        textView.setText("Welcome, " + accountViewModel.getUserEmail());

        preferences = requireActivity().getSharedPreferences("MyPrefs", requireActivity().MODE_PRIVATE);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        String currentFragmentTag = this.getClass().getSimpleName();
        preferences.edit().putString("last_screen", currentFragmentTag).apply();
    }
}
