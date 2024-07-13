멀티 채팅 프로그램 구현

- 기본 구조 밒 주요 포인트
  
    - 구조 : 클라이언트 ---(채팅)--> 서버 ---(채팅)--> 모든 클라이언트
    - 소켓 통신을 이용해 멀티 채팅 기능을 구현
    - 클라이언트가 보낸 메시지가 서버를 통해 다른 클라이언트들에게 전달되도록 구현
    - 서버에 접속하는 클라이언트마다 클라이언트-서버 간의 개별 상호작용(채팅 전달) 위해 스레드를 추가 생성하는 멀티스레드 방식 이용
    - Java Swing을 이용한 간단한 채팅프로그램 GUI 제작

----------- 서버 구현 -----------
1. Server.java
   - Server.open()을 통해 채팅 서버 작동
   - 채팅 프로그램의 서버를 구현
   - 서버 소켓의 포트 번호를 5000번으로 지정
   - 클라이언트의 소켓과 연결되면 새로운 RecieveThread 생성하여 상호작용 시작
   - 클라이언트와 연결 종료되면 소켓과 서버소켓 종료

2. RecieveTread.java
   - 클라이언트가 새롭게 추가될 때마다 새로운 RecievThread 생성
   - 추가되는 클라이언트에 대한 PrintWeiter를 list에 저장
   - sendToAll(Stirng s) 함수: list에 저장되어 있는 모든 PrintWriter에 문자열 s를 전송 후 출력 
   - 입장한 클라이언트를 전체에게 알리기 위해 스레드가 생성되자마자 "(이름)님이 들어오셨습니다"를 전체에게 전송
   - 이후에는 클라이언트가 채팅 입력하는데로 모든 클라이언트에게 전달
   - 클라이언트가 접속을 종료하면 남은 클라이언트에게 이를 전달하고 list에서 제거
  
----------- 클라이언트 구현 -----------
 1. SetName.java
     - new SetName();을 통해 클라이언트는 채팅프로그램을 시작
     - SetName.java에서는 채팅방에 입장하기 전 클라이언트의 이름을 입력받는 GUI 창 구현
     - 이름을 입력 받으면 GUI 창을 내리고 ClientMessenger생성

2. ClientMessenger.java
   - 채팅을 입력하고 상대방의 채팅을 볼 수 있는 GUI 구현
   - 소켓을 생성해 포트 번호 5000의 서버 측 소켓과 연결
   - 서버와 연결하자마자 클라이언트 이름 전송 -> 서버는 다른 클라이언트에게 "(이름)님이 들어오셨습니다" 전송
   - 채팅 입력란에 채팅을 치고 enter를 누르면 채팅이 전송되도록 ActionListener 추가(예외로 "quit"이 입력되면 채팅 종료 되도록 설정)
   - 동시에 서버에서 전달해준 채팅을 받아 채팅창에 띄우기 위해 reader 스레드 시작
   - GUI 창을 닫으면 채팅이 종료되도록 WindwoListener 추가


3. Chatting_UI.pdf
    - 실제 작동 화면 자료
     
