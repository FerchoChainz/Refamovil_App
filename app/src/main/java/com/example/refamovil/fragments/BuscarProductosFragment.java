package com.example.refamovil.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.refamovil.R;
import com.example.refamovil.adapters.ListAdapter;
import com.example.refamovil.adapters.ListElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarProductosFragment extends Fragment {
    List<ListElement> elements;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuscarProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscarProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscarProductosFragment newInstance(String param1, String param2) {
        BuscarProductosFragment fragment = new BuscarProductosFragment();
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
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(); // Llama a init() después de que la vista ha sido creada
    }

    public void init() {
        elements = new ArrayList<>();
        elements.add(new ListElement("Aceite de tiempos", "$250", "#15SFDF5"));


        ListAdapter listAdapter = new ListAdapter(elements, getContext());
        RecyclerView recyclerView = getView().findViewById(R.id.ListaProductos);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(listAdapter);
        } else {
            Log.e("TAG", "RecyclerView is null");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_productos, container, false);
    }
}