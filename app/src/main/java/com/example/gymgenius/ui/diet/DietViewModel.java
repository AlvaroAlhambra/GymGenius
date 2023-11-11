package com.example.gymgenius.ui.diet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymgenius.data.model.DietModel;

public class DietViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final DietModel dietModel;

    public DietViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the diet fragment");
        dietModel = new DietModel();
    }

    public LiveData<String> getText() {
        return mText;
    }

    // Agrega métodos en el ViewModel según sea necesario para manejar la lógica de la dieta
}
