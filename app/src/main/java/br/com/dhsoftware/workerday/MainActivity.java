package br.com.dhsoftware.workerday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.dhsoftware.workerday.adapter.ListViewAdapterMainActivity;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;

public class MainActivity extends AppCompatActivity implements Serializable {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListViewMainFragment listViewMainFragment = new ListViewMainFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, listViewMainFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }





}
