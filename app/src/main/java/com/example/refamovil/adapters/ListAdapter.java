package com.example.refamovil.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refamovil.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement> mData;
    private LayoutInflater layoutInflater;
    private Context context;

    private ListAdapter.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElement item);
    }

    public ListAdapter(List<ListElement> itemList, Context context ,  ListAdapter.OnItemClickListener listener){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.list_element_adapter,null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElement> items){mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nameProduct, priceProduct, codeProduct;

        ViewHolder(View view){
            super(view);
            iconImage = view.findViewById(R.id.iconImageView);
            nameProduct = view.findViewById(R.id.txtProductName);
            priceProduct = view.findViewById(R.id.txtPrice);
            codeProduct = view.findViewById(R.id.txtProductCode);
        }

        void bindData(final ListElement item){
            nameProduct.setText(item.getNombreProducto());
            priceProduct.setText(item.getPrecio());
            codeProduct.setText(item.getCodigoProducto());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                    uploadData(item);

                }
            });
        }

        private void uploadData(ListElement element){
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();


            firestore.collection("Productos")
                    .add(element.toMap())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", "Documento subido con ID" + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG FAIL", "Error al subir el documento", e);
                        }
                    });
        }
    }
}
