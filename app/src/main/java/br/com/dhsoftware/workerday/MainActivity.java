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
    }

    private void setView() {
        bottomNavigationView = findViewById(R.id.bottomNavigation_navigation_mainActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void createFragmentListViewRegistry(int containerView, Fragment fragment, String tag) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerView, fragment, tag);
        if(fragment == listViewMainFragment){

        }
        else
            fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();




        DialogUtil dialog = new DialogUtil(this);
        dialog.welcomeDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs

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
        switch (item.getItemId()){
            case R.id.item_Settings_menuUserSettings:
                setFragmentUserSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentUserSettings() {
        createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, userSettingsFragment, FRAGMENT_TAG_USERSETTINGS);
    }

    private boolean verifyCurrentlyFragment(String tag){
        Fragment currentlyFragment = fragmentManager.findFragmentByTag(tag);
        if (currentlyFragment != null && currentlyFragment.isVisible()) {
            return true;
        }else{
            return false;
        }

    }

    private void setFragmentInformation(){
        createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, informationFragment, FRAGMENT_TAG_INFORMATION);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.page_1:
                if(!verifyCurrentlyFragment(FRAGMENT_TAG_LISTVIEWMAIN)) {
                    System.out.println("Está na FragmentListViewMain");
                    setFragmentListViewMain();

                }
                return true;
            case R.id.page_2:
                if(!verifyCurrentlyFragment(FRAGMENT_TAG_INFORMATION))
                    setFragmentInformation();
                return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if(!verifyCurrentlyFragment(FRAGMENT_TAG_LISTVIEWMAIN)){
            fragmentController.goBackFragment();
        }else{
            finishAffinity();
        }
    }
}
