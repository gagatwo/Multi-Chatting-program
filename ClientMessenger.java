package com.sehyun.Socket;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JTextArea;


public class ClientMessenger extends JFrame {


    private static final String HOST = "127.0.0.1"; //local host
    private static final int PORT = 5000;

    JTextField input = new JTextField(20);
    JTextArea chat = new JTextArea(14, 20);

    Socket socket = null;

    boolean running = true;
    BufferedReader in;
    PrintWriter out;

    ClientMessenger(String name) {
        //채팅 GUI 생성
        setTitle(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new JScrollPane(chat));
        c.add(input);
        chat.setEditable(false);

        //서버와 채팅 교류
        try {
            //서버와 연결할 socket 생성
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            //서버와 연결되자마자 접속한 사용자의 이름 전송
            out.println(name);
            out.flush();

            //채팅창에 텍스트를 적고 enter를 누르면 서버로 전달하는 tread
            input.addActionListener(e -> {
                JTextField message = (JTextField) e.getSource();
                out.println(message.getText());
                out.flush();
                if(message.getText().equals("quit")){
                    running =false;
                    setVisible(false);
                    System.exit(0);
                }
                message.setText("");
            });

            //서버로부터 전달받는 채팅을 채팅창에 출력하는 thread
            Thread reader = new Thread(() -> {
                while (running) {
                    String inputMessage = null;
                    try {
                        inputMessage = in.readLine();
                        chat.append(inputMessage + "\n");
                    } catch (IOException e) {
                    }
                }
            });

//            writer.start();
            reader.start();
            setSize(300, 350);
            setVisible(true);
//            while (in != null) {
//                String inputMsg = in.readLine();
//                if (("[" + name + "]님이 나가셨습니다").equals(inputMsg)) break;
//                System.out.println("From:" + inputMsg);
//            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                input.removeActionListener(input.getActionListeners()[0]);
                running = false;
                out.println("quit");
                out.flush();
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("서버 연결 종료");
                }
            }
        });

    }
}
