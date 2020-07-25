package com.example.shoppingonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingonline.R;
import com.example.shoppingonline.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {
    ArrayList<LoaiSP> listLoaiSanPham;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> listLoaiSanPham, Context context) {
        this.listLoaiSanPham = listLoaiSanPham;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listLoaiSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiSanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class ViewHolder {
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_loaisanpham, null);
            viewHolder.txtTenLoaiSP = convertView.findViewById(R.id.txtTenLoaiSP);
            viewHolder.imgLoaiSP = convertView.findViewById(R.id.imgLoaiSanPham);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);
        viewHolder.txtTenLoaiSP.setText(loaiSP.getTenLoaiSP());
        Picasso.get().load(loaiSP.getHinhAnhLoaiSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgLoaiSP);
        return convertView;
    }
}
