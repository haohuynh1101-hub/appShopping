package com.example.shoppingonline.model;

public class LoaiSP {
    public int Id;
    public  String TenLoaiSP;
    public String HinhAnhLoaiSP;

    public LoaiSP(int id, String tenLoaiSP, String hinhAnhLoaiSP) {
        Id = id;
        TenLoaiSP = tenLoaiSP;
        HinhAnhLoaiSP = hinhAnhLoaiSP;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        TenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnhLoaiSP() {
        return HinhAnhLoaiSP;
    }

    public void setHinhAnhLoaiSP(String hinhAnhLoaiSP) {
        HinhAnhLoaiSP = hinhAnhLoaiSP;
    }

}
