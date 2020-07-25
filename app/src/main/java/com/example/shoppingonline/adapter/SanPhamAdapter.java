package com.example.shoppingonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingonline.R;
import com.example.shoppingonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arrSanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrSanPham) {
        this.context = context;
        this.arrSanPham = arrSanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview_sanpham,null);
        ItemHolder itemHolder=new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
    SanPham sanPham=arrSanPham.get(position);
    holder.txtTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá : "+decimalFormat.format(sanPham.getGiaSanPham()) +"Đ");
        Picasso.get().load(sanPham.getImgSanPham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(holder.imgSanPham);
    }

    @Override
    public int getItemCount() {
        return arrSanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgSanPham;
        public TextView txtTenSanPham, txtGiaSanPham;
        public ItemHolder(View itemView){
            super(itemView);
            imgSanPham=itemView.findViewById(R.id.imgProduct);
            txtTenSanPham=itemView.findViewById(R.id.txtNameProduct);
            txtGiaSanPham=itemView.findViewById(R.id.txtPriceProduct);

        }
    }
}
