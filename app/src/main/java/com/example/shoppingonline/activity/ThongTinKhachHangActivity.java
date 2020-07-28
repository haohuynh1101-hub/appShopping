package com.example.shoppingonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shoppingonline.R;
import com.example.shoppingonline.databinding.ActivityThongTinKhachHangBinding;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    ActivityThongTinKhachHangBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityThongTinKhachHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
