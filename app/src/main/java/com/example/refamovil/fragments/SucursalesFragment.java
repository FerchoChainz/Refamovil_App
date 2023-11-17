package com.example.refamovil.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.refamovil.R;
import com.example.refamovil.fragment_nuestras_sucursales;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SucursalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SucursalesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SucursalesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SucursalesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SucursalesFragment newInstance(String param1, String param2) {
        SucursalesFragment fragment = new SucursalesFragment();
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
        View view = inflater.inflate(R.layout.fragment_sucursales, container, false);

        // Agregar un OnClickListener al elemento de la interfaz de usuario que debe iniciar la nueva actividad
        Button btnIrAActividad = view.findViewById(R.id.btnIrAActividad); // Reemplaza con el ID de tu botón
        btnIrAActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamada al método para iniciar la nueva actividad
                irANuevaActividad();
            }
        });

        return view;
    }

    // Método para iniciar la nueva actividad
    private void irANuevaActividad() {
        Intent intent = new Intent(getActivity(), fragment_nuestras_sucursales.class); // Reemplaza con el nombre de tu actividad
        startActivity(intent);
    }

}