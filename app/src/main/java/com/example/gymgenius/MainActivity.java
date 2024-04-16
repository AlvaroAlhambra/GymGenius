package com.example.gymgenius;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymgenius.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            Toast.makeText(this, "ME TOCASTE", Toast.LENGTH_SHORT).show();
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Inicializar preferencias para mostrar el correo electrónico debajo del logo de la APP
        startPreferences(navigationView);

        //Configuracion del LogOut
        Menu menu = navigationView.getMenu();
        MenuItem logOutMenuItem = menu.findItem(R.id.nav_logout);
        logOutMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logOut();
                return true;
            }
        });

        // Configurar menú lateral y navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_training, R.id.nav_diet, R.id.nav_account)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Puntos de control para verificar la navegación entre fragmentos
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.d("alvaro", "Fragment changed to: " + destination.getLabel());
        });
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

    private void startPreferences(NavigationView navigationView){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Obtén el correo electrónico del usuario después de iniciar sesión
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        //Cambiar correo en el NAV HEADER
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_email", userEmail);
        editor.apply();

        // Actualiza el TextView en el encabezado de navegación
        View headerView = navigationView.getHeaderView(0); // Asegúrate de tener el índice correcto si tienes múltiples encabezados
        TextView navSubtitle = headerView.findViewById(R.id.user_txt);
        navSubtitle.setText(userEmail);
    }
}
