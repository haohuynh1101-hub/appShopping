package com.example.shoppingonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingonline.R;
import com.example.shoppingonline.activity.GioHangActivity;
import com.example.shoppingonline.activity.MainActivity;
import com.example.shoppingonline.model.GioHang;
import com.example.shoppingonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrGioHang) {
        this.context = context;
        this.arrGioHang = arrGioHang;
    }

    @Override
    public int getCount() {
        return arrGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
public class ViewHolder{
        public TextView txtName,txtPrice;
        public ImageView img;
        public Button btnAdd,btnSubtract;
        public EditText edtValue;
}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GioHangAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_giohang, null);
            viewHolder.img = convertView.findViewById(R.id.imgGioHang);
            viewHolder.txtName = convertView.findViewById(R.id.txtNameGioHang);
            viewHolder.txtPrice = convertView.findViewById(R.id.txtPriceGioHang);
            viewHolder.btnAdd=convertView.findViewById(R.id.btnAdd);
            viewHolder.btnSubtract=convertView.findViewById(R.id.btnSubtract);
            viewHolder.edtValue=convertView.findViewById(R.id.edtValue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GioHangAdapter.ViewHolder) convertView.getTag();
        }
        final GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtName.setText(gioHang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(gioHang.getGiasp()) +"đ");
        Picasso.get().load(gioHang.getImgsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.img);
        viewHolder.edtValue.setText(gioHang.getSoluong()+"");
        int soLuong=Integer.parseInt(viewHolder.edtValue.getText().toString());
        if(soLuong<2){
            viewHolder.btnSubtract.setEnabled(false);
        }
            viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newQuantity=Integer.parseInt(viewHolder.edtValue.getText().toString())+1;
                    int nowQuantity= MainActivity.listGioHang.get(position).getSoluong();
                    long nowPrice=MainActivity.listGioHang.get(position).getGiasp();
                    MainActivity.listGioHang.get(position).setSoluong(newQuantity);
                    long newPrice= (newQuantity*nowPrice)/nowQuantity;
                    MainActivity.listGioHang.get(position).setGiasp((int) newPrice);
                    DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                    viewHolder.txtPrice.setText(decimalFormat.format(newPrice)+"đ");
                    viewHolder.edtValue.setText(newQuantity+"");
                    GioHangActivity.EventUtil();
                    if(newQuantity>1){
                        viewHolder.btnSubtract.setEnabled(true);
                    }
                }
            });
            viewHolder.btnSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newQuantity=Integer.parseInt(viewHolder.edtValue.getText().toString())-1;
                    int nowQuantity= MainActivity.listGioHang.get(position).getSoluong();
                    MainActivity.listGioHang.get(position).setSoluong(newQuantity);
                    long nowPrice=MainActivity.listGioHang.get(position).getGiasp();
                    MainActivity.listGioHang.get(position).setSoluong(newQuantity);
                    long newPrice=  (newQuantity*nowPrice)/nowQuantity;
                    MainActivity.listGioHang.get(position).setGiasp((int) newPrice);
                    DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                    viewHolder.txtPrice.setText(decimalFormat.format(newPrice)+"đ");
                    viewHolder.edtValue.setText(newQuantity+"");
                    GioHangActivity.EventUtil();
                    if(newQuantity<2){
                        viewHolder.btnSubtract.setEnabled(false);
                    }
                    else {
                        viewHolder.btnSubtract.setEnabled(true);
                    }
          }
            });
        return convertView;
    }
}
