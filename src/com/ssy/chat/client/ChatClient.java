package com.ssy.chat.client;

import com.ssy.chat.util.CharacterUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClient extends JFrame {

    private JButton jButton1;

    private JButton jButton2;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JPanel jPanel3;

    private JScrollPane jScrollPane1;

    private JScrollPane jScrollPane2;

    private JTextArea jTextArea1;

    private JTextArea jTextArea2;

    private JTextField jTextField;

    private Thread thread;

    public ChatClient(Thread parentThread) {
        initComponents();
        this.thread = parentThread;

        this.addWindowListener(new MyEvent(parentThread));
    }

    public Thread getThread() {
        return thread;
    }

    public JTextArea getjTextArea2() {
        return jTextArea2;
    }

    public JTextArea getjTextArea1() {
        return jTextArea1;
    }

    public void setjTextArea1(JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jTextField = new JTextField(20);
        jButton1 = new JButton();
        jButton2 = new JButton();
        jPanel2 = new JPanel();
        jScrollPane2 = new JScrollPane();
        jTextArea2 = new JTextArea();

        jPanel3 = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("聊天室");
        setResizable(false);
        jPanel1.setBorder(BorderFactory.createTitledBorder("聊天室信息"));
        jPanel2.setBorder(BorderFactory.createTitledBorder("在线用户列表"));
        jTextArea1.setForeground(new Color(0, 0, 255));
        jTextArea1.setColumns(30);
        jTextArea1.setRows(25);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(25);

        jTextArea1.setEditable(false);
        jTextArea2.setEditable(false);

        jPanel3.add(jTextField);
        jPanel3.add(jButton1);
        jPanel3.add(jButton2);

        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jScrollPane1, BorderLayout.NORTH);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);

        jPanel2.add(jScrollPane2);

        jScrollPane1.setViewportView(jTextArea1);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("发送");
        jButton2.setText("清屏");

        this.setLayout(new FlowLayout());
        this.getContentPane().add(jPanel1);
        this.getContentPane().add(jPanel2);

        this.setAlwaysOnTop(true);
        this.pack();
        this.setVisible(true);
    }
}

class MyEvent extends WindowAdapter {

    private Thread parentThread;

    public MyEvent(Thread parentThread) {
        this.parentThread = parentThread;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int[] ports = CharacterUtil.string2Array(CharacterUtil.SERVER_PORT);
        int port = ports[0];

        try {
            InetAddress address = InetAddress.getByName(CharacterUtil.SERVER_HOST);
            Socket socket = new Socket(address, port);
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(CharacterUtil.CLIENT_NAME.getBytes());

            outputStream.close();
            socket.close();
            ((ClientConnectThread) parentThread).setFlag(false);

            System.exit(0);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
