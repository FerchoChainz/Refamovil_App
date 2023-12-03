package com.example.refamovil.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.refamovil.R;
import com.example.refamovil.adapters.ListAdapter;
import com.example.refamovil.adapters.ListElement;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscarProductosFragment extends Fragment {
    ListElement[] elementos = new ListElement[]{
            new ListElement("Aceite de carro", "$350", "123456789012"),
            new ListElement("Aceite de moto", "$150", "345678901234"),
            new ListElement("Aceite de tiempos", "$250", "567890123456"),
            new ListElement("Aceite de bicicleta", "$550", "789012345678"),
            new ListElement("Aceite de maquinas", "$550", "901234567890"),
            new ListElement("Pila", "$100", "234567890123"),
            new ListElement("Amortiguadores", "$200", "456789012345"),
            new ListElement("Carburador", "$250", "678901234567"),
            new ListElement("Bandas", "$300", "890123456789"),
            new ListElement("Balatas", "$250", "987654321087")
    };
    List <ListElement> elements;
    ListAdapter listAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public BuscarProductosFragment() {
        // Required empty public constructor
    }

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        Button button = view.findViewById(R.id.btnCamara);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    startBarcodeScanner();
                } else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.CAMERA}, 1);
                }
            }
        });
    }


    private void startBarcodeScanner() {
        //biblioteca ZXing para iniciar la cámara y escanear
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escanea un código de barras");
        integrator.setCameraId(0);  // Use la cámara trasera
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String barcode = result.getContents();


                boolean encontrado = false;
                for(int i = 0; i < elementos.length; i++){
                    if(barcode.equals(elementos[i].getCodigoProducto().toString())){
                        String n = elementos[i].getNombreProducto();
                        String p = elementos[i].getPrecio();
                        String c = elementos[i].getCodigoProducto();

                        elements.add(new ListElement(n,p,c));
                        encontrado = true;
                        break; // Suponiendo que deseas salir después de encontrar la primera coincidencia
                    }
                }
                // Notifica al adaptador sobre el cambio en los datos
                listAdapter.notifyDataSetChanged();

                if (!encontrado) {
                    Toast.makeText(getContext(), "Sirve pero no encontró el código", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(),"Sin resultados", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(),"Definitivamente no sirve", Toast.LENGTH_LONG).show();
        }
    }

    private String formatPrice(double price) {
        //limitar a 2 decimales
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "$" + decimalFormat.format(price);
    }

    public void init() {
        elements = new ArrayList<>();
        listAdapter = new ListAdapter(elements, getContext(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                showMessageOnClick(item);
            }
        });
        RecyclerView recyclerView = getView().findViewById(R.id.ListaProductos);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(listAdapter);
        } else {
            Log.e("TAG", "RecyclerView is null");
        }
    }

    public void showMessageOnClick(ListElement item){
        Toast.makeText(getContext(), "Presionaste", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_productos, container, false);
    }
}