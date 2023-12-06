package com.example.refamovil.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.refamovil.InicioActivity;
import com.example.refamovil.MainActivity;
import com.example.refamovil.R;
import com.example.refamovil.UserModel;
import com.example.refamovil.adapters.ListAdapter;
import com.example.refamovil.adapters.ListElement;
import com.example.refamovil.adapters.SwipeToDeleteCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class fragment_carrito extends Fragment  {
    List<ListElement> elements = new ArrayList<>();
    Button comprar;
    RecyclerView recyclerView;
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListAdapter listAdapter;
    TextView sub, total;
    private FirebaseFirestore firebaseFirestore;
    private float accountTotal;

    public fragment_carrito() {
        elements = new ArrayList<>();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        listAdapter = new ListAdapter(elements, getContext(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                showMessageOnClick(item);
            }
        });
        init();
        listAdapter.notifyDataSetChanged();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(listAdapter, fragment_carrito.this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void init() {
        recyclerView = getView().findViewById(R.id.listRecyclerView);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(listAdapter);
        } else {
            Log.e("TAG", "RecyclerView is null");
        }
    }

    public void setListElement(List<ListElement> listElement) {
        if (elements == null) {
            elements = new ArrayList<>();
        }

        elements.addAll(listElement);

    }


    public void showMessageOnClick(ListElement item) {
        // Borrar cardView
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        comprar = view.findViewById(R.id.btnRegistrar);
        sub = view.findViewById(R.id.txtSubTotal);
        total = view.findViewById(R.id.txtTotal);
        firebaseFirestore = FirebaseFirestore.getInstance();

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();

                navigateToFragmentCarrito();
                savePedidos();
            }
        });

        updateSubtotalAndTotal();
        return view;
    }


    public void updateSubtotalAndTotal() {
        float subtotal = 0;
        for (ListElement element : elements) {
            String precioConSigno = element.getPrecio().toString();
            float soloNumero = Float.parseFloat(precioConSigno.replace("$", ""));
            subtotal += soloNumero;
        }

        sub.setText("Subtotal: " + String.valueOf(subtotal));
        float totalAcount = (float) (subtotal * 1.10);
        accountTotal = totalAcount;
        total.setText("Total: " + String.valueOf(totalAcount));
    }


    private void navigateToFragmentCarrito() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Verifica si el fragmento ya está agregado
        fragment_carrito existingFragment = (fragment_carrito) fragmentManager.findFragmentByTag("fragment_carrito");

        if (existingFragment == null) {
            existingFragment = new fragment_carrito();
            fragmentTransaction.add(R.id.fragment_container, existingFragment, "fragment_carrito");
        }

        fragmentTransaction.replace(R.id.fragment_container, existingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public void savePedidos(){
        String titulo = "Pedido " + new Date().toLocaleString();
        String productos = "";

        for(ListElement element: elements){
            productos += "\n" + element.getNombreProducto().toString()
            + "\t" + element.getPrecio().toString() + "\n";
        }
        String total = "Total: " + String.valueOf(accountTotal);
        uploadPedidos(titulo, productos, total);
    }

    private void uploadPedidos(String titulo, String productos, String total) {
        Map<String, Object> map = new HashMap<>();

        UserModel tuViewModel = new ViewModelProvider(this).get(UserModel.class);
        tuViewModel.setUsername(InicioActivity.nombre);

        map.put("usuario", InicioActivity.nombre);
        map.put("titulo", titulo);
        map.put("descripcion", productos);
        map.put("total", total);
        firebaseFirestore.collection("pedido").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Pedido creado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), InicioActivity.class);
                intent.putExtra("username", InicioActivity.nombre);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error to introduce pedido", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_orange)
                .setContentTitle("RefamovilApp")
                .setContentText("Tu pedido se ha hecho con exito. Checkalo!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

