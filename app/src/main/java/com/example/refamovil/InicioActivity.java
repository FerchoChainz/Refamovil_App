package com.example.refamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;


import com.example.refamovil.fragments.BuscarProductosFragment;
import com.example.refamovil.fragments.HomeFragment;
import com.example.refamovil.fragments.ProductosFragment;
import com.example.refamovil.fragments.SobreNosotrosFragment;
import com.example.refamovil.fragments.SucursalesFragment;
import com.example.refamovil.fragments.VerComprasFragment;
import com.google.android.material.navigation.NavigationView;

public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar toolbar = findViewById(R.id.toolbarAction);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawner_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_Inicio);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int itemId = item.getItemId();
        if (itemId == R.id.itemLogout) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_Inicio){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (item.getItemId() == R.id.nav_Productos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductosFragment()).commit();
        }else if (item.getItemId() == R.id.nav_Buscar_Productos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BuscarProductosFragment()).commit();
         }else if (item.getItemId() == R.id.nav_Compras) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VerComprasFragment()).commit();
        } else if (item.getItemId() == R.id.nav_sucursales) {
            Intent intent = new Intent(getApplicationContext(), fragment_nuestras_sucursales.class);
            startActivity(intent);
        /*
        } else if (item.getItemId() == R.id.nav_sucursales) {
            Intent intent = new Intent(getApplicationContext(), fragment_nuestras_sucursales.class);
            startActivity(intent);
        }
        */
        }else if (item.getItemId() == R.id.nav_nosotros) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SobreNosotrosFragment()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}