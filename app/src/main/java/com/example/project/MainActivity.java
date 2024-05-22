package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle toggle;
    public boolean i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        i = sharedPreferences.getBoolean("isDarkTheme", true);
        if (i) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_main);
        moveToHomeFr();
        addNavigationDrawer();
        ButtonNav();

    }
    // Обработка нажатия на иконку меню в ActionBar для открытия и закрытия Drawer
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ButtonNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    // Обработка выбора раздела "Домой"
                    moveToHomeFr();
                    return true;
                } else if (item.getItemId() == R.id.search) {
                    // Обработка выбора раздела "Поиск"
                    moveToSearchFr();
                    return true;
                } else if (item.getItemId() == R.id.news) {
                    // Обработка выбора раздела "Новости"
                    moveToNewsFr();
                    return true;
                } else if (item.getItemId() == R.id.settings) {
                    // Обработка выбора раздела "Настройки"
                    moveToSettingsFr();
                    return true;
                }else if (item.getItemId() == R.id.favorite) {
                    // Обработка выбора раздела "Избранное"
                    moveToFavoriteFr();
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

    private void addNavigationDrawer(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.drawer_open,
                R.string.drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem modeItem = navigationView.getMenu().findItem(R.id.mode);
        if (!i){
            modeItem.setIcon(R.drawable.dark_mode);
            modeItem.setTitle(R.string.dark_mode);
        }else{
            modeItem.setIcon(R.drawable.light_mode);
            modeItem.setTitle(R.string.light_mode);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login) {
            moveToLoginFr();
        } else if (id == R.id.mode) {
            if (i) {
                // устанавливаем светлую иконку для пункта меню
                setTheme(R.style.LightTheme);
                i = false;
            } else {
                // устанавливаем темную иконку для пункта меню
                setTheme(R.style.DarkTheme);
                i = true;
            }
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDarkTheme", i);
            editor.apply();
            recreate();
        }
        else if (id == R.id.info) {
            moveToInfoFr();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void moveToHomeFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment fHome = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fHome);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void moveToSearchFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFragment fSearch = new SearchFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fSearch);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void moveToNewsFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsFragment fNews = new NewsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fNews);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void moveToSettingsFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingsFragment fSettings = new SettingsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fSettings);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void moveToLoginFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fLogin = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fLogin);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void moveToFavoriteFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FavoriteFragment fFavorite = new FavoriteFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fFavorite);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void moveToInfoFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        InfoFragment fInfo = new InfoFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fInfo);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}