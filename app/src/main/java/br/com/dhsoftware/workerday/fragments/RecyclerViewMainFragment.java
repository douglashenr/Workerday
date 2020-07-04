package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import br.com.dhsoftware.workerday.FragmentController;
import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.adapter.RecyclerViewAdapterMainActivity;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;


public class RecyclerViewMainFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageButton buttonAddRegistry;
    private User user;
    private ArrayList<Registry> registryArrayList;
    private Registry registry;
    private RecyclerView listViewAdapter;
    private FragmentController fragmentController;

    RecyclerView.Adapter adapter;

    public RecyclerViewMainFragment() {
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
        fragmentController = new FragmentController(getFragmentManager());
        user = new User(getActivity());
        registryArrayList = Registry.testArrayList(getActivity());

        //System.out.println("Retorno data: " + DateUtil.getInstanceDateUtil().calculateTotalTimeFromArrayRegistryToString(registryArrayList));
        //System.out.println(DateUtil.getInstanceDateUtil().calculateTimeFromRegistryToString(registryArrayList.get(1)) + " Teste Calculo");


        setRecyclerViewAdapter();

        return view;
    }


    private void setView() {
        buttonAddRegistry = view.findViewById(R.id.button_add_registry);
        buttonAddRegistry.setOnClickListener(this);

        listViewAdapter = view.findViewById(R.id.recyclerView_mainActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_registry:
                //startActivityAddRegistry(user);
                fragmentController.setFragmentAddRegistry(null);
                break;

            default:
                break;
        }
    }


    public void updateArrayListRegistry() {

        //Se registro estiver nulo, verificar se o intent tem ou não tem alguma coisa para criar o objeto lista
        //registry = (Registry) getIntent().getSerializableExtra("registry");


        if (registry != null) {
            registryArrayList.add(registry);
            registry = null;
        }


    }




    private void setRecyclerViewAdapter() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_mainActivity);

        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecyclerViewAdapterMainActivity(registryArrayList, getContext(), recyclerView, getFragmentManager());
        recyclerView.setAdapter(adapter);
    }


}
