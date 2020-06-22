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

import java.io.Serializable;

import br.com.dhsoftware.workerday.fragments.ListViewMainFragment;
import br.com.dhsoftware.workerday.fragments.UserSettingsFragment;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;

public class MainActivity extends AppCompatActivity implements Serializable {

    private SharedPreferences prefs = null;
    private ListViewMainFragment listViewMainFragment;
    private UserSettingsFragment userSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMainFragment = new ListViewMainFragment();
        userSettingsFragment = new UserSettingsFragment();




        prefs = getSharedPreferences("br.com.dhsoftware.workerday", MODE_PRIVATE);
    }

    private void createFragmentListViewRegistry(int containerView, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerView, fragment);
        if(fragment == listViewMainFragment){

        }
        else
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        JSONUser jsonUser = new JSONUser(this);
        if(!jsonUser.isFilePresent()){
            jsonUser.create();
        }

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

        createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, listViewMainFragment);
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
                createFragmentListViewRegistry(R.id.frameLayout_fragment_mainActivity, userSettingsFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
