package com.example.refamovil.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refamovil.fragments.fragment_carrito;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final ListAdapter adapter;
    private final fragment_carrito carrito;

    public SwipeToDeleteCallback(ListAdapter adapter, fragment_carrito carrito) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.carrito = carrito;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.removeItem(position);
        carrito.updateSubtotalAndTotal();
    }
}

