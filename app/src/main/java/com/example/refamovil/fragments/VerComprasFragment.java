package com.example.refamovil.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.refamovil.InicioActivity;
import com.example.refamovil.PedidoModel;
import com.example.refamovil.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class VerComprasFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView mostrar;
    private FirebaseFirestore firebaseFirestore;

    public VerComprasFragment() {
        // Required empty public constructor
    }
    public static VerComprasFragment newInstance(String param1, String param2) {
        VerComprasFragment fragment = new VerComprasFragment();
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
        View view = inflater.inflate(R.layout.fragment_ver_compras, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mostrar = view.findViewById(R.id.txtResults);

        mostrarPedidosEnTextView(mostrar);
        return view;
    }

    private void mostrarPedidosEnTextView(TextView textView) {
        firebaseFirestore.collection("pedido").whereEqualTo("usuario", InicioActivity.nombre)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        StringBuilder contenidoPedidos = new StringBuilder();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            PedidoModel pedido = document.toObject(PedidoModel.class);

                            contenidoPedidos.append("Título: ").append(pedido.getTitulo()).append("\n");
                            contenidoPedidos.append("Descripción: ").append(pedido.getDescripcion()).append("\n");
                            contenidoPedidos.append("Total: ").append(pedido.getTotal()).append("\n\n");
                        }

                        mostrar.setText(contenidoPedidos.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al cargar pedidos", Toast.LENGTH_LONG).show();
                    }
                });
    }

}