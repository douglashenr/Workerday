package br.com.dhsoftware.workerday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.Serializable;

import br.com.dhsoftware.workerday.fragments.ListViewMainFragment;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;

public class MainActivity extends AppCompatActivity implements Serializable {

    SharedPreferences prefs = null;
    ListViewMainFragment listViewMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMainFragment = new ListViewMainFragment();




        prefs = getSharedPreferences("br.com.dhsoftware.workerday", MODE_PRIVATE);
    }

    private void createFragment(ListViewMainFragment listViewMainFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, listViewMainFragment);
        //fragmentTransaction.addToBackStack(null);
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

        createFragment(listViewMainFragment);
    }


}
