package com.example.lenovo.myapplication.config;

public class Config {
    public static boolean IS_DEV = false;
    public static String API_VERSION = "/v5";
    public static String VERSION = "v2.3.0beta";
    public static String SAVE_PATH = "sightfuture";
    public static String DESCRIBE = "drugboxapp3:";
    public static String BASE_URL = Config.IS_DEV ? "http://192.168.199.235:52714" + Config.API_VERSION : "http://api.sightfuture.cn" + Config.API_VERSION;

}
