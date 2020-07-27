package com.example.shoppingonline.util;

public class Server {
    public static String localhost = "10.43.43.18";
    public static String linkLoaiSanPham = "http://" + localhost + "/loaispserver/api/LoaiSanPham";
    public static String linkSanPham = "http://" + localhost + "/loaispserver/api/SanPham";
    public static String linkSanPhamPhone="http://"+localhost+"/loaispserver/api/SanPham/?pagePhone=";
    public static String linkSanPhamLaptop="http://"+localhost+"/loaispserver/api/SanPham/?pageLaptop=";

}
