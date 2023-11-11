package com.example.gymgenius.ui.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymgenius.data.model.TrainingModel;

public class TrainingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final TrainingModel trainingModel;

    public TrainingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the training fragment");
        trainingModel = new TrainingModel();
    }

    public LiveData<String> getText() {
        return mText;
    }

    // Agrega métodos en el ViewModel según sea necesario para manejar la lógica del entrenamiento
}
