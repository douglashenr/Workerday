package br.com.dhsoftware.workerday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.dhsoftware.workerday.util.DialogUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs = null;
    private FragmentController fragmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView;

        fragmentController = new FragmentController(getSupportFragmentManager());

        setView();

        fragmentController.setFragmentListViewMain();

        prefs = getSharedPreferences("br.com.dhsoftware.workerday", MODE_PRIVATE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void setView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation_navigation_mainActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs

            DialogUtil dialog = new DialogUtil(this);
            dialog.welcomeDialog();

            prefs.edit().putBoolean("firstrun", false).apply();
        }

        //setFragmentListViewMain();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_Settings_menuUserSettings) {
            fragmentController.setFragmentUserSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.page_1:
                if (!fragmentController.verifyCurrentlyFragment(FragmentController.FRAGMENT_TAG_LISTVIEWMAIN)) {
                    fragmentController.setFragmentListViewMain();
                }
                return true;
            case R.id.page_2:
                if (!fragmentController.verifyCurrentlyFragment(FragmentController.FRAGMENT_TAG_INFORMATION))
                    fragmentController.setFragmentInformation();
                return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (!fragmentController.verifyCurrentlyFragment(FragmentController.FRAGMENT_TAG_LISTVIEWMAIN)) {
            fragmentController.goBackFragment();
        } else {
            finishAffinity();
        }
    }
}
