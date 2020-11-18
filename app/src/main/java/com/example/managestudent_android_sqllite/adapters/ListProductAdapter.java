package com.example.managestudent_android_sqllite.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managestudent_android_sqllite.ClickItemListener;
import com.example.managestudent_android_sqllite.MainActivity;
import com.example.managestudent_android_sqllite.R;
import com.example.managestudent_android_sqllite.models.Product;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder> {
    private LayoutInflater layoutInflater;

    private List<Product> listProduct;

    private int selectedItem = -1;

    private ClickItemListener clickItemListener;

    public ListProductAdapter(Context context, List<Product> listStudent, MainActivity clickItemListener) {
        layoutInflater = LayoutInflater.from(context);
        this.listProduct = listStudent;
        this.clickItemListener = clickItemListener;
    }


    @NonNull
    @Override
    public ListProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.list_product, parent, false);
        return new ProductViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder,final int position) {
        Product product = listProduct.get(position);

        holder.txtName.setText(product.getNameProduct());

        holder.txtSoLuong.setText(String.valueOf(product.getSoLuong()));

        holder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
        if(selectedItem == position){
            holder.linearLayout.setBackgroundColor(Color.GRAY);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousItem = selectedItem;
                selectedItem = position;
                ListProductAdapter.this.notifyItemChanged(previousItem);
                ListProductAdapter.this.notifyItemChanged(position);
                clickItemListener.onItemCLick(listProduct.get(position), position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtSoLuong;

        ListProductAdapter listProductAdapter;

//        AdapterView.OnItemClickListener itemClickListener;

        LinearLayout linearLayout;

        public ProductViewHolder(@NonNull View itemView, ListProductAdapter listProductAdapter) {
            super(itemView);


            txtName = itemView.findViewById(R.id.txtTen);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            linearLayout = itemView.findViewById(R.id.ln);

            this.listProductAdapter = listProductAdapter;


        }
    }
}
