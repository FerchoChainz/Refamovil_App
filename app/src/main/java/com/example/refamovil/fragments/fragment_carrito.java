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


public class fragment_carrito extends Fragment {

    List<ListElement> elements;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_carrito() {
        // Required empty public constructor
    }

    public static fragment_carrito newInstance(String param1, String param2) {
        fragment_carrito fragment = new fragment_carrito();
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
        elements.add(new ListElement("Aceite Para carro", "$150", "#3333"));
        elements.add(new ListElement("Aceite Para carro", "$150", "#3333"));
        elements.add(new ListElement("Aceite Para carro", "$150", "#3333"));
        elements.add(new ListElement("Aceite Para carro", "$150", "#3333"));

        ListAdapter listAdapter = new ListAdapter(elements, getContext());
        RecyclerView recyclerView = getView().findViewById(R.id.listRecyclerView);

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
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        return view;
    }
}