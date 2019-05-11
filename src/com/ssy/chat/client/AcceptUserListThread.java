package com.ssy.chat.client;

import com.ssy.chat.util.CharacterUtil;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptUserListThread extends Thread {

    private JFrame frame;

    private ServerSocket serverSocket;

    public AcceptUserListThread(JFrame frame) {
        this.frame = frame;

        try {
            serverSocket = new ServerSocket(CharacterUtil.randomPort2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("接收用户列表");
        while(true) {
            try {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();

                byte[] buf = new byte[4096];
                int length = inputStream.read(buf);
                String userList = new String(buf, 0, length);

                ChatClient chatClient = (ChatClient) frame;
                chatClient.getjTextArea2().setText(userList);

                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
