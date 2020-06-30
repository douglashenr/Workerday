package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.adapter.ListViewAdapterMainActivity;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DateUtil;


public class ListViewMainFragment extends Fragment implements View.OnClickListener {

    String FRAGMENT_TAG_LISTVIEWMAIN = "LISTVIEWFRAGMENT";
    String FRAGMENT_TAG_USERSETTINGS = "USERSETTINGS";
    String FRAGMENT_TAG_INFORMATION = "INFORMATION";
    String FRAGMENT_TAG_REGISTRY = "REGISTRY";


    private View view;
    private ImageButton buttonAddRegistry;
    private User user;
    private ArrayList<Registry> registryArrayList;
    private Registry registry;
    private ListView listViewAdapter;
    ListViewAdapterMainActivity adapter;


    public ListViewMainFragment() {
        // Required empty public constructor
    }

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


    private void setView() {
        buttonAddRegistry = view.findViewById(R.id.button_add_registry);
        buttonAddRegistry.setOnClickListener(this);

        listViewAdapter = view.findViewById(R.id.list_view_main_activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_registry:
                //startActivityAddRegistry(user);
                goToFragmentAddRegistry();
                break;

            default:
                break;
        }
    }

    private void goToFragmentAddRegistry() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_fragment_mainActivity, new AddRegistryFragment());
        fragmentTransaction.addToBackStack(FRAGMENT_TAG_REGISTRY);
        fragmentTransaction.commit();
    }

    public void updateArrayListRegistry() {

        //Se registro estiver nulo, verificar se o intent tem ou não tem alguma coisa para criar o objeto lista
        //registry = (Registry) getIntent().getSerializableExtra("registry");


        if (registry != null) {
            registryArrayList.add(registry);
            registry = null;
        }


    }


    public void updateListView() {
        adapter.notifyDataSetChanged();


    }


    private void setListViewAdapter() {
        adapter = new ListViewAdapterMainActivity(registryArrayList, getActivity(), this);
        listViewAdapter.setAdapter(adapter);
        listViewAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Registry registry = (Registry) listViewAdapter.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("registry", registry);

                AddRegistryFragment addRegistryFragment = new AddRegistryFragment();
                addRegistryFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_fragment_mainActivity, addRegistryFragment);
                fragmentTransaction.addToBackStack(FRAGMENT_TAG_REGISTRY);
                fragmentTransaction.commit();
            }
        });
    }

}
