package com.ssy.chat.client;

import com.ssy.chat.util.CharacterUtil;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnectThread extends Thread {

    private JFrame frame;

    private String hostAddress;

    private int hostPort;

    private String username;

    private boolean flag = true;

    private Thread acceptMessageThread;

    private Thread acceptUserListThread;

    public ClientConnectThread(JFrame frame, String hostAddress, int hostPort, String username) {
        this.frame = frame;
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;
        this.username = username;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        String error = CharacterUtil.ERROR;

        try {
            InetAddress host = InetAddress.getByName(hostAddress);

            // 客户端向服务器发起连接
            socket = new Socket(host, hostPort);

            is = socket.getInputStream();
            os = socket.getOutputStream();

            int randomPort = CharacterUtil.randomPort;
            int randomPort2 = CharacterUtil.randomPort2;

            InetAddress address = InetAddress.getLocalHost(); // 得到客户端地址信息
            String clientAddress = address.toString();
            System.out.println(clientAddress);
            int l = clientAddress.indexOf("/");
            clientAddress = clientAddress.substring(l + 1); // 客户端ip地址
            // 客户端刚登陆时向服务器端所发送的信息
            String info = username + "@@@" + randomPort + "_" + randomPort2 + "_" + clientAddress;

            os.write(info.getBytes());

            byte[] buf = new byte[100];

            int length = is.read(buf);

            String temp = new String(buf, 0, length);

            if (temp.equals(error)) {
                JOptionPane.showMessageDialog(frame, "用户名与现有用户重复，请更换用户名！", "错误", JOptionPane.ERROR_MESSAGE);
                socket.close();
                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
