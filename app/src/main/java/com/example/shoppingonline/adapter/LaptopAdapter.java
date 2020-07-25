package com.example.shoppingonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingonline.R;
import com.example.shoppingonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrLaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> arrLaptop) {
        this.context = context;
        this.arrLaptop = arrLaptop;
    }

    @Override
    public int getCount() {
        return arrLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtNameLaptop,txtPriceLaptop,txtDesLaptop;
        public ImageView imgLaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_laptop, null);
            viewHolder.imgLaptop = convertView.findViewById(R.id.imgLaptop);
            viewHolder.txtNameLaptop = convertView.findViewById(R.id.txtNameLaptop);
            viewHolder.txtPriceLaptop = convertView.findViewById(R.id.txtPriceLaptop);
            viewHolder.txtDesLaptop = convertView.findViewById(R.id.txtDesLaptop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LaptopAdapter.ViewHolder) convertView.getTag();
            //Toast.makeText(context,"Đã load hết sản phẩm",Toast.LENGTH_SHORT).show();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtNameLaptop.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtPriceLaptop.setText("Giá : "+decimalFormat.format(sanPham.getGiaSanPham()) +"Đ");
        viewHolder.txtDesLaptop.setMaxLines(2);
        viewHolder.txtDesLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesLaptop.setText(sanPham.getDesSanPham());
        Picasso.get().load(sanPham.getImgSanPham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgLaptop);
        return convertView;
    }
}
