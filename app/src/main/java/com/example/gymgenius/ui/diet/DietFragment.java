package com.example.gymgenius.ui.diet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymgenius.databinding.FragmentDietBinding;

public class DietFragment extends Fragment {

    private FragmentDietBinding binding;
    private SharedPreferences preferences; // Inicializa esto en onCreateView

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DietViewModel dietViewModel =
                new ViewModelProvider(this).get(DietViewModel.class);

        binding = FragmentDietBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        dietViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Inicializa preferences en onCreateView
        preferences = requireActivity().getSharedPreferences("MyPrefs", requireActivity().MODE_PRIVATE);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Actualizar el valor de lastScreen antes de cambiar de fragmento
        String currentFragmentTag = this.getClass().getSimpleName();
        preferences.edit().putString("last_screen", currentFragmentTag).apply();
    }
}
