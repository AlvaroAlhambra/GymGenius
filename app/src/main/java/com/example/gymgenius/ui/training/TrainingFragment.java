package com.example.gymgenius.ui.training;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymgenius.databinding.FragmentTrainingBinding;

public class TrainingFragment extends Fragment {

    private FragmentTrainingBinding binding;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TrainingViewModel trainingViewModel =
                new ViewModelProvider(this).get(TrainingViewModel.class);

        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        trainingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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
