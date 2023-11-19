package com.example.refamovil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.refamovil.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement> mData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
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
//            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nameProduct.setText(item.getNombreProducto());
            priceProduct.setText(item.getPrecio());
            codeProduct.setText(item.getCodigoProducto());
        }
    }
}
