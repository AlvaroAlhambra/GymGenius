package com.example.gymgenius.ui.training.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gymgenius.R;

import java.util.Locale;

public class SelectedDateFragment extends DialogFragment {

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";
    private static final String ARG_DAY = "day";

    private int year;
    private int month;
    private int day;

    public static SelectedDateFragment newInstance(int year, int month, int day) {
        SelectedDateFragment fragment = new SelectedDateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        args.putInt(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_YEAR);
            month = getArguments().getInt(ARG_MONTH);
            day = getArguments().getInt(ARG_DAY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selected_date, container, false);

        // Obtener la referencia al botón
        Button saveDateButton = root.findViewById(R.id.saveDateButton);

        // Agregar un listener de clic al botón
        saveDateButton.setOnClickListener(v -> {
            // Llamar al método de interfaz para comunicarse con el fragmento padre
            if (getActivity() instanceof OnSaveDateClickListener) {
                ((OnSaveDateClickListener) getActivity()).onSaveDateClicked(year, month, day);
            }
        });

        return root;
    }

    // Interfaz para comunicarse con el fragmento padre cuando se hace clic en el botón de guardar fecha
    public interface OnSaveDateClickListener {
        void onSaveDateClicked(int year, int month, int day);
    }
}

