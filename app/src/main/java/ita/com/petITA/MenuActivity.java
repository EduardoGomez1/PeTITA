package ita.com.petITA;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import ita.com.petITA.databinding.ActivityMenuBinding;
import ita.com.petITA.R;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Hacer que no gire
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(binding.appBarMenu.toolbar);

        
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.supportpcFragment, R.id.supportLapFragment, R.id.instalationNetFragment,R.id.instalationSoftFragment , R.id.aboutFragment, R.id.contacFragment, R.id.termAndConditionsFragment, R.id.homeServiceFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Cerrar sesion
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_languaje:
                change();
                return true;

            case R.id.action_languajeEsp:
                spanish();
                return true;
            case R.id.tema:
                dark();
                return true;

            case R.id.lingt:
                light();
                return true;

            case R.id.closeSession:
                closesession();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void light() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void spanish() {
        Locale localizacion = new Locale("es", "ES");
        Locale.setDefault(localizacion);
        Configuration config = new Configuration();
        config.locale = localizacion;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent refrescar = new Intent(this, MenuActivity.class);
        startActivity(refrescar);
        finish();
    }

    //Metodo para cerrar sesion
    private void closesession() {
        this.finish();
    }

    //Metodo para cambiar idioma
    private void change(){
        Locale localizacion = new Locale("en", "EN");
        Locale.setDefault(localizacion);
        Configuration config = new Configuration();
        config.locale = localizacion;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent refrescar = new Intent(this, MenuActivity.class);
        startActivity(refrescar);
        finish();
    }

    private void dark(){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}