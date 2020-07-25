package com.example.shoppingonline.model;

public class GioHang {
    public int idsp;
    public String tensp;
    public Integer giasp;
    public String imgsp;
    public int soluong;

    public GioHang(int idsp, String tensp, Integer giasp, String imgsp, int soluong) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.imgsp = imgsp;
        this.soluong = soluong;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getImgsp() {
        return imgsp;
    }

    public void setImgsp(String imgsp) {
        this.imgsp = imgsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
