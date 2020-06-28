package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.adapter.ListViewAdapterMainActivity;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DateUtil;


/*
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewMainFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String FRAGMENT_TAG_LISTVIEWMAIN = "LISTVIEWFRAGMENT";
    String FRAGMENT_TAG_USERSETTINGS = "USERSETTINGS";
    String FRAGMENT_TAG_INFORMATION = "INFORMATION";
    String FRAGMENT_TAG_REGISTRY = "REGISTRY";


    private View view;
    private ImageButton buttonAddRegistry;
    private User user;
    private ArrayList<Registry> registryArrayList;
    private Registry registry;
    private ListView listView;


    public ListViewMainFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListViewMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static ListViewMainFragment newInstance(String param1, String param2) {
        ListViewMainFragment fragment = new ListViewMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_view_main, container, false);
        System.out.println("OnCreateView");
        setView();

        user = new User(getActivity());
        registryArrayList = Registry.testArrayList(getActivity());
        System.out.println("Retorno data: " + DateUtil.getInstanceDateUtil().calculateTotalTimeFromArrayRegistryToString(registryArrayList));
        //System.out.println(DateUtil.getInstanceDateUtil().calculateTimeFromRegistryToString(registryArrayList.get(1)) + " Teste Calculo");


        setListViewAdapter();

        return view;
    }



    private void setView(){
        buttonAddRegistry = view.findViewById(R.id.button_add_registry);
        buttonAddRegistry.setOnClickListener(this);

        listView = view.findViewById(R.id.list_view_main_activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_add_registry:
                //startActivityAddRegistry(user);
                goToFragmentAddRegistry();
                break;

            default:
                break;
        }
    }

    private void goToFragmentAddRegistry(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_fragment_mainActivity, new AddRegistryFragment());
        fragmentTransaction.addToBackStack(FRAGMENT_TAG_REGISTRY);
        fragmentTransaction.commit();
    }

    public void updateArrayListRegistry(){

        //Se registro estiver nulo, verificar se o intent tem ou não tem alguma coisa para criar o objeto lista
        //registry = (Registry) getIntent().getSerializableExtra("registry");


        if(registry != null){
            registryArrayList.add(registry);
            registry = null;
        }


    }

    private void setListViewAdapter(){
        ListViewAdapterMainActivity adapter = new ListViewAdapterMainActivity(registryArrayList, getActivity());
        listView.setAdapter(adapter);
    }

}
