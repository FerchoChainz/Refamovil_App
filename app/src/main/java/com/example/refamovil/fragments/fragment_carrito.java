package com.example.refamovil.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.refamovil.R;
import com.example.refamovil.adapters.ListAdapter;
import com.example.refamovil.adapters.ListElement;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class fragment_carrito extends Fragment  {
    List<ListElement> elements = new ArrayList<>();
    Button comprar;
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListAdapter listAdapter;

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
    }

    public void init() {
        RecyclerView recyclerView = getView().findViewById(R.id.listRecyclerView);

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

//        ListElement element = new ListElement(listElement.getNombreProducto(), listElement.getPrecio(), listElement.getCodigoProducto());
//
//        Log.d("carrito", "Nombre: " + element.getNombreProducto());
//        Log.d("carrito", "Precio: " + element.getPrecio());
//        Log.d("carrito", "Código: " + element.getCodigoProducto());

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

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
                navigateToFragmentCarrito();
            }
        });
        return view;
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

