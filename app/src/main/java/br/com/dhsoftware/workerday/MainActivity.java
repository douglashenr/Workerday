package br.com.dhsoftware.workerday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

import br.com.dhsoftware.workerday.fragments.ListViewMainFragment;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;

public class MainActivity extends AppCompatActivity implements Serializable {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListViewMainFragment listViewMainFragment = new ListViewMainFragment();


        createFragment(listViewMainFragment);

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

        final JSONUser jsonUser = new JSONUser();
        if(!jsonUser.isFilePresent(this)){
            jsonUser.createJSONUser();
            jsonUser.create(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogUtil dialog = new DialogUtil(this);
        dialog.welcomeDialog();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs


            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }
}
