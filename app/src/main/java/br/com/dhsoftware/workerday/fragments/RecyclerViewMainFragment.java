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


public class RecyclerViewMainFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ArrayList<Registry> registryArrayList;
    private FragmentController fragmentController;

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
        setView();
        fragmentController = new FragmentController(getFragmentManager());
        registryArrayList = Registry.getRegistryArray(getActivity());


        setRecyclerViewAdapter();

        return view;
    }


    private void setView() {
        ImageButton buttonAddRegistry = view.findViewById(R.id.button_add_registry);
        buttonAddRegistry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_registry) {//startActivityAddRegistry(user);
            fragmentController.setFragmentAddRegistry(null);
        }
    }

    private void setRecyclerViewAdapter() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_mainActivity);

        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.Adapter adapter = new RecyclerViewAdapterMainActivity(registryArrayList, getContext(), recyclerView, getFragmentManager());
        recyclerView.setAdapter(adapter);
    }


}
