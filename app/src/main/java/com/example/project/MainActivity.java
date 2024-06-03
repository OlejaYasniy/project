package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.project.defaultfragments.InfoFragment;
import com.example.project.search.SearchFragment;
import com.example.project.login.LoginFragment;
import com.example.project.login.ProfileFragment;
import com.example.project.defaultfragments.SettingsFragment;
import com.example.project.news.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle toggle;
    public static boolean i;
    private int currentFragment;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChangeTheme();
        setContentView(R.layout.activity_main);
        addNavigationDrawer();
        ButtonNav();
        if (savedInstanceState == null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            currentFragment = sharedPreferences.getInt("current_fragment", 0);
            moveToFr();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_fragment", currentFragment);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentFragment = savedInstanceState.getInt("current_fragment");
    }

    public void moveToFr(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        currentFragment = sharedPreferences
                .getInt("current_fragment", 0);
        if (currentFragment == 0) {
            moveToSearchFr();
        } else if (currentFragment == 1) {
            moveToNewsFr();
        } else if (currentFragment == 2) {
            moveToSettingsFr();
        } else if (currentFragment == 3) {
            moveToLoginFr();
        }else if (currentFragment == 5) {
            moveToInfoFr();
        }
    }

    public void setChangeTheme(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        i = sharedPreferences.getBoolean("isDarkTheme", false);
        if (i) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ButtonNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.search) {
                            moveToSearchFr();
                            getSupportActionBar().setTitle("Поиск");
                            return true;
                        } else if (item.getItemId() == R.id.news) {
                            moveToNewsFr();
                            getSupportActionBar().setTitle("Новости");
                            return true;
                        } else if (item.getItemId() == R.id.settings) {
                            moveToSettingsFr();
                            getSupportActionBar().setTitle("Настройки");
                            return true;
                        } else {
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

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem modeItem = navigationView.getMenu().findItem(R.id.mode);
        if (!i){
            modeItem.setIcon(R.drawable.dark_mode);
            modeItem.setTitle(R.string.dark_mode);
        }else{
            modeItem.setIcon(R.drawable.light_mode);
            modeItem.setTitle(R.string.light_mode);
        }
        MenuItem loginItem = navigationView.getMenu().findItem(R.id.login);
        if (getLoginIconState()) {
            loginItem.setTitle(R.string.profile);
        } else {
            loginItem.setTitle(R.string.enter);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login) {
            String title = item.getTitle().toString();
            if (title.equals("Войти")) {
                getSupportActionBar().setTitle("Войти");
                moveToLoginFr();
            } else if (title.equals("Профиль")) {
                moveToProfileFr();
                getSupportActionBar().setTitle("Профиль");
            }
        } else if (id == R.id.mode) {
            changeTheme();
        }
        else if (id == R.id.info) {
            getSupportActionBar().setTitle("Информация");
            moveToInfoFr();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeTheme(){
        if (i) {
            i = false;
        } else {
            i = true;
        }
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkTheme", i);
        editor.apply();
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putInt("current_fragment", currentFragment);
        editor.apply();
        recreate();
    }

    public void changeIcon() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        MenuItem loginItem = navigationView.getMenu().findItem(R.id.login);
        loginItem.setTitle(R.string.profile);
        saveLoginIconState(true);
    }

    public void changeIcon2() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        MenuItem loginItem = navigationView.getMenu().findItem(R.id.login);
        loginItem.setTitle(R.string.enter);
        saveLoginIconState(false);
    }

    public void moveToSearchFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFragment fSearch = new SearchFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fSearch);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_fragment", 0);
        editor.apply();
    }

    public void moveToNewsFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsFragment fNews = new NewsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fNews);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_fragment", 1);
        editor.apply();
    }

    public void moveToSettingsFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingsFragment fSettings = new SettingsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fSettings);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_fragment", 2);
        editor.apply();
    }

    public void moveToLoginFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fLogin = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fLogin, "LoginFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_fragment", 3);
        editor.apply();
    }

    public void moveToInfoFr() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        InfoFragment fInfo = new InfoFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fInfo);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_fragment", 5);
        editor.apply();
    }

    private void moveToProfileFr() {
        String[] userData = getUserData();
        String login = userData[0];
        String password = userData[1];

        if (login != null && password != null) {
            ProfileFragment profileFragment = ProfileFragment.newInstance(login, password);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, profileFragment, "ProfileFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_fragment", 7);
        editor.apply();
    }


    public String[] getUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login", null);
        String password = sharedPreferences.getString("password", null);
        return new String[]{login, password};
    }

    public void saveLoginIconState(boolean isProfile) {
        SharedPreferences sharedPreferences = getSharedPreferences("icon_state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_profile", isProfile);
        editor.apply();
    }

    public boolean getLoginIconState() {
        SharedPreferences sharedPreferences = getSharedPreferences("icon_state", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_profile", false);
    }

}