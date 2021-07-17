package beanstudycafe_chat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import beanstudycafe_dto.CafeDTO;

public class CafeClient extends JFrame implements ActionListener,Runnable {
   private JTextArea output;
   private JTextField inputT;
   private JButton sendBtn;
   private Socket socket;
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   private CafeDTO cafeDTO;
   private String phone;

    public CafeClient(CafeDTO cafeDTO) {
    	this.cafeDTO =cafeDTO;
    	
    	
    output = new JTextArea();
    output.setFont(new Font("LH B",Font.BOLD,20));
    JScrollPane scroll = new JScrollPane(output);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    output.setEditable(false);//입력못하게 원청봉쇄
    
    inputT = new JTextField();
    sendBtn = new JButton("보내기");
    
    JPanel southP = new JPanel();
    southP.setLayout(new BorderLayout());
    southP.add("Center", inputT);
    southP.add("East",sendBtn);
    
    Container container = this.getContentPane();
    container.add("Center",scroll);
    container.add("South",southP);
    
    setTitle("문의하기");
    setBounds(500,200,500,500);
    setVisible(true);
    
    service(cafeDTO);
    addWindowListener(new WindowAdapter() {
       
       @Override
      public void windowClosing(WindowEvent e) {//창을 종료할때
          ChatDTO chatDTO = new ChatDTO();
          chatDTO.setCommand(Info.EXIT); //나갈거얌!
          chatDTO.setTo(cafeDTO.getTel());
          try {
            oos.writeObject(chatDTO);//server에 나갈거라고 보내주고 기다리기.
            oos.flush();
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
    });

    }//CafeClient()
    
    public void service(CafeDTO cafeDTO) {
    	  String serverIP="";
    		try {
    			serverIP = 
//    	             JOptionPane.showInputDialog(this,"서버 IP를 입력하세요","192.***.*"); //테스트용 각자 자기ip적기

    			   Inet4Address.getLocalHost().getHostAddress();
    		} catch (UnknownHostException e1) {//로컬호스트를 못가져오면
    			e1.printStackTrace();
    		}
       
//       if(serverIP == null || serverIP.length()==0) {
//          System.out.println("서버 IP가 입력되지 않았습니다.");
//          System.exit(0);
//       }//if
       
      phone = cafeDTO.getTel();
       
       
      try {
         socket = new Socket(serverIP, 9500); 
         
         //IO생성
         ois = new ObjectInputStream(socket.getInputStream());
         oos = new ObjectOutputStream(socket.getOutputStream());
         
      //폰번호(아이디)를 서버로 보내주기 --> 서버열리자마자 해줘야하는거
         ChatDTO chatDTO = new ChatDTO();
         chatDTO.setCommand(Info.JOIN);//입장
         chatDTO.setPhone(phone);//폰번호(아이디)보내주기
         oos.writeObject(chatDTO);//DTO를 보내주기
         oos.flush();//버퍼를 비우는거
      
      } catch (UnknownHostException e) {
         System.out.println("서버를 찾을 수 없습니다");
         e.printStackTrace();
         setVisible(false);
      } catch (IOException e) {
         System.out.println("서버와 연결이 안되었습니다");
         e.printStackTrace();
         setVisible(false);
      } 
      
      //스레드 생성
      Thread thread = new Thread(this);
      
      //스레드 시작 - 스레드 실행(run()을 찾아서 수행)
      thread.start();
      
      //이벤트발생
      inputT.addActionListener(this); //엔터누르면 go
      sendBtn.addActionListener(this); // 버튼누르면 go
      
    }//service()

    
    @Override
    public void run() { //server 에서 오는 메세지
    	
    	// 이건 에러랑같이 뜨지만 우선 관리자인지 손님인지 분리해줘야한다.
//        if (phone.equals(chatDTO.getPhone())) {
//          output.append(chatDTO.getMessage() + "\n");
//          int pos = output.getText().length();
//          output.setCaretPosition(pos);
//          System.out.println("폰이 같다면 뿌려줘");
//       }
    	
    	  ChatDTO chatDTO = null;
    	  
			while (true) {

				try {
					chatDTO = (ChatDTO) ois.readObject(); // chatDTO가 받을라면 형변환

					if (chatDTO.getCommand() == Info.EXIT) {
						ois.close();
						oos.close();
						socket.close();

						setVisible(false);
						break;
					} else if (chatDTO.getCommand() == Info.SEND) {
						if (chatDTO.getTo().equals(cafeDTO.getTel())) {
							output.append(chatDTO.getMessage() + "\n");
							int pos = output.getText().length();
							output.setCaretPosition(pos);// 글자 길이에 따라 자동으로 스크롤 조절
							
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} // 형변환

       }//while
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
       //메세지 보내주기
       String message = inputT.getText();
       
		ChatDTO chatDTO = new ChatDTO();
		if (message.equals("나갈래")) {
			chatDTO.setTo(cafeDTO.getTel());
			chatDTO.setCommand(Info.EXIT);

		} else {
			chatDTO.setCommand(Info.SEND);
			chatDTO.setTo(cafeDTO.getTel());
			chatDTO.setMessage(message); // 메세지 보내주기
		}
       try {
         oos.writeObject(chatDTO);
         oos.flush();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
       inputT.setText(""); //채팅창 입력부분 비워주기
    }//actionPerformed(ActionEvent e)
   
//   public static void main(String[] args) {
//      new CafeClient().service(new CafeDTO());
//   }

   
}