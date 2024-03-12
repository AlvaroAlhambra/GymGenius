package com.example.gymgenius.ui.training;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymgenius.R;
import com.example.gymgenius.data.model.TrainingModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
}
