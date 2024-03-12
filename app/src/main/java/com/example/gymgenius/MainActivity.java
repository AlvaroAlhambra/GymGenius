package com.example.gymgenius;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.gymgenius.databinding.ActivityMainBinding;
import com.example.gymgenius.ui.training.TrainingViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TrainingViewModel trainingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Al hacer clic en el botón flotante
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        /*//QUITAR DE AQUI EL LLAMAR AL STARTPREFERENCES
        // Obtén una instancia del TrainingViewModel
        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);

        // Llama al método startPreferences() del TrainingViewModel
        trainingViewModel.startPreferences(findViewById(R.id.nav_view)); // Ajusta el ID según tu layout
*/
        Menu menu = navigationView.getMenu();
        MenuItem logOutMenuItem = menu.findItem(R.id.nav_logout);
        logOutMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logOut();
                return true;
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_training, R.id.nav_diet, R.id.nav_account)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}



        /*// Recuperar el último fragmento visitado desde SharedPreferences o el estado guardado
        if (savedInstanceState != null) {
            lastScreen = savedInstanceState.getString("lastScreen");
        } else {
            lastScreen = preferences.getString("last_screen", null);
        }

        // Mostrar el fragmento correspondiente al último visitado o por defecto el fragmento de Training
        if (lastScreen != null) {
            showFragmentByTag(lastScreen);
        } else {
            showFragmentByTag("Training");
        }*/


    // Método para mostrar un fragmento por su tag
    /*private void showFragmentByTag(String tag) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        if (currentFragment == null || !currentFragment.getClass().getSimpleName().equals(tag + "Fragment")) {
            Fragment fragmentToShow = null;

            if (tag.equals("Account")) {
                fragmentToShow = new AccountFragment();
            } else if (tag.equals("Diet")) {
                fragmentToShow = new DietFragment();
            } else if (tag.equals("Training")) {
                fragmentToShow = new TrainingFragment();
            }

            if (fragmentToShow != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, fragmentToShow)
                        .commit();
            }
        }
    }*/

  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guarda el último fragmento visitado en el estado de la actividad
        outState.putString("lastScreen", lastScreen);
    }*/



    /*@Override
    protected void onStop() {
        super.onStop();

        // Actualizar y guardar la última pantalla visitada en SharedPreferences
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        String currentFragmentTag = navController.getCurrentDestination().getLabel().toString();
        preferences.edit().putString("last_screen", currentFragmentTag).apply();
    }*/
