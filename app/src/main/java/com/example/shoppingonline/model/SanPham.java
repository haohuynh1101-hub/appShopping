package com.example.shoppingonline.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int Id;
    public String TenSanPham;
    public Integer GiaSanPham;
    public String ImgSanPham;
    public String DesSanPham;

    public SanPham(int id, String tenSanPham, Integer giaSanPham, String imgSanPham, String desSanPham) {
        Id = id;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        ImgSanPham = imgSanPham;
        DesSanPham = desSanPham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getImgSanPham() {
        return ImgSanPham;
    }

    public void setImgSanPham(String imgSanPham) {
        ImgSanPham = imgSanPham;
    }

    public String getDesSanPham() {
        return DesSanPham;
    }

    public void setDesSanPham(String desSanPham) {
        DesSanPham = desSanPham;
    }


}
