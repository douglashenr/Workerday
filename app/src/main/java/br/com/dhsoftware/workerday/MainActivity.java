package br.com.dhsoftware.workerday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import br.com.dhsoftware.workerday.fragments.InformationFragment;
import br.com.dhsoftware.workerday.fragments.ListViewMainFragment;
import br.com.dhsoftware.workerday.fragments.UserSettingsFragment;
import br.com.dhsoftware.workerday.util.DialogUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String FRAGMENT_TAG_LISTVIEWMAIN = "LISTVIEWFRAGMENT";
    String FRAGMENT_TAG_USERSETTINGS = "USERSETTINGS";
    String FRAGMENT_TAG_INFORMATION = "INFORMATION";
    private SharedPreferences prefs = null;
    private ListViewMainFragment listViewMainFragment;
    private UserSettingsFragment userSettingsFragment;
    private InformationFragment informationFragment;
    private BottomNavigationView bottomNavigationView;
    private FragmentController fragmentController;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView;

        listViewMainFragment = new ListViewMainFragment();
        userSettingsFragment = new UserSettingsFragment();
        informationFragment = new InformationFragment();
        fragmentController = new FragmentController(getSupportFragmentManager());

        setView();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout_fragment_mainActivity, listViewMainFragment, FRAGMENT_TAG_LISTVIEWMAIN);
        fragmentTransaction.commit();

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
        bottomNavigationView = findViewById(R.id.bottomNavigation_navigation_mainActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void createFragmentListViewRegistry(int containerView, Fragment fragment, String tag) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerView, fragment, tag);
        if (fragment != listViewMainFragment)
            fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();


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

    private void setFragmentListViewMain() {
        createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, listViewMainFragment, FRAGMENT_TAG_LISTVIEWMAIN);
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
            setFragmentUserSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentUserSettings() {
        createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, userSettingsFragment, FRAGMENT_TAG_USERSETTINGS);
    }

    private boolean verifyCurrentlyFragment(String tag) {
        Fragment currentlyFragment = fragmentManager.findFragmentByTag(tag);
        return currentlyFragment != null && currentlyFragment.isVisible();
    }

    private void setFragmentInformation() {
        createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, informationFragment, FRAGMENT_TAG_INFORMATION);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.page_1:
                if (!verifyCurrentlyFragment(FRAGMENT_TAG_LISTVIEWMAIN)) {
                    setFragmentListViewMain();
                }
                return true;
            case R.id.page_2:
                if (!verifyCurrentlyFragment(FRAGMENT_TAG_INFORMATION))
                    setFragmentInformation();
                return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (!verifyCurrentlyFragment(FRAGMENT_TAG_LISTVIEWMAIN)) {
            fragmentController.goBackFragment();
        } else {
            finishAffinity();
        }
    }
}
