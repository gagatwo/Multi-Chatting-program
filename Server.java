package com.sehyun.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void open() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            serverSocket = new ServerSocket(5000); //포트 번호 5000으로 socket 생성

            //클라이언트의 연결을 대기하며 연결시에 receivethread 생성
            while(true){
                System.out.println("Server실행, Client연결대기중");
                socket = serverSocket.accept();
                ReceiveThread receiveThread = new ReceiveThread(socket);
                receiveThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                scanner.close();        // Scanner 닫기
                socket.close();            // Socket 닫기
                serverSocket.close();        // ServerSocket 닫기
                System.out.println("연결 종료");
            } catch (IOException e) {
                System.out.println("통신 에러");
            }
        }
    }
}

