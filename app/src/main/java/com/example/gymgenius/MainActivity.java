package com.example.gymgenius;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymgenius.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    //private String lastScreen; // Almacena el tag del último fragmento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        startPreferences(navigationView);

        // Configura el menú "Log Out"
        Menu menu = navigationView.getMenu();
        MenuItem logOutMenuItem = menu.findItem(R.id.nav_logout);
        logOutMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logOut();
                return true;
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_training, R.id.nav_diet, R.id.nav_account)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



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
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Método para cerrar sesión
    public void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    /*@Override
    protected void onStop() {
        super.onStop();

        // Actualizar y guardar la última pantalla visitada en SharedPreferences
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        String currentFragmentTag = navController.getCurrentDestination().getLabel().toString();
        preferences.edit().putString("last_screen", currentFragmentTag).apply();
    }*/

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
