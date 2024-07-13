package com.sehyun.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ReceiveThread extends Thread {
    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;
    static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

    public ReceiveThread (Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            list.add(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        String name = "";
        try {
            //처음에는 client이름을 수신
            name = in.readLine();
            sendAll(name + "님이 들어오셨습니다.");

            while (in != null) {
                String inputMsg = in.readLine();
                if("quit".equals(inputMsg)) break; //quit 입력받으면 해당 client와 연결 종료
                sendAll(name + ": " + inputMsg); //한 명의 client로부터 받은 메세지를 전체에게 다시 전달
            }
        } catch (IOException e) {
            System.out.println("[" + name + " 접속끊김]");
        } finally {
            sendAll(name + "님이 나가셨습니다");
            list.remove(out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //서버가 전체에게 메세지 전달
    private void sendAll (String s) {
        for (PrintWriter out: list) {
            out.println(s);
            out.flush();
        }
    }

}


