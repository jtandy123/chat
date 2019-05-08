package com.ssy.chat.util;

import java.util.Map;

public class CharacterUtil {

    public static final String ERROR = "ERROR";

    public static final String SUCCESS = "SUCCESS";

    public static String SERVER_HOST; // 服务器地址信息

    public static String SERVER_PORT; // 服务器端口号

    public static String CLIENT_NAME; // 用户名

    public static int randomPort = generatePort(); // 客户端接收消息的端口号

    public static int randomPort2 = generatePort(); // 客户端接收用户列表的端口号

    public static int PORT = generatePort();

    public static int PORT2 = generatePort();

    public static boolean isEmpty(String str) {
        return null == str || str.length() == 0;
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPortCorrect(String port) {
        int temp = Integer.parseInt(port);

        if (temp <= 1024 || temp > 65535) {
            return false;
        }

        return true;
    }

    public static boolean isCorrect(String username) {
        return !username.contains("@") && !username.contains("/");
    }

    public static int generatePort() {
        int port = (int)(Math.random() * 50000 + 1025);
        return port;
    }

    public static boolean isUsernameDuplicated(Map map, String username) {
        return map.containsKey(username);
    }
}
