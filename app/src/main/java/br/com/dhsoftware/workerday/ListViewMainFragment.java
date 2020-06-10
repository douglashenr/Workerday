package br.com.dhsoftware.workerday;

import android.content.Intent;
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

import br.com.dhsoftware.workerday.adapter.ListViewAdapterMainActivity;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewMainFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View view;

    ImageButton buttonAddRegistry;
    User user;
    ArrayList<Registry> registryArrayList;
    Registry registry;
    ListView listView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListViewMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListViewMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListViewMainFragment newInstance(String param1, String param2) {
        ListViewMainFragment fragment = new ListViewMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_view_main, container, false);
        System.out.println("OnCreateView");
        setView();

        user = new User("Douglas", "douglas@hotmail.com");
        registryArrayList = new ArrayList<>();

        return view;
    }



    public void setView(){
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
        }
    }

    public void goToFragmentAddRegistry(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, new AddRegistryFragment());
        fragmentTransaction.addToBackStack(null);
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

    public void setListViewAdapter(){
        ListViewAdapterMainActivity adapter = new ListViewAdapterMainActivity(registryArrayList, getActivity());

        listView.setAdapter(adapter);
    }



}
