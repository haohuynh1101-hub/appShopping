package com.example.shoppingonline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.shoppingonline.R;
import com.example.shoppingonline.adapter.GioHangAdapter;
import com.example.shoppingonline.databinding.ActivityGioHangBinding;
import com.example.shoppingonline.model.GioHang;
import com.example.shoppingonline.model.SanPham;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    static ActivityGioHangBinding binding;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionToolbar();
        CheckData();
        EventUtil();
        addControls();
        addEvents();
    }

    private void addEvents() {
        ContinuePurchase();
        CatchOnItemListview();
        CompletePurchase();
    }

    private void CompletePurchase() {
    binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(MainActivity.listGioHang.size()<=0){
                Toast.makeText(getApplicationContext(),"Giỏ hàng của bạn đang trống",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent=new Intent(getApplicationContext(),ThongTinKhachHangActivity.class);
                startActivity(intent);
            }

        }
    });
    }

    private void CatchOnItemListview() {
        binding.lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        MainActivity.listGioHang.remove(position);
                        gioHangAdapter.notifyDataSetChanged();
                        EventUtil();
                        CheckData();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            }
        });
    }


    private void ContinuePurchase() {
        binding.btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        gioHangAdapter = new GioHangAdapter(getApplicationContext(), MainActivity.listGioHang);
        binding.lvGioHang.setAdapter(gioHangAdapter);
    }

    public static void EventUtil() {
        long toltalMoney = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            toltalMoney = toltalMoney + MainActivity.listGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txtValue.setText(decimalFormat.format(toltalMoney) + " đ");
    }

    private void CheckData() {
        if (MainActivity.listGioHang.size() <= 0) {
            binding.txtThongBao.setVisibility(View.VISIBLE);
            binding.lvGioHang.setVisibility(View.INVISIBLE);
        } else {
            binding.txtThongBao.setVisibility(View.INVISIBLE);
            binding.lvGioHang.setVisibility(View.VISIBLE);

        }
    }

    private void ActionToolbar() {
        setSupportActionBar(binding.toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
