package com.sehyun.Socket;
import javax.swing.*;
import java.awt.*;

public class SetName extends JFrame {

    JTextField nameLine = new JTextField(15);
    JTextArea enter = new JTextArea("<이름을 입력하세요>");
    SetName(){
        //사용자 이름 입력 창 open
        setTitle("메신저");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(enter);
        c.add(nameLine);
        enter.setEditable(false);
        setSize(250, 150);
        setVisible(true);

        //사용자가 이름을 입력하고 enter를 누르면 사용자 이름으로 채팅 시작
        nameLine.addActionListener(e -> {
            new ClientMessenger(nameLine.getText());
            setVisible(false);
        });



    }
}
