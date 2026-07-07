package com.meridian.demo;
import java.io.*; import java.net.*;
public class Net {
  static final String BASE = "https://demo.aegisrunner.com/api";
  static String get(String p) throws Exception { return send("GET",p,null); }
  static String post(String p,String j) throws Exception { return send("POST",p,j); }
  static String put(String p,String j) throws Exception { return send("PUT",p,j); }
  static String del(String p) throws Exception { return send("DELETE",p,null); }
  static String send(String method,String path,String json) throws Exception {
    HttpURLConnection c=(HttpURLConnection)new URL(BASE+path).openConnection();
    c.setRequestMethod(method); c.setConnectTimeout(15000); c.setReadTimeout(15000); c.setRequestProperty("Accept","application/json");
    if(json!=null){ c.setDoOutput(true); c.setRequestProperty("Content-Type","application/json"); try(OutputStream o=c.getOutputStream()){ o.write(json.getBytes("UTF-8")); } }
    int code=c.getResponseCode();
    return read(code<400?c.getInputStream():c.getErrorStream());
  }
  static String read(InputStream is) throws Exception {
    ByteArrayOutputStream b=new ByteArrayOutputStream(); byte[] buf=new byte[4096]; int n;
    while((n=is.read(buf))>0) b.write(buf,0,n); is.close(); return b.toString("UTF-8");
  }
}
