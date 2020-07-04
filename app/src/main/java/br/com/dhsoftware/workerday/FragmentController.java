package br.com.dhsoftware.workerday;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import br.com.dhsoftware.workerday.fragments.AddRegistryFragment;
import br.com.dhsoftware.workerday.fragments.InformationFragment;
import br.com.dhsoftware.workerday.fragments.RecyclerViewMainFragment;
import br.com.dhsoftware.workerday.fragments.UserSettingsFragment;


public class FragmentController extends FragmentActivity {

    public static String FRAGMENT_TAG_LISTVIEWMAIN = "LISTVIEWFRAGMENT";
    public static String FRAGMENT_TAG_USERSETTINGS = "USERSETTINGS";
    public static String FRAGMENT_TAG_INFORMATION = "INFORMATION";
    public static String FRAGMENT_TAG_REGISTRY = "REGISTRY";

    private FragmentManager fragmentManager;


    public FragmentController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public boolean verifyCurrentlyFragment(String tag) {
        Fragment currentlyFragment = fragmentManager.findFragmentByTag(tag);
        return currentlyFragment != null && currentlyFragment.isVisible();
    }

    public void goBackFragment() {
        fragmentManager.popBackStack();
    }

    private void createFragment(int containerView, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerView, fragment, tag);
        if (fragment != new RecyclerViewMainFragment())
            fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commit();
    }


    public void setFragmentListViewMain() {
        createFragment(R.id.frameLayout_fragment_mainActivity, new RecyclerViewMainFragment(), FRAGMENT_TAG_LISTVIEWMAIN);
    }

    public void setFragmentUserSettings() {
        createFragment(R.id.frameLayout_fragment_mainActivity, new UserSettingsFragment(), FRAGMENT_TAG_USERSETTINGS);
    }

    public void setFragmentAddRegistry(AddRegistryFragment addRegistryFragment){
        if(addRegistryFragment == null) {
            createFragment(R.id.frameLayout_fragment_mainActivity, new AddRegistryFragment(), FRAGMENT_TAG_REGISTRY);
        }else{
            createFragment(R.id.frameLayout_fragment_mainActivity, addRegistryFragment, FRAGMENT_TAG_REGISTRY);
        }
    }

    public void setFragmentInformation() {
        createFragment(R.id.frameLayout_fragment_mainActivity, new InformationFragment(), FRAGMENT_TAG_INFORMATION);
    }
}
