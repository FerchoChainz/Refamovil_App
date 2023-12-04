package com.example.refamovil.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.refamovil.R;
import com.example.refamovil.adapters.ListAdapter;
import com.example.refamovil.adapters.ListElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosFragment extends Fragment {
    List<ListElement> elements;
    ListAdapter listAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ProductosFragment() {
        // Required empty public constructor
    }

    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
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
        setAllProducts();

         listAdapter = new ListAdapter(elements, getContext(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                showMessageOnClick(item);
            }
        });
        RecyclerView recyclerView = getView().findViewById(R.id.list);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(listAdapter);
        } else {
            Log.e("TAG", "RecyclerView is null");
        }
    }

    public void showMessageOnClick(ListElement item){
        ListElement sendItem = new ListElement(item.getNombreProducto(), item.getPrecio(), item.getCodigoProducto());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment_carrito carrito = new fragment_carrito();
        carrito.setListElement(sendItem);
        fragmentTransaction.replace(R.id.fragment_container, carrito);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setAllProducts() {
        elements.clear();
        elements.add(new ListElement("Aceite de carro", "$350", "123456789012"));
        elements.add(new ListElement("Aceite de moto", "$150", "345678901234"));
        elements.add(new ListElement("Aceite de tiempos", "$250", "567890123456"));
        elements.add(new ListElement("Aceite de bicicleta", "$550", "789012345678"));
        elements.add(new ListElement("Aceite de maquinas", "$550", "901234567890"));
        elements.add(new ListElement("Pila", "$100", "234567890123"));
        elements.add(new ListElement("Amortiguadores", "$200", "456789012345"));
        elements.add(new ListElement("Carburador", "$250", "678901234567"));
        elements.add(new ListElement("Bandas", "$300", "890123456789"));
        elements.add(new ListElement("Balatas", "$250", "987654321087"));
    }

    public void setOnAceites() {
        elements.clear();
        elements.add(new ListElement("Aceite de carro", "$350", "123456789012"));
        elements.add(new ListElement("Aceite de moto", "$150", "345678901234"));
        elements.add(new ListElement("Aceite de tiempos", "$250", "567890123456"));
        elements.add(new ListElement("Aceite de bicicleta", "$550", "789012345678"));
        elements.add(new ListElement("Aceite de maquinas", "$550", "901234567890"));
    }

    public void setOnAutopartes() {
        elements.clear();
        elements.add(new ListElement("Pila", "$100", "234567890123"));
        elements.add(new ListElement("Amortiguadores", "$200", "456789012345"));
        elements.add(new ListElement("Carburador", "$250", "678901234567"));
        elements.add(new ListElement("Bandas", "$300", "890123456789"));
        elements.add(new ListElement("Balatas", "$250", "987654321087"));
        // Agrega más elementos según sea necesario
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);

        // Obtén una referencia al Spinner desde el diseño XML
        Spinner spinner = root.findViewById(R.id.spTipo);  // Asegúrate de tener el ID correcto

        // Crear una lista de opciones para el Spinner
        List<String> opciones = new ArrayList<>();
        opciones.add("Todos los productos");
        opciones.add("Aceites");
        opciones.add("Autopartes");

        // Crear un adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcionSeleccionada = opciones.get(position);

                if (opcionSeleccionada.equals("Aceites")) {
                    setOnAceites();
                } else if (opcionSeleccionada.equals("Autopartes")) {
                    setOnAutopartes();
                } else {
                    setAllProducts();
                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejar la selección nula si es necesario
            }
        });

        return root;
    }
}