package br.com.dhsoftware.workerday;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class FragmentController extends FragmentActivity {

    private FragmentManager fragmentManager;


    public FragmentController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void goBackFragment(){
        fragmentManager.popBackStack();
    }
}
