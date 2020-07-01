package br.com.dhsoftware.workerday;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;



public class FragmentController extends FragmentActivity {

    private FragmentManager fragmentManager;


    public FragmentController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void goBackFragment() {
        fragmentManager.popBackStack();
    }
}
