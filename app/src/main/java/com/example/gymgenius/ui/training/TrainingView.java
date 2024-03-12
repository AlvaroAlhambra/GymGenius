package com.example.gymgenius.ui.training;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymgenius.databinding.FragmentTrainingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TrainingView extends Fragment {

    private FragmentTrainingBinding binding;
    private SharedPreferences preferences;
    private TrainingViewModel trainingViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);

        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        // Observa el texto en el ViewModel y actualiza la interfaz de usuario
        trainingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Inicializa preferences en onCreateView
        preferences = requireActivity().getSharedPreferences("MyPrefs",
                requireActivity().MODE_PRIVATE);

        // Verificar si es un usuario nuevo y guardar los datos en Firebase
        checkAndSaveUserData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Actualizar el valor de lastScreen antes de cambiar de fragmento
        String currentFragmentTag = this.getClass().getSimpleName();
        preferences.edit().putString("last_screen", currentFragmentTag).apply();
    }

    private void checkAndSaveUserData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Verificar si el usuario ha iniciado sesión
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();

            // Verificar si es un usuario nuevo consultando Firebase Firestore
            DocumentReference userRef = db.collection("USERS").document(userId);
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // El usuario ya existe, no se necesita hacer nada
                    } else {
                        // El usuario es nuevo, guardar los datos en Firebase
                        saveUserData(userId);
                    }
                } else {
                    Log.d("TrainingView", "Error al verificar el usuario: ",
                            task.getException());
                }
            });
        }
    }

    private void saveUserData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crear la jerarquía de datos para el usuario nuevo
        Map<String, Object> exerciseData = new HashMap<>();
        exerciseData.put("Peso", 0);
        exerciseData.put("Repeticiones", 0);

        Map<String, Object> backExercise = new HashMap<>();
        backExercise.put("Back", exerciseData);

        Map<String, Object> chestExercise = new HashMap<>();
        chestExercise.put("Bench Press", exerciseData);

        Map<String, Object> shoulderExercise = new HashMap<>();
        shoulderExercise.put("Militar Press", exerciseData);

        Map<String, Object> bicepsExercise = new HashMap<>();
        bicepsExercise.put("Curl DB", exerciseData);

        Map<String, Object> tricepsExercise = new HashMap<>();
        tricepsExercise.put("Dips", exerciseData);

        Map<String, Object> forearmExercise = new HashMap<>();
        forearmExercise.put("Curl Muñeca Inverso", exerciseData);

        Map<String, Object> abductorExercise = new HashMap<>();
        abductorExercise.put("Abduccion Maquina", exerciseData);

        Map<String, Object> cuadricepsExercise = new HashMap<>();
        cuadricepsExercise.put("Squat", exerciseData);

        Map<String, Object> femoralExercise = new HashMap<>();
        femoralExercise.put("Leg Curl", exerciseData);

        Map<String, Object> glutesExercise = new HashMap<>();
        glutesExercise.put("Hip Thrust", exerciseData);

        Map<String, Object> twinlegsExercise = new HashMap<>();
        twinlegsExercise.put("Prensa Horizontal", exerciseData);

        Map<String, Object> exercises = new HashMap<>();
        exercises.put("Back", backExercise);
        exercises.put("Chest", chestExercise);
        exercises.put("Shoulder", shoulderExercise);
        exercises.put("Biceps", bicepsExercise);
        exercises.put("Triceps", tricepsExercise);
        exercises.put("Forearm", forearmExercise);
        exercises.put("Abductor", abductorExercise);
        exercises.put("Cuadriceps", cuadricepsExercise);
        exercises.put("Femoral", femoralExercise);
        exercises.put("Glutes", glutesExercise);
        exercises.put("Twin Legs", twinlegsExercise);

        Map<String, Object> userDoc = new HashMap<>();
        userDoc.put("Exercises", exercises);

        // Agregar los datos al documento del usuario
        db.collection("USERS").document(userId)
                .set(userDoc)
                .addOnSuccessListener(aVoid -> {
                    // Éxito al crear la jerarquía en Firestore
                    Log.d(TAG, "Jerarquía creada para el usuario: " + userId);
                })
                .addOnFailureListener(e -> {
                    // Manejar el error al crear la jerarquía en Firestore
                    Log.e("errorJerarquia",
                            "Error al crear la jerarquía para el usuario: " + userId, e);
                });
    }
}
