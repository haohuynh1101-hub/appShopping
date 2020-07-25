package com.example.shoppingonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.shoppingonline.R;
import com.example.shoppingonline.databinding.ActivityGioHangBinding;

public class GioHangActivity extends AppCompatActivity {
ActivityGioHangBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionToolbar();
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
