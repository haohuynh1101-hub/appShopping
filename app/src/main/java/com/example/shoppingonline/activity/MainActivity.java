package com.example.shoppingonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingonline.R;
import com.example.shoppingonline.adapter.LoaiSPAdapter;
import com.example.shoppingonline.adapter.SanPhamAdapter;
import com.example.shoppingonline.databinding.ActivityMainBinding;
import com.example.shoppingonline.model.GioHang;
import com.example.shoppingonline.model.LoaiSP;
import com.example.shoppingonline.model.SanPham;
import com.example.shoppingonline.util.CheckConnection;
import com.example.shoppingonline.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<LoaiSP> listLoaiSP = new ArrayList<>();
    LoaiSPAdapter loaiSPAdapter;

    ArrayList<SanPham> listProduct = new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<GioHang> listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFliper();
            GetDataLoaiSanPham();

            GetDataSanPham();
            CatchOnItemListView();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
        addControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        binding.lvManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                            intent.putExtra("IdProduct", listLoaiSP.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("IdProduct", listLoaiSP.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void addControls() {
        loaiSPAdapter = new LoaiSPAdapter(listLoaiSP, getApplicationContext());
        binding.lvManHinhChinh.setAdapter(loaiSPAdapter);
        listLoaiSP.add(0, new LoaiSP(0, "Trang chủ", "https://img.icons8.com/fluent/48/000000/home.png"));
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), listProduct);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        binding.recyclerview.setAdapter(sanPhamAdapter);
        if(listGioHang!=null){

        }else {
            listGioHang=new ArrayList<>();
        }
    }

    private void GetDataLoaiSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkLoaiSanPham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int Id = jsonObject.getInt("Id");
                            String NameLoaiSP = jsonObject.getString("NameLoaiSP");
                            String ImgLoaiSP = jsonObject.getString("ImgLoaiSP");
                            listLoaiSP.add(new LoaiSP(Id, NameLoaiSP, ImgLoaiSP));
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            Log.d("TAG", "LOI: " + ex);
                        }
                    }
                    listLoaiSP.add(3, new LoaiSP(3, "Thông tin", "https://img.icons8.com/cute-clipart/64/000000/information.png"));
                    listLoaiSP.add(4, new LoaiSP(4, "Liên hệ", "https://img.icons8.com/cute-clipart/64/000000/phone.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkSanPham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response != null) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int Id = jsonObject.getInt("Id");
                            String Name = jsonObject.getString("NameProduct");
                            Integer Price = jsonObject.getInt("PriceProduct");
                            String Img = jsonObject.getString("ImgProduct");
                            String Description = jsonObject.getString("DesProduct");
                            listProduct.add(new SanPham(Id, Name, Price, Img, Description));
                            sanPhamAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("TAG", "LOI: ", ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    // chỉnh animation cho viewFlipper
    private void ActionViewFliper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn.fptshop.com.vn/Uploads/Originals/2019/1/21/636836609818617272_ip7-plus-hong-1.png");
        mangQuangCao.add("https://dienmaycholon.vn/public/picture/news/dienmay_1485.png");
        mangQuangCao.add("https://dienmaycholon.vn/public/picture/news/dienmay_1481.png");
        mangQuangCao.add("https://dienmaycholon.vn/public/picture/news/dienmay_1482.png");
        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.viewflipper.addView(imageView);
        }
        binding.viewflipper.setFlipInterval(5000);
        binding.viewflipper.setAutoStart(true);
        Animation slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        binding.viewflipper.setInAnimation(slide_in_right);
        binding.viewflipper.setOutAnimation(slide_out_right);

    }

    // Mở trang mục lục
    private void ActionBar() {
        setSupportActionBar(binding.toolbarTrangChu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarTrangChu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        binding.toolbarTrangChu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
