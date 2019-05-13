package com.ssy.chat.server;

import com.ssy.chat.util.CharacterUtil;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ExitThread extends Thread {

    private JFrame frame;

    private ServerSocket serverSocket;

    public ExitThread(JFrame frame) {
        this.frame = frame;

        try {
            serverSocket = new ServerSocket(CharacterUtil.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                byte[] buff = new byte[100];

                int length = inputStream.read(buff);

                String clientName = new String(buff, 0, length);

                Map<String, String> map = ((Server)frame).getMap();
                if (map.containsKey(clientName)) {
                    map.remove(clientName);
                }

                Set<String> set = map.keySet();
                StringBuffer sb = new StringBuffer();

                for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
                    sb.append(iter.next() + "\n");
                }

                String userList = sb.toString(); // 得到用户列表

                ((Server)frame).setUserList();

                Set set2 = map.entrySet();
                Iterator iterator2 = set2.iterator();

                while(iterator2.hasNext()) {
                    Map.Entry me = (Map.Entry) iterator2.next();

                    String temp = (String) me.getValue();
                    int index_ = temp.indexOf("_");
                    int lastIndex_ = temp.lastIndexOf("_");
                    int port = Integer.parseInt(temp.substring(index_ + 1, lastIndex_));
                    String address = temp.substring(lastIndex_ + 1);

                    InetAddress clientAddress = InetAddress.getByName(address);

                    Socket s = new Socket(clientAddress, port);
                    OutputStream os = s.getOutputStream();
                    os.write(userList.getBytes());
                    os.close();
                    s.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
