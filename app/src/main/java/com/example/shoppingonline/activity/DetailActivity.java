package com.example.shoppingonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.shoppingonline.R;
import com.example.shoppingonline.databinding.ActivityDetailBinding;
import com.example.shoppingonline.model.GioHang;
import com.example.shoppingonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    int id = 0;
    String name = "";
    Integer price = 0;
    String description = "";
    String img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionToolbar();
        GetDetailProduct();
        CatchEventSpninner();
        addEvents();
    }

    private void addEvents() {
        AddToCart();
    }

    private void AddToCart() {
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (MainActivity.listGioHang.size() > 0) {
//                    int sl=1;
//                    for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
//                        if (MainActivity.listGioHang.get(i).getIdsp() == id) {
//                            MainActivity.listGioHang.get(i).setSoluong(MainActivity.listGioHang.get(i).getSoluong() + sl);
//                            if (MainActivity.listGioHang.size() >= 10) {
//                                MainActivity.listGioHang.get(i).setSoluong(10);
//                            }
//                            MainActivity.listGioHang.get(i).setGiasp(price * MainActivity.listGioHang.get(i).getSoluong());
//                        }
//                    }
//                } else {
                    int soLuong = 1;
                    Integer total = price * soLuong;
                    MainActivity.listGioHang.add(new GioHang(id, name, total, img, soLuong));
//                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpninner() {
//        Integer[] soLuong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soLuong);
//        binding.spinner.setAdapter(adapter);
    }

    private void GetDetailProduct() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsp");
        id = sanPham.getId();
        name = sanPham.getTenSanPham();
        price = sanPham.getGiaSanPham();
        description = sanPham.getDesSanPham();
        img = sanPham.getImgSanPham();
        binding.txtNameDetailProduct.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        binding.txtPriceProductDetail.setText("Giá: " + decimalFormat.format(price) + " đ");
        binding.txtDesProductDetail.setText(description);
        Picasso.get().load(img).placeholder(R.drawable.noimage).error(R.drawable.error).into(binding.imgDetailProduct);


    }

    private void ActionToolbar() {
        setSupportActionBar(binding.tooltbarDetailProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.tooltbarDetailProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
