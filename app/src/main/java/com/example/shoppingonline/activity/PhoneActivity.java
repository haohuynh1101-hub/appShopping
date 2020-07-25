package com.example.shoppingonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingonline.R;
import com.example.shoppingonline.adapter.PhoneAdapter;
import com.example.shoppingonline.databinding.ActivityPhoneBinding;
import com.example.shoppingonline.model.LoaiSP;
import com.example.shoppingonline.model.SanPham;
import com.example.shoppingonline.util.CheckConnection;
import com.example.shoppingonline.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneActivity extends AppCompatActivity {
    ActivityPhoneBinding binding;
    ArrayList<SanPham> listProductPhone = new ArrayList<>();
    PhoneAdapter phoneAdapter;
    int madm = 1;
    View footerview;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addControls();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionToolbar();
            GetDataPhone(page);
            LoadMoreData();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }

    }



    private void LoadMoreData() {
        binding.lvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("thongtinsp",listProductPhone.get(position));
                startActivity(intent);
            }
        });
        binding.lvPhone.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void GetDataPhone(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Server.linkSanPhamPhone + page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d("TAG", "jsonArrayyyyy: " + jsonArray);
                        binding.lvPhone.removeFooterView(footerview);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("Id");
                            String name = jsonObject.getString("NameProduct");
                            Integer price = jsonObject.getInt("PriceProduct");
                            String img = jsonObject.getString("ImgProduct");
                            String des = jsonObject.getString("DesProduct");
                            listProductPhone.add(new SanPham(id, name, price, img, des));
                            phoneAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        Log.e("TAG", "LOI: ", e);
                        ;
                    }
                } else {
                    limitData = true;
                    binding.lvPhone.removeFooterView(footerview);
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

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null && response.length() != 2) {
//                    try {
//                        binding.lvPhone.removeFooterView(footerview);
//                        for (int i = 0; i < response.length(); i++) {
//
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            int Id = jsonObject.getInt("Id");
//                            String Name = jsonObject.getString("NameProduct");
//                            Integer Price = jsonObject.getInt("PriceProduct");
//                            String Img = jsonObject.getString("ImgProduct");
//                            String Description = jsonObject.getString("DesProduct");
//                            listProductPhone.add(new SanPham(Id, Name, Price, Img, Description));
//                            phoneAdapter.notifyDataSetChanged();
//                        }
//                    } catch (Exception ex) {
//                        Log.e("TAG", "LOI: ", ex);
//                    }
//                }
//             else
//            {
//                limitData = true;
//                binding.lvPhone.removeFooterView(footerview);
//                CheckConnection.showToast(getApplicationContext(), "Đã hết dữ liệu");
//            }
//                Log.d("TAG","onResponse: "+response);
//                Log.d("TAG","limitData: "+limitData);
//
//        }
//    },new Response.ErrorListener()
//
//    {
//        @Override
//        public void onErrorResponse (VolleyError error){
//        CheckConnection.showToast(getApplicationContext(), error.toString());
//    }
//    });
        requestQueue.add(stringRequest);
    }


    private void ActionToolbar() {
        setSupportActionBar(binding.toolbarPhone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarPhone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        phoneAdapter = new PhoneAdapter(getApplicationContext(), listProductPhone);
        binding.lvPhone.setAdapter(phoneAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.processbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    binding.lvPhone.addFooterView(footerview);
                    break;
                case 1:
                    GetDataPhone(++page);
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


}
