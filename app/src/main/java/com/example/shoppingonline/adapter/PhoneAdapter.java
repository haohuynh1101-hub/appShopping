package com.example.shoppingonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingonline.R;
import com.example.shoppingonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrPhone;

    public PhoneAdapter(Context context, ArrayList<SanPham> arrPhone) {
        this.context = context;
        this.arrPhone = arrPhone;
    }

    @Override
    public int getCount() {
        return arrPhone.size();
    }

    @Override
    public Object getItem(int position) {
        return arrPhone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtNamePhone, txtPricePhone, txtDesPhone;
        public ImageView imgPhone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_phone, null);
            viewHolder.imgPhone = convertView.findViewById(R.id.imgPhone);
            viewHolder.txtNamePhone = convertView.findViewById(R.id.txtNamePhone);
            viewHolder.txtPricePhone = convertView.findViewById(R.id.txtPricePhone);
            viewHolder.txtDesPhone = convertView.findViewById(R.id.txtDesProduct);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            //Toast.makeText(context,"Đã load hết sản phẩm",Toast.LENGTH_SHORT).show();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtNamePhone.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
         viewHolder.txtPricePhone.setText("Giá : "+decimalFormat.format(sanPham.getGiaSanPham()) +"Đ");
        viewHolder.txtDesPhone.setMaxLines(2);
        viewHolder.txtDesPhone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesPhone.setText(sanPham.getDesSanPham());
        Picasso.get().load(sanPham.getImgSanPham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgPhone);

        return convertView;
    }
}
