package com.example.gymgenius.ui.training;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymgenius.R;
import com.example.gymgenius.databinding.FragmentTrainingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TrainingView extends Fragment {

    private SharedPreferences preferences;
    private CalendarView calendarView;
    private TrainingViewModel trainingViewModel;
    private FragmentTrainingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inicializar el ViewModel
        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);

        // Inflar el layout del fragmento y obtener la referencia a la vista raíz
        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener la referencia al TextView del layout
        final TextView textView = binding.textHome;

        // Observar los cambios en el texto del ViewModel y actualizar la interfaz de usuario
        trainingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Inicializar las preferencias compartidas en onCreateView
        preferences = requireActivity().getSharedPreferences("MyPrefs",
                requireActivity().MODE_PRIVATE);

        // Verificar si es un usuario nuevo y guardar los datos en Firebase
        checkAndSaveUserData();

        // Obtener la referencia al CalendarView del layout
        calendarView = root.findViewById(R.id.calendarView);

        // Establecer un listener para el evento de cambio de fecha del CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Formatear la fecha seleccionada
            String selectedDate = String.format(Locale.getDefault(), "%04d/%02d/%02d", year, (month + 1), dayOfMonth);

            // Obtener el ID del usuario actual (asegúrate de tener esta información disponible)
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Guardar la fecha seleccionada en Firebase
            saveDate(userId, selectedDate);
        });

        return root;
    }




    /*public void onSaveDateClicked(String selectedDate) {
        // Aquí puedes agregar la lógica para guardar la fecha en Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("selected_date", selectedDate);

        db.collection("USERS").document(userId)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Log.d("TrainingView", "Fecha guardada en Firebase: " + selectedDate);
                    Toast.makeText(getContext(), "Fecha guardada exitosamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("TrainingView", "Error al guardar la fecha en Firebase", e);
                    Toast.makeText(getContext(), "Error al guardar la fecha en Firebase", Toast.LENGTH_SHORT).show();
                });
    }
*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Actualizar el valor de lastScreen antes de cambiar de fragmento
        preferences = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
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
    private void saveDate(String userId, String selectedDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Verificar si la fecha ya existe en Firebase
        db.collection("USERS").document(userId).collection("Dates").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // La fecha ya existe, no es necesario guardarla nuevamente
                        Log.d(TAG, "La fecha ya existe en Firebase: " + selectedDate);
                    } else {
                        // La fecha no existe, guardarla en Firebase
                        Map<String, Object> data = new HashMap<>();

                        // Agregar o actualizar el subdocumento de la fecha en la colección de fechas del usuario
                        db.collection("USERS").document(userId).collection("Dates")
                                .document(selectedDate) // Utiliza la fecha seleccionada como el nombre del documento
                                .set(data)
                                .addOnSuccessListener(aVoid -> {
                                    // Éxito al guardar los datos en Firestore
                                    Log.d(TAG, "Fecha guardada en Firebase: " + selectedDate);
                                })
                                .addOnFailureListener(e -> {
                                    // Manejar el error al guardar los datos en Firestore
                                    Log.e(TAG, "Error al guardar la fecha en Firebase: " + selectedDate, e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Manejar el error al verificar la existencia de la fecha en Firebase
                    Log.e(TAG, "Error al verificar la existencia de la fecha en Firebase: " + selectedDate, e);
                });
    }
}
