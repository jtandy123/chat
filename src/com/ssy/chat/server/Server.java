package com.ssy.chat.server;

import com.ssy.chat.util.CharacterUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Server extends JFrame {

    private JLabel jLabel1;

    private JLabel jLabel2;

    private JLabel jLabel3;

    private JButton jButton;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JScrollPane jScrollPane1;

    private JTextArea jTextArea1;

    private JTextField jTextField1;

    private static final String CONNECT_THREAD = "CONNECT";

    private static Thread thread;

    private static Thread thread2;

    private static Thread thread3;

    private Map map = new HashMap<>(); // 用户名与端口号的映射

    public Server(String name) {
        super(name);

        this.initComponents();
    }

    // 初始化界面
    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();

        jTextField1 = new JTextField(10);
        jButton = new JButton();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();

        jPanel1.setBorder(BorderFactory.createTitledBorder("服务器信息"));
        jPanel2.setBorder(BorderFactory.createTitledBorder("在线用户列表"));

        jLabel1.setText("服务器状态");
        jLabel2.setText("停止");
        jLabel2.setForeground(new Color(204, 0, 51));
        jLabel3.setText("端口号");

        jButton.setText("启动服务器");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Server.this.execute();
                execute();
            }
        });

        this.jTextArea1.setEditable(false);
        this.jTextArea1.setForeground(new Color(245, 0, 0));

        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel1.add(jLabel3);
        jPanel1.add(jTextField1);
        jPanel1.add(jButton);

        jTextArea1.setColumns(30);
        jTextArea1.setForeground(new Color(0, 51, 204));
        jTextArea1.setRows(20);

        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane1);

        this.getContentPane().add(jPanel1, BorderLayout.NORTH);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private void execute() {
        String hostPort = this.jTextField1.getText();

        if (CharacterUtil.isEmpty(hostPort)) {
            JOptionPane.showMessageDialog(this, "端口号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);

            this.jTextField1.requestFocus();

            return;
        }

        if (!CharacterUtil.isNumber(hostPort)) {
            JOptionPane.showMessageDialog(this, "端口号必须为数字！", "警告", JOptionPane.WARNING_MESSAGE);

            this.jTextField1.requestFocus();

            return;
        }

        if (!CharacterUtil.isPortCorrect(hostPort)) {
            JOptionPane.showMessageDialog(this, "端口号必须在1024与65535之间！", "警告", JOptionPane.WARNING_MESSAGE);

            this.jTextField1.requestFocus();

            return;
        }


    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static void main(String[] args) {
        new Server("服务器");
    }
}