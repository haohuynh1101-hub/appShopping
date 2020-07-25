package com.example.shoppingonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingonline.R;
import com.example.shoppingonline.adapter.LaptopAdapter;
import com.example.shoppingonline.databinding.ActivityLaptopBinding;
import com.example.shoppingonline.model.SanPham;
import com.example.shoppingonline.util.CheckConnection;
import com.example.shoppingonline.util.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    ActivityLaptopBinding binding;
    ArrayList<SanPham> listLaptop = new ArrayList<>();
    LaptopAdapter laptopAdapter;
    View footerview;
    int pageLaptop = 1;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaptopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            GetDataLaptop(pageLaptop);
            LoadMoreData();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }

        addControls();
    }

    private void LoadMoreData() {
        binding.lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("thongtinsp", listLaptop.get(position));
                startActivity(intent);
            }
        });
        binding.lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetDataLaptop(int page) {
        String link = Server.linkSanPhamLaptop + page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d("TAG", "jsonArrayyyyy: " + jsonArray);
                        binding.lvLaptop.removeFooterView(footerview);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("Id");
                            String name = jsonObject.getString("NameProduct");
                            Integer price = jsonObject.getInt("PriceProduct");
                            String img = jsonObject.getString("ImgProduct");
                            String des = jsonObject.getString("DesProduct");
                            listLaptop.add(new SanPham(id, name, price, img, des));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        Log.e("TAG", "LOI: ", e);
                    }
                } else {
                    limitData = true;
                    binding.lvLaptop.removeFooterView(footerview);
                    CheckConnection.showToast(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int IdProduct = getIntent().getIntExtra("IdProduct", -1);
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idSanpham", String.valueOf(IdProduct));
                //param =1
                Log.d("TAG", "getParams: " + param);
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void addControls() {
        laptopAdapter = new LaptopAdapter(getApplicationContext(), listLaptop);
        binding.lvLaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.processbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    binding.lvLaptop.addFooterView(footerview);
                    break;
                case 1:
                    GetDataLaptop(++pageLaptop);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {

            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();

        }

    }

    private void ActionBar() {
        setSupportActionBar(binding.laptopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.laptopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
