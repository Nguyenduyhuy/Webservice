package com.example.ndh.webservice;


import io.socket.client.Socket;

/**
 * Created by NDH on 4/23/2018.
 */

public class Public {
    public static final String root = "http://192.168.211.19:7000";
    public static  final String urlDangNhap = root+"/login";
    public static final  String urlDangKy =  root+"/register";
    public static final String urlGetInfo=root+"/getchatlist";
    public static final String urlchangepass=root+"/changepassword";
    public static Socket pSocket;
}
